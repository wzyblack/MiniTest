package dao.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class JdbcHandler {

	private static String driver;
	private static String source = "jdbc";
	private static String url;
	private static String user;
	private static String password;

	static{
		ResourceBundle bundle = ResourceBundle.getBundle(source);
		driver = bundle.getString("driver");
		url = bundle.getString("url");
		user = bundle.getString("username");
		password = bundle.getString("password");
		//加载驱动
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取连接
	 * @return
	 */
	public static Connection getConnection(){
		Connection con = null;
		//获取连接
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static void  closeAll(Connection con ,Statement stmt,ResultSet rs){
		try {
			if(rs!=null){
				rs.close();
			}
			if(stmt !=null){
				stmt.close();
			}
			if(con!=null){
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 对数据库增删改的通用方法
	 * @param sql
	 * @param obj
	 * @return
	 */
	public static boolean executeUpdate(String sql,Object[]obj){
		Connection con = getConnection();
		PreparedStatement stmt=null;
		try {
			stmt = con.prepareStatement(sql);
			
			if(obj != null){
				for(int i=0; i < obj.length; i++){
					if(obj[i].getClass()==Date.class){
						stmt.setTimestamp(i+1, new Timestamp(((Date)obj[i]).getTime()));
					}
					else{
						stmt.setObject(i+1, obj[i]);
					}
				}
				return stmt.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			closeAll(con,stmt,null);
		}
		
		return false;
	}
	

	/**
	 * 返回单个查询结果
	 * @param <T>
	 * @param sql
	 * @param obj
	 * @param tClass
	 * @return
	 */
	public static<T> T getExecuteQuerySingle(String sql,Object[] obj,Class<T> tClass){
		T t = null;
		Connection con = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			if(con != null){
				stmt = con.prepareStatement(sql);
				//注入参数
				if(obj != null){
					for(int i = 0; i < obj.length; i++){
						//判断是否为日期类型
						if(obj[i].getClass() == Date.class){
							stmt.setTimestamp(i+1, new Timestamp(((Date)obj[i]).getTime()));
						}
						//若是整形
						else if(obj[i].getClass() == int.class){
							stmt.setInt(i+1, Integer.parseInt(obj[i].toString()));
						}
						//若是字符型
						else if(obj[i].getClass() == String.class){
							
							stmt.setString(i+1, obj[i].toString());
						}
					}
					
				}
				rs = stmt.executeQuery();
				//获取元数据
				ResultSetMetaData metaData = rs.getMetaData();
				List<ColumnInfo> colInfoList = JdbcUtil.parseMetaData(metaData);
				//获取反射类的所有属性和方法
				Field[] fields = tClass.getDeclaredFields();
				//获取所有方法
				Method[] methods = tClass.getMethods();
				while(rs.next()){
					//获取返回对象
					t = (T)tClass.newInstance();
					//循环匹配
					for(int i = 0 ; i<colInfoList.size(); i++){
						//获取这个列
						ColumnInfo col = colInfoList.get(i);
						//获取该列的类型
						int colType = col.getColnumType();
						//获取该列的列名
						String colName = col.getColunmName();
						//该列的值
						Object value = null;
						//日期类型
						if(colType == Types.DATE){
							Date date = new Date(rs.getTimestamp(i+1).getTime());
							value = date;
						}
						else if(colType == Types.TIME){
							value = rs.getInt(i+1);
							//System.out.println("111进入了Date："+value);
						}
						//字符类型
						else if(colType == Types.VARCHAR){
							value = rs.getString(i+1);
						}
						//整数
						else if(colType == Types.INTEGER){
							value = rs.getInt(i+1);
						}
						//浮点
						else if(colType == Types.FLOAT){
							value = rs.getDouble(i+1);
						}
						//双精度
						else if(colType == Types.DOUBLE){
							
							value = rs.getDouble(i+1);
						}
						//数值
						else if(colType == Types.DECIMAL){
							BigDecimal num = rs.getBigDecimal(i+1);
							if(num != null){
								if(JdbcUtil.isInteger(num)){
									value = rs.getInt(i+1);
								}
								else{
									value = rs.getDouble(i+1);
								}
							}
						}
						else if(colType == Types.TIME){
							value = rs.getInt(i+1);
						}
						//datatime
						else if(colType == 93){
							Date date = new Date(rs.getTimestamp(i+1).getTime());
							value = date;
						}
						//MySQLText
						else if(colType == -1){
							value= rs.getString(i+1);
						}//判断结束
						
						
						//是否有属性的标志性
						boolean hasField = false;
						//存储获得的属性
						String fieldName = null;
						if(fields != null){
							for(int j = 0; j < fields.length; j++){
								if(fields[j].getName().toUpperCase().equals(colName.toUpperCase())){
									hasField = true;
									fieldName = fields[j].getName();
									break;
								}
							}
						}
						
						//找到set方法
						if(methods!=null&&hasField){
							for(int j = 0; j< methods.length; j++){
								if(methods[j].getName().equals("set"+JdbcUtil.parseFirstUpper(fieldName))){
									methods[j].invoke(t, value);
									break;
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			closeAll(con,stmt,rs);
		}
		return t;
	}
	
	/**
	 * 返回多个 查询结果
	 * @param <T>
	 * @param sql
	 * @param obj
	 * @param tClass
	 * @return
	 */
	public static<T> List<T> getExecuteQueryList(String sql,Object[] obj,Class<T> tClass){
		List<T> tList = new ArrayList<T>();
		Connection con = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			if(con != null){
				stmt = con.prepareStatement(sql);
				//注入参数
				if(obj != null){
					for(int i = 0; i < obj.length; i++){
						//判断是否为日期类型
						if(obj[i].getClass() == Date.class){
							stmt.setTimestamp(i+1, new Timestamp(((Date)obj[i]).getTime()));
						}
						else{
							stmt.setObject(i+1, obj[i]);
						}
					}
					
				}
				rs = stmt.executeQuery();
				//获取元数据
				ResultSetMetaData metaData = rs.getMetaData();
				List<ColumnInfo> colInfoList = JdbcUtil.parseMetaData(metaData);
				//获取反射类的所有属性和方法
				Field[] fields = tClass.getDeclaredFields();
				//获取所有方法
				Method[] methods = tClass.getMethods();
				while(rs.next()){
					T t = null;
					//获取返回对象
					t = (T)tClass.newInstance();
					//循环匹配
					for(int i = 0 ; i<colInfoList.size(); i++){
						//获取这个列
						ColumnInfo col = colInfoList.get(i);
						//获取该列的类型
						int colType = col.getColnumType();
						//System.out.println("jdbcHandle:"+colType);
						//获取该列的列名
						String colName = col.getColunmName();
						//System.out.println("列名："+colName);
						//该列的值
						Object value = null;
						//日期类型
						//System.out.println(colType);
						if(colType == Types.DATE){
							value = rs.getTimestamp(i+1);
							//System.out.println("111进入了Date："+value);
						}
						else if(colType == Types.TIME){
							value = rs.getInt(i+1);
							//System.out.println("111进入了Date："+value);
						}
						//MySQLText
						else if(colType == -1){
							value= rs.getString(i+1);
						}
						//字符类型
						else if(colType == Types.VARCHAR){
							//System.out.println("是字符串");
							value = rs.getString(i+1);
							//System.out.println("111进入了varchar："+colType);
						}
						//整数
						else if(colType == Types.INTEGER){
							value = rs.getInt(i+1);
							//System.out.println("111进入了Int："+colType);
						}
						//浮点
						else if(colType == Types.FLOAT){
							value = rs.getDouble(i+1);
						}
						//双精度
						else if(colType == Types.DOUBLE){
							value = rs.getDouble(i+1);
						}
						//datatime
						else if(colType == 93){
							value = rs.getTimestamp(i+1);
						}//判断结束
						
						//是否有属性的标志性
						boolean hasField = false;
						//存储获得的属性
						String fieldName = null;
						if(fields != null){
							for(int j = 0; j < fields.length; j++){
								//System.out.println("属性名："+fields[j].getName().toUpperCase());
								if(fields[j].getName().toUpperCase().equals(colName.toUpperCase())){
									hasField = true;
									fieldName = fields[j].getName();
									break;
								}
							}
						}
						
						//找到set方法
						if(methods!=null&&hasField){
							for(int j = 0; j< methods.length; j++){
								if(methods[j].getName().equals("set"+JdbcUtil.parseFirstUpper(fieldName))){
									methods[j].invoke(t, value);
									break;
								}
							}
						}
					}//for循环结束
					if(t!=null){
						tList.add(t);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			closeAll(con,stmt,rs);
		}
		
		return tList;
	}
	
	/**
	 * 获取返回数字查询
	 * @param <T>
	 * @param sql
	 * @param obj
	 * @param tClass
	 * @return
	 */
	public static <T> int getNumSingle(String sql, Object[] obj){
		int num = 0;
		Connection con = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		if(con!=null){
			try {
				stmt = con.prepareStatement(sql);
				if(obj != null){
					//注入参数
					for(int i = 0; i< obj.length; i++){
						if(obj[i].getClass() == Date.class){
							stmt.setTimestamp(i+1,new Timestamp(((Date)obj[i]).getTime()));
						}
						else{
							stmt.setObject(i+1, obj[i]);
						}
					}
				}
				rs = stmt.executeQuery();
				while(rs.next()){
					num = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				closeAll(con,stmt,rs);
			}
		}
		return num;
	}
	
}

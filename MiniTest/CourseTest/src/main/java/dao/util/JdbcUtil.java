package dao.util;
import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JdbcUtil {
	/**
	 * 解析元数据
	 * @param metaData
	 * @return
	 */
	public static List<ColumnInfo> parseMetaData(ResultSetMetaData metaData){
		ArrayList<ColumnInfo> list = new ArrayList<ColumnInfo>();
		
		try {
			for(int i = 0; i< metaData.getColumnCount();i++){
				ColumnInfo col = new ColumnInfo(metaData.getColumnName(i+1), metaData.getColumnType(i+1));
				list.add(col);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 判断是否是整数
	 * @param num
	 * @return
	 */
	public static boolean isInteger(BigDecimal num){
		if(num.toString().indexOf(".") == -1){
			return true;
		}
		return false;
	}
	
	/**
	 * 将首字母转化成大写的方法
	 * @param s
	 * @return
	 */
	public static String parseFirstUpper(String s){
		return s.substring(0,1).toUpperCase()+s.substring(1);
	}
}

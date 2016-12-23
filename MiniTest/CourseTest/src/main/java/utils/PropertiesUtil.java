package utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties properties = new Properties();
	public static String local;
	 public static int getNum(){
		InputStream in;
		try {
			properties.load(new FileInputStream(local)); 
			in = new BufferedInputStream(new FileInputStream(
					 local));
			properties.load(in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         String value = properties.getProperty("courseNum");
		 System.out.println(value+":"+local);
		 return Integer.parseInt(value)+1;
	 }
	 
	 public static boolean setNum(String string,String key){
		 boolean flag = true;
		 //string = (Integer.parseInt(string)+1)+"";
		 System.out.println("num:"+string+"local:"+local);
		 try {
			 properties.load(new FileInputStream(local)); 
			 OutputStream fos = new FileOutputStream(local);
			 properties.setProperty(key, string);
			 // 以适合使用 load 方法加载到 Properties 表中的格式，
			 // 将此 Properties 表中的属性列表（键和元素对）写入输出流
			 properties.store(fos, "Update '" + key + "' value");
		} catch (Exception e) {
			//flag = false;
			e.printStackTrace();
		}
		 return flag;
	 }
	 
	 public static String getKCNum(){
		 InputStream in;
			try {
				properties.load(new FileInputStream(local)); 
				in = new BufferedInputStream(new FileInputStream(
						 local));
				properties.load(in);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
	         String value = properties.getProperty("id");
			 System.out.println(value+":11:"+local);
			 return value;
	 }
}

package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PerUtils {
	
	/**
	 * 获得ID
	 * @return
	 */
	public String getID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	
	/**
	 * 分页
	 * @param request
	 * @param list
	 * @return
	 */
	public static <T> String getAuto(List<T> list,int total){
		List<T> data = new ArrayList<T>();
		for (int i = 0, l = list.size(); i < l; i++) {
			T record = list.get(i);
			if (record == null)
				continue;
			//System.out.println(((CarInfo)list.get(i)).getCarNumber());
			data.add(record);
		}
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("data", data);
		result.put("total", total);
		return JsonUtil.objectToJson(result);
	}
	
	/**
	 * 改变类型
	 * @param movie
	 * @return
	 */
	/*public static List<T> change(List<T> u){
		List<UseInfo> show = new ArrayList<UseInfo>();
		for(UseInfo m: u){
			UseInfo s = new UseInfo();
			String c = m.getCarNumber();
			s.setDriver(m.getDriver());
			s.setRemark(m.getRemark());
			s.setReturnDate(m.getReturnDate());
			s.setUseCause(m.getUseCause());
			s.setUseGuid(m.getUseGuid());
			s.setUseDate(m.getReturnDate());
			s.setUsePerson(m.getUsePerson());
			s.setCarNumber(c);
			show.add(s);
		}
		return show;
	}*/
}

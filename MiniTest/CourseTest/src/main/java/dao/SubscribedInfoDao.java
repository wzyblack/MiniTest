package dao;

import java.util.ArrayList;
import java.util.List;

import bean.SubscribedInfo;
import dao.util.JdbcHandler;

public class SubscribedInfoDao {

	public int getAllSubscribedInfo(String courseId,int totalHouse){
		String sql = "select count(salaryId) from SubscribedInfo where 1=1";
		List<Object> objects = new ArrayList<Object>();
		if(courseId != null && courseId.trim().equals("")){
			sql += " and courseId=? ";
			objects.add(courseId);
		}
		if(totalHouse > 0){
			sql += " and actualHours=?";
			objects.add(totalHouse);
		}
		Object[] obj = null;
		if(!objects.isEmpty()){
			obj = objects.toArray();
		}
		return JdbcHandler.getNumSingle(sql, obj);
	}
	
	public List<SubscribedInfo> getSubscribedInfo(int pageSize,int pageCount,String courseId,int totalHouse){
		String sql = "select salaryId,courseId,(select courseName from course where id = courseId) as courseName,"
				+ "endTime,startTime,actualHours,note from subscribedinfo where 1=1 ";
		List<Object> objects = new ArrayList<Object>();
		
		if(courseId != null && courseId.trim().equals("")){
			sql += " and courseId=? ";
			objects.add(courseId);
		}
		if(totalHouse > 0){
			sql += " and actualHours=?";
			objects.add(totalHouse);
		}
		sql += " limit ?,? ";
		objects.add((pageSize-1)*pageCount);
		objects.add(pageCount);
		Object[] obj = null;
		if(!objects.isEmpty()){
			obj = objects.toArray();
		}
		
		return JdbcHandler.getExecuteQueryList(sql, obj, SubscribedInfo.class);
	}
	
	public boolean deleteSub(String[] ids){
		String sql = "delete from subscribedinfo where salaryID in(?";
		if(ids != null && ids.length>1){
			for(int i = 2; i<ids.length; i++){
				sql += ",?";
			}
		}
		sql +=")";
		Object[] objects = ids;
		return JdbcHandler.executeUpdate(sql, objects);
	}
	
	public boolean addSubscribed(SubscribedInfo subscribedInfo){
		String sql1 = "insert into subscribedinfo(salaryId,courseId,endTime,startTime,actualHours,note) values(?,?,?,?,?,?)";
		String sql2 = "insert into subscribedinfo(salaryId,courseId,endTime,startTime,actualHours) values(?,?,?,?,?)";
		String sql3 = sql2;
		List<Object> sInfos = new ArrayList<Object>();
		sInfos.add(subscribedInfo.getSalaryId());
		sInfos.add(subscribedInfo.getCourseId());
		sInfos.add(subscribedInfo.getEndTime());
		sInfos.add(subscribedInfo.getStartTime());
		sInfos.add(subscribedInfo.getActualHours());
		if(subscribedInfo.getNote() != null ){
			sql3 = sql1;
			sInfos.add(subscribedInfo.getNote());
		}
		return JdbcHandler.executeUpdate(sql3,sInfos.toArray());
	}
}

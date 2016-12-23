package dao;

import java.util.ArrayList;
import java.util.List;

import dao.util.JdbcHandler;
import bean.CourseInfo;
import bean.CourseNameModel;

public class CourseInfoDao {

	public List<CourseInfo> getCourseInfo(int pageSize,int pageNum,String courseName,int type){
		String sql = "select id,courseName,startTime,suitable,type,totalHouse,hotLevel,selectedCount,note from course where 1=1 ";
		List<Object> objects = new ArrayList<Object>();
		if(courseName != null && !courseName.trim().equals("")){
			sql += "and courseName like ?";
			objects.add(courseName);
		}
		if(type != 0){
			sql += " and type = ?";
			objects.add(type);
		}
		sql += " limit ?,?";
		objects.add((pageNum-1)*pageSize);
		objects.add(pageSize);
		return JdbcHandler.getExecuteQueryList(sql, objects.toArray(), CourseInfo.class);
	}
	
	public boolean addCourse(CourseInfo courseInfo){
		String sql1 = "insert into course(id,courseName,startTime,suitable,type,totalHouse,hotLevel,selectedCount) values(?,?,?,?,?,?,?,?)";
		String sql2 = "insert into course(id,courseName,startTime,suitable,type,totalHouse,hotLevel,selectedCount,note) values(?,?,?,?,?,?,?,?,?)";
		String sql3 = null;
		ArrayList<Object> objects = new ArrayList<Object>();
		objects.add(courseInfo.getId());
		objects.add(courseInfo.getCourseName());
		objects.add(courseInfo.getStartTime());
		objects.add(courseInfo.getSuitable());
		objects.add(courseInfo.getType());
		objects.add(courseInfo.getTotalHouse());
		objects.add(0);
		objects.add(0);
		if(courseInfo.getNote()==null || courseInfo.getNote().trim().equals("")){
			sql3 = sql1;
		}
		else{
			sql3 = sql2;
			objects.add(courseInfo.getNote());
		}
		return JdbcHandler.executeUpdate(sql3, objects.toArray());
	}
	
	public boolean editCourseInfo(CourseInfo courseInfo){
		String sql = "update Course set courseName=?,starttime=?,suitable=?,type=?,totalHouse=?,hotLevel=?,selectedCount=?,note=? where id=?";
		ArrayList<Object> objects = new ArrayList<Object>();
		objects.add(courseInfo.getCourseName());
		objects.add(courseInfo.getStartTime());
		objects.add(courseInfo.getSuitable());
		objects.add(courseInfo.getType());
		objects.add(courseInfo.getTotalHouse());
		objects.add(courseInfo.getHotLevel());
		objects.add(courseInfo.getSelectedCount());
		if(courseInfo.getNote() != null){
			objects.add(courseInfo.getNote());
		}
		else{
			objects.add("");
		}
		objects.add(courseInfo.getId());
		return JdbcHandler.executeUpdate(sql, objects.toArray());
	}
	
	public int getAllCount(String courseName){
		String sql = "select count(id) from course where 1=1 ";
		List<Object> objects = new ArrayList<Object>();
		if(courseName != null && !courseName.trim().equals("")){
			sql += "and courseName like ?";
			objects.add(courseName);
		}
		Object[] obj = null; 
		if(!objects.isEmpty()){
			obj = new Object[]{courseName};
		}
		
		int count = JdbcHandler.getNumSingle(sql, obj);
		return count;
	}
	
	public boolean deleteCourse(String[] ids){
		String sql = "delete from course where id in(?";
		if(ids != null && ids.length>1){
			for(int i = 2; i<ids.length; i++){
				sql += ",?";
			}
		}
		sql +=")";
		Object[] objects = ids;
		return JdbcHandler.executeUpdate(sql, objects);
	}
	
	public CourseInfo getCourseInfoById(String id){
		String sql = "select id,courseName,startTime,suitable,type,totalHouse,hotLevel,selectedCount,note from course where id=?";
		return JdbcHandler.getExecuteQuerySingle(sql, new Object[]{id}, CourseInfo.class);
	}
	
	public boolean updateCourseInfo(int level,int count,String id){
		String sql = "update course set hotLevel=hotLevel+?,selectedCount=selectedCount+? where id=?";
		return JdbcHandler.executeUpdate(sql, new Object[]{level*5,count,id});
	}
	
	public List<CourseNameModel> getCourseName(){
		String sql = "select id,courseName from course";
		return JdbcHandler.getExecuteQueryList(sql, null, CourseNameModel.class);
	}
}

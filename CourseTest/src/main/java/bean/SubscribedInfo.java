package bean;

import java.util.Date;

public class SubscribedInfo {

	private String salaryId;
	private String courseId;
	private Date startTime;
	private Date endTime;
	private int actualHours;
	private String note;
	private String courseName;
	public SubscribedInfo() {
		super();
	}
	public SubscribedInfo(String salaryId, String courseId, Date startTime,
			Date endTime, int actualHours, String note) {
		super();
		this.salaryId = salaryId;
		this.courseId = courseId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.actualHours = actualHours;
		this.note = note;
	}
	public String getSalaryId() {
		return salaryId;
	}
	public void setSalaryId(String salaryId) {
		this.salaryId = salaryId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getActualHours() {
		return actualHours;
	}
	public void setActualHours(int actualHours) {
		this.actualHours = actualHours;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
}

package bean;

import java.util.Date;

public class CourseInfo {
	private String id;
	private String courseName;
	private Date startTime;
	private String Note;
	private int suitable;
	private int type;
	private int totalHouse;
	private int hotLevel;
	private int selectedCount;
	public CourseInfo() {
		
	}
	
	public CourseInfo(String id, String courseName, Date satrtTime,
			String note, int suitable, int type, int totalHouse, int hotLevel,
			int selectedCount) {
		this.id = id;
		this.courseName = courseName;
		this.startTime = satrtTime;
		Note = note;
		this.suitable = suitable;
		this.type = type;
		this.totalHouse = totalHouse;
		this.hotLevel = hotLevel;
		this.selectedCount = selectedCount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getNote() {
		return Note;
	}
	public void setNote(String note) {
		Note = note;
	}
	public int getSuitable() {
		return suitable;
	}
	public void setSuitable(int suitable) {
		this.suitable = suitable;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getTotalHouse() {
		return totalHouse;
	}
	public void setTotalHouse(int totalHouse) {
		this.totalHouse = totalHouse;
	}
	public int getHotLevel() {
		return hotLevel;
	}
	public void setHotLevel(int hotLevel) {
		this.hotLevel = hotLevel;
	}
	public int getSelectedCount() {
		return selectedCount;
	}
	public void setSelectedCount(int selectedCount) {
		this.selectedCount = selectedCount;
	}

}

package dao.util;

public class ColumnInfo {
	//列名
	private String colunmName;
	//列的类型
	private int colnumType;
	
	public ColumnInfo(String colunmName, int colnumType) {
		super();
		this.colunmName = colunmName;
		this.colnumType = colnumType;
	}
	public String getColunmName() {
		return colunmName;
	}
	public void setColunmName(String colunmName) {
		this.colunmName = colunmName;
	}
	public int getColnumType() {
		return colnumType;
	}
	public void setColnumType(int colnumType) {
		this.colnumType = colnumType;
	}
}

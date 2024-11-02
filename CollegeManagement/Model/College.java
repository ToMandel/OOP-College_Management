package Model;

import java.io.Serializable;

public class College implements Serializable {
	private static final long serialVersionUID = 1L;
	private String colName;

	public College(String colName) {
		this.colName = colName;
	}

	public String getColName() {
		return colName;
	}

	@Override
	public String toString() {
		return colName ;
	}
}

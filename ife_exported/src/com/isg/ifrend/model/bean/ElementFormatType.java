package com.isg.ifrend.model.bean;

public enum ElementFormatType {

	CHARACTER(1, "Character"),
	ALPHANUMERIC(2, "Alpha-Numeric"),
	NUMERIC(3, "Numeric"),
	DATE(4, "Date"),
	AMOUNT(5, "Amount");

	private int id;
	private String desc;

	private ElementFormatType(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public static ElementFormatType getElementFormatTypeByID(int id) {
		for (ElementFormatType elementFormatType : values()) {
			if (elementFormatType.getId() == id)
				return elementFormatType;
		}
		return null;
	}

	public static String getDesc(int id) {
		for (ElementFormatType elementFormatType : values()) {
			if (elementFormatType.getId() == id)
				return elementFormatType.getDesc();
		}
		return null;
	}
}

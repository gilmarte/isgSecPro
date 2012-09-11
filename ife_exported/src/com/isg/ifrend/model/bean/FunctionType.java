package com.isg.ifrend.model.bean;

/**
 * 
 * @author kristine.furto
 * 
 */
public enum FunctionType {

	CRITERIA(1, "Criteria"), MLI(2, "MLI"), SCRIPT(3, "Script");

	private int id;
	private String desc;

	private FunctionType(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public static FunctionType getStatusTypeByID(int id) {
		for (FunctionType functionType : values()) {
			if (functionType.getId() == id)
				return functionType;
		}
		return null;
	}

	public static String getDescByID(int id) {
		for (FunctionType functionType : values()) {
			if (functionType.getId() == id)
				return functionType.getDesc();
		}
		return null;
	}
}

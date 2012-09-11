package com.isg.ifrend.model.bean;

public enum ElementType {
	GLOBAL(1, "Global"),
	FUNCTION(2, "Function");

	private int id;
	private String desc;

	private ElementType(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public static ElementType getElementTypeByID(int id) {
		for (ElementType elementType : values()) {
			if (elementType.getId() == id)
				return elementType;
		}
		return null;
	}

	public static String getDesc(int id) {
		for (ElementType elementType : values()) {
			if (elementType.getId() == id)
				return elementType.getDesc();
		}
		return null;
	}
	
	public static Integer getID_ByDesc(String desc){
		for(ElementType elementType:values()){
			if(elementType.getDesc().equalsIgnoreCase(desc))
				return elementType.getId();
		}
		return null;
	}
}

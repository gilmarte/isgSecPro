package com.isg.ifrend.model.bean;

/**
 * 
 * @author kristine.furto
 * 
 */
public enum ActionType {

	ALL(0, "All"), ADD(1, "Add"), UPDATE(2, "Update"), DELETE(3, "Delete");

	private int id;
	private String desc;

	private ActionType(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public static String getDesc(int id) {
		for (ActionType t : values()) {
			if (t.getId() == id) {
				return t.getDesc();
			}
		}
		return null;
	}

	public static int getId(String value) {
		for (ActionType t : values()) {
			if (t.getDesc().equals(value)) {
				return t.getId();
			}
		}
		return 0;
	}
}
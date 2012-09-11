package com.isg.ifrend.model.bean;

public enum StatusActionType {

	ALL(0, "All"),

	ACTIVE(1, "Active"),

	PENDING_UPDATE(2, "Pending Authorization - Update"),

	PENDING_DELETE(3, "Pending Authorization - Delete");

	private int id;
	private String desc;

	private StatusActionType(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public static int getId(String desc) {
		for (StatusActionType t : values()) {
			if (t.getDesc().equals(desc)) {
				return t.getId();
			}
		}
		return 0;
	}

	public static String getDesc(int id) {
		for (StatusActionType t : values()) {
			if (t.getId() == id) {
				return t.getDesc();
			}
		}
		return null;
	}
}

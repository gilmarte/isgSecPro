package com.isg.ifrend.model.bean;

public enum EntityType {

	ALL(0, "All"),
	ELEMENT(1, "Element"),
	CRITERIA(2, "Criteria"),
	MLI(3, "MLI"),
	SCRIPT(4, "Script"),
	CODETYPE(5, "Code Table"),
	LABEL(6, "Label");
	

	private int id;
	private String desc;

	/**
	 * 
	 * @param id
	 * @param desc
	 */
	private EntityType(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public static EntityType getEntityTypeByID(int id) {
		for (EntityType entityEnum : values()) {
			if (entityEnum.getId() == id)
				return entityEnum;
		}
		return null;
	}

	public static String getDesc(int id) {
		for (EntityType entityEnum : values()) {
			if (entityEnum.getId() == id)
				return entityEnum.getDesc();
		}
		return null;
	}

	public static int getId(String value) {
		for (EntityType t : values()) {
			if (t.getDesc().equals(value)) {
				return t.getId();
			}
		}
		return 0;
	}
}

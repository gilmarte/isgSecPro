package com.isg.ifrend.model.bean;

/**
 * 
 * @author kristine.furto
 * 
 */
public enum StatusType {

	ALL(0, "All"),	// Status ID for ALL should be less than all the other status IDs!
	
	ACTIVE(1, "Active"),

	PENDING(2, "Pending Authorization"),

	DELETED(3, "Deleted"),

	APPROVED(4, "Approved"),

	REJECTED(5, "Rejected"),

	CANCELLED(6, "Cancelled");

	private int id;
	private String desc;

	private StatusType(int id, String desc) {
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
		for (StatusType t : values()) {
			if (t.getId() == id) {
				return t.getDesc();
			}
		}
		return null;
	}

	public static int getId(String value) {
		for (StatusType t : values()) {
			if (t.getDesc().equals(value)) {
				return t.getId();
			}
		}
		return 0;
	}

	public static boolean isStatusAuthorized(int statusID) {
		return (StatusType.ACTIVE.getId() == statusID)
				|| (StatusType.APPROVED.getId() == statusID)
				|| (StatusType.REJECTED.getId() == statusID);
	}
}
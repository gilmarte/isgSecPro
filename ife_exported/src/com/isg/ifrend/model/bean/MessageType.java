package com.isg.ifrend.model.bean;


/**
 * Fixed set of Message Type values. 
 * 
 * @author kristine.furto
 *
 */
public enum MessageType {
	INFORMATION(1, "Information"),
	COMPLIANCE(2, "Compliance"),
	OPTIONAL(3, "Optional"),
	NONE(4, "None");

	private int id;
	private String desc;

	/**
	 * 
	 * @param id
	 * @param desc
	 */
	private MessageType(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public static MessageType getMessageTypeByID(int id) {
		for (MessageType messageType : values()) {
			if (messageType.getId() == id)
				return messageType;
		}
		return null;
	}

	public static String getDesc(int id) {
		for (MessageType messageType : values()) {
			if (messageType.getId() == id)
				return messageType.getDesc();
		}
		return null;
	}
	
	public static Integer getID_ByDesc(String desc) {
		for (MessageType messageType : values()) {
			if (messageType.getDesc().equalsIgnoreCase(desc))
				return messageType.getId();
		}
		return null;
	}
}
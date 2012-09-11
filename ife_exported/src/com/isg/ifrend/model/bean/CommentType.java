package com.isg.ifrend.model.bean;

/**
 * Fixed set of Comment Type values. 
 * 
 * @author kristine.furto
 *
 */
public enum CommentType {
	HISTORY(1, "History"),
	QUEUE(2, "Queue"),
	NONE(3, "None");
	
	
	private int id;
	private String desc;

	private CommentType(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}
	
	
	public static CommentType getCommentTypeByID(int id) {
		for (CommentType commentType : values()) {
			if (commentType.getId() == id)
				return commentType;
		}
		return null;
	}

	public static String getDesc(int id) {
		for (CommentType commentType : values()) {
			if (commentType.getId() == id)
				return commentType.getDesc();
		}
		return null;
	}
	
	public static Integer getID_ByDesc(String desc){
		for(CommentType commentType:values()){
			if(commentType.getDesc().equalsIgnoreCase(desc))
				return commentType.getId();
		}
		return null;
	}
	
}
package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class Priority implements Serializable {

	private static final long serialVersionUID = 1L;

	private int priorityID;
	private String priorityDesc;

	public Priority(){}

	public Priority(int priorityID, String priorityDesc) {
		super();
		this.priorityID = priorityID;
		this.priorityDesc = priorityDesc;
	}

	public int getPriorityID() {
		return priorityID;
	}

	public void setPriorityID(int priorityID) {
		this.priorityID = priorityID;
	}

	public String getPriorityDesc() {
		return priorityDesc;
	}

	public void setPriorityDesc(String priorityDesc) {
		this.priorityDesc = priorityDesc;
	}

}

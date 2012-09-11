package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class UserField implements Serializable {

	private static final long serialVersionUID = 1L;

	private int userFieldID;
	private String userFieldDesc;

	public UserField(){}

	public UserField(int userFieldID, String userFieldDesc) {
		super();
		this.userFieldID = userFieldID;
		this.userFieldDesc = userFieldDesc;
	}
	
	public int getUserFieldID() {
		return userFieldID;
	}

	public void setUserFieldID(int userFieldID) {
		this.userFieldID = userFieldID;
	}

	public String getUserFieldDesc() {
		return userFieldDesc;
	}

	public void setUserFieldDesc(String userFieldDesc) {
		this.userFieldDesc = userFieldDesc;
	}

}

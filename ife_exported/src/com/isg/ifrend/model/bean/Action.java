package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class Action implements Serializable {

	private static final long serialVersionUID = 1L;

	private int action_id;
	private String action_desc;
	private int action_category;
	
	public Action(){}
	
	public Action(int action_id, String action_desc, int action_category) {
		super();
		this.action_id = action_id;
		this.action_desc = action_desc;
		this.action_category = action_category;
	}

	public int getAction_id() {
		return action_id;
	}

	public void setAction_id(int action_id) {
		this.action_id = action_id;
	}

	public String getAction_desc() {
		return action_desc;
	}

	public void setAction_desc(String action_desc) {
		this.action_desc = action_desc;
	}

	public int getAction_category() {
		return action_category;
	}

	public void setAction_category(int action_category) {
		this.action_category = action_category;
	}
}

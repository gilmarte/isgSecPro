package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class GlobalMaxID implements Serializable {

	private static final long serialVersionUID = 1L;

	private int globalMax_id;
	
	public GlobalMaxID(){}

	public GlobalMaxID(int globalMax_id) {
		super();
		this.globalMax_id = globalMax_id;
	}

	public int getGlobalMax_id() {
		return globalMax_id;
	}

	public void setGlobalMax_id(int globalMax_id) {
		this.globalMax_id = globalMax_id;
	}
}

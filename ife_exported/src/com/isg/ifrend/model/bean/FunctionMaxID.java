package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class FunctionMaxID implements Serializable {

	private static final long serialVersionUID = 1L;

	private int functionMax_id;
	
	public FunctionMaxID(){}

	public FunctionMaxID(int functionMax_id) {
		super();
		this.functionMax_id = functionMax_id;
	}

	public int getFunctionMax_id() {
		return functionMax_id;
	}

	public void setFunctionMax_id(int functionMax_id) {
		this.functionMax_id = functionMax_id;
	}
}

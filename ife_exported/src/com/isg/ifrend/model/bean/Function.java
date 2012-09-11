package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class Function implements Serializable {

	private static final long serialVersionUID = 1L;

	private String function_code;
	private String function_desc;
	
	public Function(){}
	
	public Function(String function_code, String function_desc) {
		super();
		this.function_code = function_code;
		this.function_desc = function_desc;
	}

	public String getFunction_code() {
		return function_code;
	}

	public void setFunction_code(String function_code) {
		this.function_code = function_code;
	}

	public String getFunction_desc() {
		return function_desc;
	}

	public void setFunction_desc(String function_desc) {
		this.function_desc = function_desc;
	}

}

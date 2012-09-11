package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class Operator implements Serializable {

	private static final long serialVersionUID = 1L;

	private String operator_code;
	private String operator_desc;
	
	public String getOperator_code() {
		return operator_code;
	}
	public void setOperator_code(String operator_code) {
		this.operator_code = operator_code;
	}
	public String getOperator_desc() {
		return operator_desc;
	}
	public void setOperator_desc(String operator_desc) {
		this.operator_desc = operator_desc;
	}

}

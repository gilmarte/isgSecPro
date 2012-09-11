package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class ElementFormats implements Serializable {

	private static final long serialVersionUID = 1L;
	private int elementformat_id;
	private String elementFormat_desc;

	public ElementFormats(){}

	public ElementFormats(int elementformat_id, String elementFormat_desc) {
		super();
		this.elementformat_id = elementformat_id;
		this.elementFormat_desc = elementFormat_desc;
	}

	public int getElementformat_id() {
		return elementformat_id;
	}

	public void setElementformat_id(int elementformat_id) {
		this.elementformat_id = elementformat_id;
	}

	public String getElementFormat_desc() {
		return elementFormat_desc;
	}

	public void setElementFormat_desc(String elementFormat_desc) {
		this.elementFormat_desc = elementFormat_desc;
	}
}

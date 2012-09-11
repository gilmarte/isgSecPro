package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class GenericComboItemBean implements Serializable {

	private static final long serialVersionUID = -4743169966174884492L;

	private int id;
	private String desc;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}

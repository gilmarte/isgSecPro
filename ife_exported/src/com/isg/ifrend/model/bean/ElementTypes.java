package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class ElementTypes implements Serializable {

	private static final long serialVersionUID = 1L;

	private int elementtype_id;
	private String elementtype_desc;

	public ElementTypes(){}

	public ElementTypes(Integer elementtype_id, String elementtype_desc) {
		super();
		this.elementtype_id = elementtype_id;
		this.elementtype_desc = elementtype_desc;
	}


	public Integer getElementtype_id() {
		return elementtype_id;
	}

	public void setElementtype_id(int elementtype_id) {
		this.elementtype_id = elementtype_id;
	}

	public String getElementtype_desc() {
		return elementtype_desc;
	}

	public void setElementtype_desc(String elementtype_desc) {
		this.elementtype_desc = elementtype_desc;
	}

}

package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class DateFormats implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer dateformat_id;
	private String dateformat_desc;

	public DateFormats(){}

	public DateFormats(Integer dateformat_id, String dateformat_desc) {
		super();
		this.dateformat_id = dateformat_id;
		this.dateformat_desc = dateformat_desc;
	}

	public Integer getDateformat_id() {
		return dateformat_id;
	}

	public void setDateformat_id(Integer dateformat_id) {
		this.dateformat_id = dateformat_id;
	}

	public String getDateformat_desc() {
		return dateformat_desc;
	}

	public void setDateformat_desc(String dateformat_desc) {
		this.dateformat_desc = dateformat_desc;
	}
}

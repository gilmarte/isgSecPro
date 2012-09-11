package com.isg.ifrend.model.bean;

import java.io.Serializable;

public class BusinessEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String bus_ent_code;
	private String bus_ent_desc;
	
	public String getBus_ent_code() {
		return bus_ent_code;
	}
	public void setBus_ent_code(String bus_ent_code) {
		this.bus_ent_code = bus_ent_code;
	}
	public String getBus_ent_desc() {
		return bus_ent_desc;
	}
	public void setBus_ent_desc(String bus_ent_desc) {
		this.bus_ent_desc = bus_ent_desc;
	}
	
	
}

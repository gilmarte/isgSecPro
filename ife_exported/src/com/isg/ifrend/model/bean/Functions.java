package com.isg.ifrend.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_sa_ref_functionslist")
public class Functions implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer function_id;
	private String description;
	private String function_name;
		
	//Getter Setter
	@Id
	@Column(name="function_id")
	public Integer getFunction_id() {
		return function_id;
	}
	public void setFunction_id(Integer function_id) {
		this.function_id = function_id;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="function_name")
	public String getFunction_name() {
		return function_name;
	}
	public void setFunction_name(String function_name) {
		this.function_name = function_name;
	}
	
}

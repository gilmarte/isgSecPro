package com.isg.ifrend.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="TMP_TBL_SA_SELECTEDFUNCTIONS")
public class SelectedFunctions implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer sf_id;
	private String group_id;
	private Integer function_id;
	private String  description;
	private TmpSaUserGroup tmpsausergroup;
	private String function_name;



	/*Constructors*/
	public SelectedFunctions(){}	

	public SelectedFunctions(String group_id, Integer function_id, String description, String function_name, TmpSaUserGroup tmpusersagroup){

		this.group_id = group_id;
		this.function_id = function_id;
		this.description = description;
		this.function_name = function_name;
		this.tmpsausergroup = tmpusersagroup;		
	}

	public SelectedFunctions(Integer sf_id, String group_id, Integer function_id, String description, TmpSaUserGroup tmpusersagroup){

		this.sf_id = sf_id;
		this.group_id = group_id;
		this.function_id = function_id;
		this.description = description;
		this.tmpsausergroup = tmpusersagroup;
	}


	/*Getters and Setters Declaration*/

	@Id
	@Column(name="sf_id")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	public Integer getSf_id() {
		return sf_id;
	}
	public void setSf_id(Integer sf_id) {
		this.sf_id = sf_id;
	}
	
	@Column(name="group_id")
	public String getGroup_id() {
		return group_id;
	}	
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

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

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="group_id", referencedColumnName="group_id", insertable=false, updatable=false)
	public TmpSaUserGroup getTmpsausergroup() {
		return tmpsausergroup;
	}
	public void setTmpsausergroup(TmpSaUserGroup tmpsausergroup) {
		this.tmpsausergroup = tmpsausergroup;
	}


}

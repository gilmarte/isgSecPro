package com.isg.ifrend.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TBL_HST_SA_USERGROUP")
public class SaUserGroupHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer seq_id;
	private String group_id;
	private String description;
	/*private String vcategory;*/
	private String status;
	private String action;
	private String maker_id;
	private String checker_id;
	private String creation_dt;
	private String decision_dt;
	private String modified_dt;
	private String modifier_id;
	
	/*private List<SaSelectedFunctions>sa_selectedfunctions = new ArrayList<SaSelectedFunctions>();*/

	public SaUserGroupHistory(){
		
	}
	
	/*
	 * Getters and Setters Declaration
	 * */
		
	
	@Id
	@Column(name="seq_id")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy="increment")
	public Integer getSeq_id() {
		return seq_id;
	}

	public void setSeq_id(Integer seq_id) {
		this.seq_id = seq_id;
	}
	
	
	@Column(name="group_id")
	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
			
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	
	/*@Column(name="vcategory")
	public String getVcategory() {
		return vcategory;
	}

	public void setVcategory(String vcategory) {
		this.vcategory = vcategory;
	}*/

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="action")
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	@Column(name="maker_id")
	public String getMaker_id() {
		return maker_id;
	}

	public void setMaker_id(String maker_id) {
		this.maker_id = maker_id;
	}
	
	@Column(name="checker_id")
	public String getChecker_id() {
		return checker_id;
	}

	public void setChecker_id(String checker_id) {
		this.checker_id = checker_id;
	}

	@Column(name="creation_dt")
	public String getCreation_dt() {
		return creation_dt;
	}

	public void setCreation_dt(String creation_dt) {
		this.creation_dt = creation_dt;
	}

	@Column(name="decision_dt")
	public String getDecision_dt() {
		return decision_dt;
	}

	public void setDecision_dt(String decision_dt) {
		this.decision_dt = decision_dt;
	}

	@Column(name="modified_dt")
	public String getModified_dt() {
		return modified_dt;
	}

	public void setModified_dt(String modified_dt) {
		this.modified_dt = modified_dt;
	}
	
	@Column(name="modifier_id")
	public String getModifier_id() {
		return modifier_id;
	}

	public void setModifier_id(String modifier_id) {
		this.modifier_id = modifier_id;
	}

	/*@OneToMany(mappedBy="sausergroup", fetch=FetchType.LAZY , cascade=CascadeType.ALL, orphanRemoval=true)
	public List<SaSelectedFunctions> getSa_selectedfunctions() {
		return sa_selectedfunctions;
	}*/

	/*public void setSa_selectedfunctions(
			List<SaSelectedFunctions> sa_selectedfunctions) {
		this.sa_selectedfunctions = sa_selectedfunctions;
	}*/
	
}

package com.isg.ifrend.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class HistCriteria extends Global implements Serializable {

	private static final long serialVersionUID = 4251080614570146244L;

	private int trans_id;
	private String criteria_id;
	private String country_code;
	private String function_id;
	private int user_field_id;
	private int priority_id;
	private String description;
	private int pass_action_id;
	private int pass_messagetype_id;
	private int pass_lettercode_id;
	private int pass_commenttype_id;
	private String pass_message;
	private String pass_comment;
	private int fail_action_id;
	private int fail_messagetype_id;
	private int fail_lettercode_id;
	private int fail_commenttype_id;
	private String fail_message;
	private String fail_comment;
	
	private int element_id;
	private int elementtype_id;
	private String element_desc;

	private HistEnhancedCriteria histEnhancedCriteria;
	private int enhancedcriteria_id;

	private Set<HistEnhancedCriteria> histEnhancedCriteriaSet = new HashSet<HistEnhancedCriteria>();

	/* Common - START */
	private int statusID;
	private int actionID;
	private String creatorUserID;
	private Date dateCreated;
	private String approverUserID;
	private Date dateApproved;
	private String lastModifierUserID;
	private Date dateLastModified;
	/* Common - END */
	
	public HistCriteria(){}

	public HistCriteria(TempCriteria tempCriteria){
		this.criteria_id = tempCriteria.getCriteria_id();
		this.country_code= tempCriteria.getCountry_code();
		this.function_id = tempCriteria.getFunction_id();
		this.user_field_id = tempCriteria.getUser_field_id();
		this.priority_id = tempCriteria.getPriority_id();
		this.description = tempCriteria.getDescription();
		this.pass_action_id = tempCriteria.getPass_action_id();
		this.pass_messagetype_id = tempCriteria.getPass_messagetype_id();
		this.pass_lettercode_id = tempCriteria.getPass_lettercode_id();
		this.pass_commenttype_id = tempCriteria.getPass_commenttype_id();
		this.pass_message = tempCriteria.getPass_message();
		this.pass_comment = tempCriteria.getPass_comment();
		this.fail_action_id = tempCriteria.getFail_action_id();
		this.fail_messagetype_id = tempCriteria.getFail_messagetype_id();
		this.fail_lettercode_id = tempCriteria.getFail_lettercode_id();
		this.fail_commenttype_id = tempCriteria.getFail_commenttype_id();
		this.fail_message = tempCriteria.getFail_message();
		this.fail_comment = tempCriteria.getFail_comment();
		this.enhancedcriteria_id = tempCriteria.getEnhancedcriteria_id();
		this.element_id = tempCriteria.getElement_id();
		this.elementtype_id = tempCriteria.getElementtype_id();
		this.element_desc = tempCriteria.getElement_desc();
		
		/* Common - START */
		this.statusID = tempCriteria.getStatusID();
		this.actionID = tempCriteria.getActionID();
		this.creatorUserID = tempCriteria.getCreatorUserID();
		this.dateCreated = tempCriteria.getDateCreated();
		this.approverUserID = tempCriteria.getApproverUserID();
		this.dateApproved = tempCriteria.getDateApproved();
		this.lastModifierUserID = tempCriteria.getLastModifierUserID();
		this.dateLastModified = tempCriteria.getDateLastModified();
		/* Common - END */
		
		/*List<TempEnhancedCriterion> tmpList = new ArrayList<TempEnhancedCriterion>(tempCriteria.getTempEnhancedCriterionSet());
		
		for (int index=0; index<tmpList.size(); index++){
			TempEnhancedCriterion teCriterion = (TempEnhancedCriterion)tmpList.get(index);
			HistEnhancedCriteria heCriterion = new HistEnhancedCriteria(teCriterion);
			this.histEnhancedCriteriaSet.add(heCriterion);
		}*/
		
		  for (TempEnhancedCriterion teCriterion : tempCriteria.getTempEnhancedCriterionSet()) {
			   addToHistEnhancedCriteriaSet(new HistEnhancedCriteria(teCriterion));
			  }
		
	}

	public void addToHistEnhancedCriteriaSet(HistEnhancedCriteria histEnhancedCriteria) {
		histEnhancedCriteriaSet.add(histEnhancedCriteria);
		histEnhancedCriteria.setHistCriteria(this);
	}
	
	public String getCriteria_id() {
		return criteria_id;
	}

	public void setCriteria_id(String criteria_id) {
		this.criteria_id = criteria_id;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getFunction_id() {
		return function_id;
	}

	public void setFunction_id(String function_id) {
		this.function_id = function_id;
	}

	public int getUser_field_id() {
		return user_field_id;
	}

	public void setUser_field_id(int user_field_id) {
		this.user_field_id = user_field_id;
	}

	public int getPriority_id() {
		return priority_id;
	}

	public void setPriority_id(int priority_id) {
		this.priority_id = priority_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPass_action_id() {
		return pass_action_id;
	}

	public void setPass_action_id(int pass_action_id) {
		this.pass_action_id = pass_action_id;
	}

	public int getPass_messagetype_id() {
		return pass_messagetype_id;
	}

	public void setPass_messagetype_id(int pass_messagetype_id) {
		this.pass_messagetype_id = pass_messagetype_id;
	}

	public int getPass_lettercode_id() {
		return pass_lettercode_id;
	}

	public void setPass_lettercode_id(int pass_lettercode_id) {
		this.pass_lettercode_id = pass_lettercode_id;
	}

	public int getPass_commenttype_id() {
		return pass_commenttype_id;
	}

	public void setPass_commenttype_id(int pass_commenttype_id) {
		this.pass_commenttype_id = pass_commenttype_id;
	}

	public String getPass_message() {
		return pass_message;
	}

	public void setPass_message(String pass_message) {
		this.pass_message = pass_message;
	}

	public String getPass_comment() {
		return pass_comment;
	}

	public void setPass_comment(String pass_comment) {
		this.pass_comment = pass_comment;
	}

	public int getFail_action_id() {
		return fail_action_id;
	}

	public void setFail_action_id(int fail_action_id) {
		this.fail_action_id = fail_action_id;
	}

	public int getFail_messagetype_id() {
		return fail_messagetype_id;
	}

	public void setFail_messagetype_id(int fail_messagetype_id) {
		this.fail_messagetype_id = fail_messagetype_id;
	}

	public int getFail_lettercode_id() {
		return fail_lettercode_id;
	}

	public void setFail_lettercode_id(int fail_lettercode_id) {
		this.fail_lettercode_id = fail_lettercode_id;
	}

	public int getFail_commenttype_id() {
		return fail_commenttype_id;
	}

	public void setFail_commenttype_id(int fail_commenttype_id) {
		this.fail_commenttype_id = fail_commenttype_id;
	}

	public String getFail_message() {
		return fail_message;
	}

	public void setFail_message(String fail_message) {
		this.fail_message = fail_message;
	}

	public String getFail_comment() {
		return fail_comment;
	}

	public void setFail_comment(String fail_comment) {
		this.fail_comment = fail_comment;
	}

	public HistEnhancedCriteria getHistEnhancedCriteria() {
		return histEnhancedCriteria;
	}

	public void setHistEnhancedCriteria(HistEnhancedCriteria histEnhancedCriteria) {
		this.histEnhancedCriteria = histEnhancedCriteria;
	}

	public Set<HistEnhancedCriteria> getHistEnhancedCriteriaSet() {
		return histEnhancedCriteriaSet;
	}

	public void setHistEnhancedCriteriaSet(
			Set<HistEnhancedCriteria> histEnhancedCriteriaSet) {
		this.histEnhancedCriteriaSet = histEnhancedCriteriaSet;
	}

	public int getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(int trans_id) {
		this.trans_id = trans_id;
	}
	
	public int getEnhancedcriteria_id() {
		return enhancedcriteria_id;
	}

	public void setEnhancedcriteria_id(int enhancedcriteria_id) {
		this.enhancedcriteria_id = enhancedcriteria_id;
	}

	public int getElement_id() {
		return element_id;
	}

	public void setElement_id(int element_id) {
		this.element_id = element_id;
	}

	public int getElementtype_id() {
		return elementtype_id;
	}

	public void setElementtype_id(int elementtype_id) {
		this.elementtype_id = elementtype_id;
	}

	public String getElement_desc() {
		return element_desc;
	}

	public void setElement_desc(String element_desc) {
		this.element_desc = element_desc;
	}

	/* Common - START */
	@Override
	public String getId() {
		return criteria_id;
	}

	@Override
	public void setId(String id) {
		criteria_id = id;
	}
	
	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public int getActionID() {
		return actionID;
	}

	public void setActionID(int actionID) {
		this.actionID = actionID;
	}

	public String getCreatorUserID() {
		return creatorUserID;
	}

	public void setCreatorUserID(String creatorUserID) {
		this.creatorUserID = creatorUserID;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getApproverUserID() {
		return approverUserID;
	}

	public void setApproverUserID(String approverUserID) {
		this.approverUserID = approverUserID;
	}

	public Date getDateApproved() {
		return dateApproved;
	}

	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}

	public String getLastModifierUserID() {
		return lastModifierUserID;
	}

	public void setLastModifierUserID(String lastModifierUserID) {
		this.lastModifierUserID = lastModifierUserID;
	}

	public Date getDateLastModified() {
		return dateLastModified;
	}

	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}
	
	@Override
	public String getDesc() {
		return description;
	}
	
	@Override
	public void setDesc(String desc) {
		description = desc;
	}

	@Override
	public String getEntityDesc() {
		return EntityType.CRITERIA.getDesc();
	}
	/* Common - END */
}

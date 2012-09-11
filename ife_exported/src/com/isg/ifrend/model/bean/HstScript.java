package com.isg.ifrend.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author kristine.furto
 * 
 */
public class HstScript extends Global implements Serializable {

	private static final long serialVersionUID = 806804247181139525L;

	private int hstScriptID;

	private String scriptID;

	// global configuration
	private String countryCode;

	private String functionCode;
	private int userFieldID;
	private int priorityID;
	private String desc;

	private int messageTypeID;
	private String message;
	private int commentTypeID;
	private String comment;
	private int statusID;
	private int actionID;
	private String creatorUserID;
	private Date dateCreated;
	private String approverUserID;
	private Date dateApproved;
	private String lastModifierUserID;
	private Date dateLastModified;

	private UserField userField;
	private Priority priority;

	private String statusDesc; // sherwin

	public HstScript() {
	}

	public HstScript(String scriptID, String countryCode, String functionCode,
			int userFieldID, int priorityID, String desc, int messageTypeID,
			String message, int commentTypeID, String comment, int statusID,
			int actionID, String creatorUserID, Date dateCreated,
			String approverUserID, Date dateApproved,
			String lastModifierUserID, Date dateLastModified) {
		this.scriptID = scriptID;
		this.countryCode = countryCode;
		this.functionCode = functionCode;
		this.userFieldID = userFieldID;
		this.priorityID = priorityID;
		this.desc = desc;
		this.messageTypeID = messageTypeID;
		this.message = message;
		this.commentTypeID = commentTypeID;
		this.comment = comment;
		this.statusID = statusID;
		this.actionID = actionID;
		this.creatorUserID = creatorUserID;
		this.dateCreated = dateCreated;
		this.approverUserID = approverUserID;
		this.dateApproved = dateApproved;
		this.lastModifierUserID = lastModifierUserID;
		this.dateLastModified = dateLastModified;
	}

	public HstScript(TmpScript g) {
		this(g.getScriptID(), g.getCountryCode(), g.getFunctionCode(), g
				.getUserFieldID(), g.getPriorityID(), g.getDesc(), g
				.getMessageTypeID(), g.getMessage(), g.getCommentTypeID(), g
				.getComment(), g.getStatusID(), g.getActionID(), g
				.getCreatorUserID(), g.getDateCreated(), g.getApproverUserID(),
				g.getDateApproved(), g.getLastModifierUserID(), g
						.getDateLastModified());
	}

	public int getHstScriptID() {
		return hstScriptID;
	}

	public void setHstScriptID(int hstScriptID) {
		this.hstScriptID = hstScriptID;
	}

	public String getScriptID() {
		return scriptID;
	}

	public void setScriptID(String scriptID) {
		this.scriptID = scriptID;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public int getUserFieldID() {
		return userFieldID;
	}

	public void setUserFieldID(int userFieldID) {
		this.userFieldID = userFieldID;
	}

	public int getPriorityID() {
		return priorityID;
	}

	public void setPriorityID(int priorityID) {
		this.priorityID = priorityID;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getMessageTypeID() {
		return messageTypeID;
	}

	public void setMessageTypeID(int messageTypeID) {
		this.messageTypeID = messageTypeID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCommentTypeID() {
		return commentTypeID;
	}

	public void setCommentTypeID(int commentTypeID) {
		this.commentTypeID = commentTypeID;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public UserField getUserField() {
		return userField;
	}

	public void setUserField(UserField userField) {
		this.userField = userField;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	@Override
	public String getId() {
		return scriptID;
	}

	@Override
	public void setId(String id) {
		scriptID = id;
	}
	@Override

	public String getEntityDesc() {
		return EntityType.SCRIPT.getDesc();
	}
}
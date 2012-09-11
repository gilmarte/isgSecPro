package com.isg.ifrend.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author kristine.furto
 * 
 */
public class HstMli extends Global implements Serializable {

	private static final long serialVersionUID = 301652661220101584L;

	private int hstMliID;

	private String mliID;
	// global configuration
	private String countryCode;

	private String functionCode;
	private int userFieldID;

	// TODO code type
	private int responseCodeID;

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

	private String statusDesc; // sherwin

	public HstMli() {
	}

	public HstMli(String mliID, String countryCode, String functionCode,
			int userFieldID, int responseCodeID, String desc,
			int messageTypeID, String message, int commentTypeID,
			String comment, int statusID, int actionID, String creatorUserID,
			Date dateCreated, String approverUserID, Date dateApproved,
			String lastModifierUserID, Date dateLastModified) {
		this.mliID = mliID;
		this.countryCode = countryCode;
		this.functionCode = functionCode;
		this.userFieldID = userFieldID;
		this.responseCodeID = responseCodeID;
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

	/**
	 * Copies TmpMli to HstMli
	 * 
	 * @param g
	 */
	public HstMli(TmpMli g) {
		this(g.getMliID(), g.getCountryCode(), g.getFunctionCode(), g
				.getUserFieldID(), g.getResponseCodeID(), g.getDesc(), g
				.getMessageTypeID(), g.getMessage(), g.getCommentTypeID(), g
				.getComment(), g.getStatusID(), g.getActionID(), g
				.getCreatorUserID(), g.getDateCreated(), g.getApproverUserID(),
				g.getDateApproved(), g.getLastModifierUserID(), g
						.getDateLastModified());
	}

	public int getHstMliID() {
		return hstMliID;
	}

	public void setHstMliID(int hstMliID) {
		this.hstMliID = hstMliID;
	}

	public String getMliID() {
		return mliID;
	}

	public void setMliID(String mliID) {
		this.mliID = mliID;
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

	public int getResponseCodeID() {
		return responseCodeID;
	}

	public void setResponseCodeID(int responseCodeID) {
		this.responseCodeID = responseCodeID;
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

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	@Override
	public String getId() {
		return mliID;
	}

	@Override
	public void setId(String id) {
		mliID = id;
	}

	@Override
	public String getEntityDesc() {
		return EntityType.MLI.getDesc();
	}
}
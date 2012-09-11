package com.isg.ifrend.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author kristine.furto
 * 
 */
public class Label extends Global implements Serializable {

	private static final long serialVersionUID = -1489300534973557903L;

	private String labelID;
	private String labelLanguageCode;
	private String labelName;
	private String labelNativeString;

	private int statusID;
	private int actionID;

	private String creatorUserID;
	private Date dateCreated;

	private String lastModifierUserID;
	private Date dateLastModified;

	private String approverUserID;
	private Date dateApproved;

	public Label() {
	}

	public Label(TmpLabel t) {
		this.setLabelID(t.getLabelID());

		this.setLabelLanguageCode(t.getLabelLanguageCode());
		this.setLabelName(t.getLabelName());
		this.setLabelNativeString(t.getLabelNativeString());

		this.setStatusID(t.getStatusID());
		this.setActionID(t.getActionID());

		this.setCreatorUserID(t.getCreatorUserID());
		this.setDateCreated(t.getDateCreated());

		this.setLastModifierUserID(t.getLastModifierUserID());
		this.setDateLastModified(t.getDateLastModified());

		this.setApproverUserID(t.getApproverUserID());
		this.setDateApproved(t.getDateApproved());
	}

	public String getLabelID() {
		return labelID;
	}

	public void setLabelID(String labelID) {
		this.labelID = labelID;
	}

	public String getLabelLanguageCode() {
		return labelLanguageCode;
	}

	public void setLabelLanguageCode(String labelLanguageCode) {
		this.labelLanguageCode = labelLanguageCode;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getLabelNativeString() {
		return labelNativeString;
	}

	public void setLabelNativeString(String labelNativeString) {
		this.labelNativeString = labelNativeString;
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

	@Override
	public String getId() {
		return labelID;
	}

	@Override
	public void setId(String id) {
		labelID = id;
	}

	@Override
	public String getDesc() {
		return labelName;
	}


	@Override
	public void setDesc(String desc) {
		labelName = desc;
	}

	@Override
	public String getEntityDesc() {
		return EntityType.LABEL.getDesc();
	}
}
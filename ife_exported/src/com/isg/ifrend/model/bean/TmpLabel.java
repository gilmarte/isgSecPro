package com.isg.ifrend.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author kristine.furto
 * 
 */
public class TmpLabel extends Global implements Serializable {

	private static final long serialVersionUID = -5997885220511559729L;

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

	public TmpLabel() {
	}

	public TmpLabel(Label g) {
		this.setLabelID(g.getLabelID());

		this.setLabelLanguageCode(g.getLabelLanguageCode());
		this.setLabelName(g.getLabelName());
		this.setLabelNativeString(g.getLabelNativeString());

		this.setStatusID(g.getStatusID());
		this.setActionID(g.getActionID());

		this.setCreatorUserID(g.getCreatorUserID());
		this.setDateCreated(g.getDateCreated());

		this.setLastModifierUserID(g.getLastModifierUserID());
		this.setDateLastModified(g.getDateLastModified());

		this.setApproverUserID(g.getApproverUserID());
		this.setDateApproved(g.getDateApproved());
	}

	public TmpLabel(HstLabel h) {
		this.setLabelID(h.getLabelID());

		this.setLabelLanguageCode(h.getLabelLanguageCode());
		this.setLabelName(h.getLabelName());
		this.setLabelNativeString(h.getLabelNativeString());

		this.setStatusID(h.getStatusID());
		this.setActionID(h.getActionID());

		this.setCreatorUserID(h.getCreatorUserID());
		this.setDateCreated(h.getDateCreated());

		this.setLastModifierUserID(h.getLastModifierUserID());
		this.setDateLastModified(h.getDateLastModified());

		this.setApproverUserID(h.getApproverUserID());
		this.setDateApproved(h.getDateApproved());
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
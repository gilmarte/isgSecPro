package com.isg.ifrend.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class HstCodeType extends Global implements Serializable {

	private static final long serialVersionUID = 5522944485122605206L;

	private int hstCodeTypeID;

	private String codeTypeID;
	private String desc;

	private int statusID;
	private int actionID;

	private String creatorUserID;
	private Date dateCreated;

	private String lastModifierUserID;
	private Date dateLastModified;

	private String approverUserID;
	private Date dateApproved;

	private Set<HstCode> hstCodeSet = new HashSet<HstCode>();

	public HstCodeType() {
	}

	public HstCodeType(TmpCodeType g) {
		this.setCodeTypeID(g.getCodeTypeID());

		this.setDesc(g.getDesc());

		for (TmpCode code : g.getTmpCodeSet()) {
			this.hstCodeSet.add(new HstCode(code, this));
		}

		this.setStatusID(g.getStatusID());
		this.setActionID(g.getActionID());

		this.setCreatorUserID(g.getCreatorUserID());
		this.setDateCreated(g.getDateCreated());

		this.setLastModifierUserID(g.getLastModifierUserID());
		this.setDateLastModified(g.getDateLastModified());

		this.setApproverUserID(g.getApproverUserID());
		this.setDateApproved(g.getDateApproved());
	}

	public void addToHstCodeSet(HstCode hstCode) {
		hstCodeSet.add(hstCode);
		hstCode.setHstCodeType(this);
	}

	public int getHstCodeTypeID() {
		return hstCodeTypeID;
	}

	public void setHstCodeTypeID(int hstCodeTypeID) {
		this.hstCodeTypeID = hstCodeTypeID;
	}

	public String getCodeTypeID() {
		return codeTypeID;
	}

	public void setCodeTypeID(String codeTypeID) {
		this.codeTypeID = codeTypeID;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public Set<HstCode> getHstCodeSet() {
		return hstCodeSet;
	}

	public void setHstCodeSet(Set<HstCode> hstCodeSet) {
		this.hstCodeSet = hstCodeSet;
	}

	@Override
	public String getId() {
		return codeTypeID;
	}

	@Override
	public void setId(String id) {
		codeTypeID = id;
	}

	@Override
	public String getEntityDesc() {
		return EntityType.CODETYPE.getDesc();
	}
}
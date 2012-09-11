package com.isg.ifrend.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author kristine.furto
 * 
 */
public class CodeType extends Global implements Serializable {

	private static final long serialVersionUID = -407826445050244477L;

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

	private Set<Code> codeSet = new HashSet<Code>();

	public CodeType() {
	}

	public CodeType(TmpCodeType t) {
		this.setCodeTypeID(t.getCodeTypeID());

		this.setDesc(t.getDesc());

		for (TmpCode tmpCode : t.getTmpCodeSet()) {
			this.codeSet.add(new Code(tmpCode, this));
		}

		this.setStatusID(t.getStatusID());
		this.setActionID(t.getActionID());

		this.setCreatorUserID(t.getCreatorUserID());
		this.setDateCreated(t.getDateCreated());

		this.setLastModifierUserID(t.getLastModifierUserID());
		this.setDateLastModified(t.getDateLastModified());

		this.setApproverUserID(t.getApproverUserID());
		this.setDateApproved(t.getDateApproved());
	}

	public void addToCodeSet(Code code) {
		codeSet.add(code);
		code.setCodeType(this);
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

	public void setDesc(String codeDesc) {
		this.desc = codeDesc;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int status) {
		statusID = status;
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

	public Set<Code> getCodeSet() {
		return codeSet;
	}

	public void setCodeSet(Set<Code> codeSet) {
		this.codeSet = codeSet;
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
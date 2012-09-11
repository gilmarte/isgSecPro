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
public class TmpCodeType extends Global implements Serializable {

	private static final long serialVersionUID = -3242719438386720925L;

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

	private Set<TmpCode> tmpCodeSet = new HashSet<TmpCode>();

	public TmpCodeType() {
	}

	public TmpCodeType(CodeType g) {
		this.setCodeTypeID(g.getCodeTypeID());

		this.setDesc(g.getDesc());

		for (Code code : g.getCodeSet()) {
			this.tmpCodeSet.add(new TmpCode(code, this));
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

	public TmpCodeType(HstCodeType h) {
		this.setCodeTypeID(h.getCodeTypeID());

		this.setDesc(h.getDesc());

		for (HstCode hstCode : h.getHstCodeSet()) {
			this.tmpCodeSet.add(new TmpCode(hstCode, this));
		}

		this.setStatusID(h.getStatusID());
		this.setActionID(h.getActionID());

		this.setCreatorUserID(h.getCreatorUserID());
		this.setDateCreated(h.getDateCreated());

		this.setLastModifierUserID(h.getLastModifierUserID());
		this.setDateLastModified(h.getDateLastModified());

		this.setApproverUserID(h.getApproverUserID());
		this.setDateApproved(h.getDateApproved());
	}

	public void addToTmpCodeSet(TmpCode tmpCode) {
		tmpCode.setTmpCodeType(this);
		tmpCodeSet.add(tmpCode);
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

	public Set<TmpCode> getTmpCodeSet() {
		return tmpCodeSet;
	}

	public void setTmpCodeSet(Set<TmpCode> tmpCodeSet) {
		this.tmpCodeSet = tmpCodeSet;
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
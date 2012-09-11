package com.isg.ifrend.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author kristine.furto
 *
 */
public class TmpElement extends Global implements Serializable {

	private static final long serialVersionUID = 1224944014962950451L;
	
	private boolean isNew;
	private String entityType;
	private String tmp_elem_id;
	private Integer tmp_elemtype_id;
	private Integer tmp_elem_format_id;
	private Integer tmp_elem_length;
	private Integer tmp_elem_decimal;
	private Integer tmp_elem_dateformat_id;
	private String tmp_elem_desc;
	private String tmp_elem_operator_eq;
	private String tmp_elem_operator_ne;
	private String tmp_elem_operator_gt;
	private String tmp_elem_operator_ge;
	private String tmp_elem_operator_lt;
	private String tmp_elem_operator_le;
	
	private String status_desc;
	
	private int statusID;
	private int actionID;
	private String creatorUserID;
	private Date dateCreated;
	private String approverUserID;
	private Date dateApproved;
	private String lastModifierUserID;
	private Date dateLastModified;
	

	private ElementTypes tmp_elemTypes;
	private ElementFormats tmp_elemFormats;
	private ElementFormats tmp_dateFormats;
	
	public TmpElement() {}
	
	/**
	 * Copies Element to TmpElement
	 * @param g
	 */
	public TmpElement(Element g) {
		this.tmp_elem_id = g.getElement_id();
		this.tmp_elemtype_id = g.getElementtype_id();
		this.tmp_elem_format_id = g.getElement_format_id();
		this.tmp_elem_length = g.getElement_length();
		this.tmp_elem_decimal = g.getElement_decimal();
		this.tmp_elem_dateformat_id = g.getElement_dateformat_id();
		this.tmp_elem_desc = g.getElement_desc();
		this.tmp_elem_operator_eq = g.getElement_operator_eq();
		this.tmp_elem_operator_ne = g.getElement_operator_ne();
		this.tmp_elem_operator_gt = g.getElement_operator_gt();
		this.tmp_elem_operator_ge = g.getElement_operator_ge();
		this.tmp_elem_operator_lt = g.getElement_operator_lt();
		this.tmp_elem_operator_le = g.getElement_operator_le();
		this.statusID = g.getStatusID();
		this.actionID = g.getActionID();
		this.creatorUserID = g.getCreatorUserID();
		this.dateCreated = g.getDateCreated();
		this.approverUserID = g.getApproverUserID();
		this.dateApproved = g.getDateApproved();
		this.lastModifierUserID = g.getLastModifierUserID();
		this.dateLastModified = g.getDateLastModified();		
	}
	
	public TmpElement(HstElement h) {
		this.tmp_elem_id = h.getHst_elem_id();
		this.tmp_elemtype_id = h.getHst_elemtype_id();
		this.tmp_elem_format_id = h.getHst_elem_format_id();
		this.tmp_elem_length = h.getHst_elem_length();
		this.tmp_elem_decimal = h.getHst_elem_decimal();
		this.tmp_elem_dateformat_id = h.getHst_elem_dateformat_id();
		this.tmp_elem_desc = h.getHst_elem_desc();
		this.tmp_elem_operator_eq = h.getHst_elem_operator_eq();
		this.tmp_elem_operator_ne = h.getHst_elem_operator_ne();
		this.tmp_elem_operator_gt = h.getHst_elem_operator_gt();
		this.tmp_elem_operator_ge = h.getHst_elem_operator_ge();
		this.tmp_elem_operator_lt = h.getHst_elem_operator_lt();
		this.tmp_elem_operator_le = h.getHst_elem_operator_le();
		this.statusID = h.getStatusID();
		this.actionID = h.getActionID();
		this.creatorUserID = h.getCreatorUserID();
		this.dateCreated = h.getDateCreated();
		this.approverUserID = h.getApproverUserID();
		this.dateApproved = h.getDateApproved();
		this.lastModifierUserID = h.getLastModifierUserID();
		this.dateLastModified = h.getDateLastModified();		
	}

	public String getTmp_elem_id() {
		return tmp_elem_id;
	}

	public void setTmp_elem_id(String tmp_elem_id) {
		this.tmp_elem_id = tmp_elem_id;
	}

	public Integer getTmp_elemtype_id() {
		return tmp_elemtype_id;
	}

	public void setTmp_elemtype_id(Integer tmp_elemtype_id) {
		this.tmp_elemtype_id = tmp_elemtype_id;
	}

	public Integer getTmp_elem_format_id() {
		return tmp_elem_format_id;
	}

	public void setTmp_elem_format_id(Integer tmp_elem_format_id) {
		this.tmp_elem_format_id = tmp_elem_format_id;
	}

	public Integer getTmp_elem_length() {
		return tmp_elem_length;
	}

	public void setTmp_elem_length(Integer tmp_elem_length) {
		this.tmp_elem_length = tmp_elem_length;
	}

	public Integer getTmp_elem_decimal() {
		return tmp_elem_decimal;
	}

	public void setTmp_elem_decimal(Integer tmp_elem_decimal) {
		this.tmp_elem_decimal = tmp_elem_decimal;
	}

	public Integer getTmp_elem_dateformat_id() {
		return tmp_elem_dateformat_id;
	}

	public void setTmp_elem_dateformat_id(Integer tmp_elem_dateformat_id) {
		this.tmp_elem_dateformat_id = tmp_elem_dateformat_id;
	}

	public String getTmp_elem_desc() {
		return tmp_elem_desc;
	}

	public void setTmp_elem_desc(String tmp_elem_desc) {
		this.tmp_elem_desc = tmp_elem_desc;
	}

	public String getTmp_elem_operator_eq() {
		return tmp_elem_operator_eq;
	}

	public void setTmp_elem_operator_eq(String tmp_elem_operator_eq) {
		this.tmp_elem_operator_eq = tmp_elem_operator_eq;
	}

	public String getTmp_elem_operator_ne() {
		return tmp_elem_operator_ne;
	}

	public void setTmp_elem_operator_ne(String tmp_elem_operator_ne) {
		this.tmp_elem_operator_ne = tmp_elem_operator_ne;
	}

	public String getTmp_elem_operator_gt() {
		return tmp_elem_operator_gt;
	}

	public void setTmp_elem_operator_gt(String tmp_elem_operator_gt) {
		this.tmp_elem_operator_gt = tmp_elem_operator_gt;
	}

	public String getTmp_elem_operator_ge() {
		return tmp_elem_operator_ge;
	}

	public void setTmp_elem_operator_ge(String tmp_elem_operator_ge) {
		this.tmp_elem_operator_ge = tmp_elem_operator_ge;
	}

	public String getTmp_elem_operator_lt() {
		return tmp_elem_operator_lt;
	}

	public void setTmp_elem_operator_lt(String tmp_elem_operator_lt) {
		this.tmp_elem_operator_lt = tmp_elem_operator_lt;
	}

	public String getTmp_elem_operator_le() {
		return tmp_elem_operator_le;
	}

	public void setTmp_elem_operator_le(String tmp_elem_operator_le) {
		this.tmp_elem_operator_le = tmp_elem_operator_le;
	}

	public ElementTypes getTmp_elemTypes() {
		return tmp_elemTypes;
	}

	public void setTmp_elemTypes(ElementTypes tmp_elemTypes) {
		this.tmp_elemTypes = tmp_elemTypes;
	}

	public ElementFormats getTmp_elemFormats() {
		return tmp_elemFormats;
	}

	public void setTmp_elemFormats(ElementFormats tmp_elemFormats) {
		this.tmp_elemFormats = tmp_elemFormats;
	}

	public ElementFormats getTmp_dateFormats() {
		return tmp_dateFormats;
	}

	public void setTmp_dateFormats(ElementFormats tmp_dateFormats) {
		this.tmp_dateFormats = tmp_dateFormats;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getStatus_desc() {
		return status_desc;
	}

	public void setStatus_desc(String status_desc) {
		this.status_desc = status_desc;
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

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	@Override
	public String getId() {
		return tmp_elem_id;
	}

	@Override
	public void setId(String id) {
		tmp_elem_id = id;
	}

	@Override
	public String getDesc() {
		return tmp_elem_desc;
	}

	@Override
	public void setDesc(String desc) {
		tmp_elem_desc = desc;
	}

	@Override
	public String getEntityDesc() {
		return EntityType.ELEMENT.getDesc();
	}
}

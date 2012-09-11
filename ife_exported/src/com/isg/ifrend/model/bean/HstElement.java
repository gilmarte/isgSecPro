package com.isg.ifrend.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author kristine.furto
 *
 */
public class HstElement extends Global implements Serializable {

	private static final long serialVersionUID = -5862265701088426523L;

	private Integer his_elem_trans_id;
	private String hst_elem_id;
	private Integer hst_elemtype_id;
	private Integer hst_elem_format_id;
	private Integer hst_elem_length;
	private Integer hst_elem_decimal;
	private Integer hst_elem_dateformat_id;
	private String hst_elem_desc;
	private String hst_elem_operator_eq;
	private String hst_elem_operator_ne;
	private String hst_elem_operator_gt;
	private String hst_elem_operator_ge;
	private String hst_elem_operator_lt;
	private String hst_elem_operator_le;

	private int statusID;
	private int actionID;
	private String creatorUserID;
	private Date dateCreated;
	private String approverUserID;
	private Date dateApproved;
	private String lastModifierUserID;
	private Date dateLastModified;

	private String hst_elem_record_type;

	private ElementTypes hst_elemTypes;
	private ElementFormats hst_elemFormats;
	private ElementFormats hst_dateFormats;

	public HstElement() {
	}

	/**
	 * Copies TmpElement to HstElement
	 * 
	 * @param g
	 */
	public HstElement(TmpElement g) {
		this.hst_elem_id = g.getTmp_elem_id();
		this.hst_elemtype_id = g.getTmp_elemtype_id();
		this.hst_elem_format_id = g.getTmp_elem_format_id();
		this.hst_elem_length = g.getTmp_elem_length();
		this.hst_elem_decimal = g.getTmp_elem_decimal();
		this.hst_elem_dateformat_id = g.getTmp_elem_dateformat_id();
		this.hst_elem_desc = g.getTmp_elem_desc();
		this.hst_elem_operator_eq = g.getTmp_elem_operator_eq();
		this.hst_elem_operator_ne = g.getTmp_elem_operator_ne();
		this.hst_elem_operator_gt = g.getTmp_elem_operator_gt();
		this.hst_elem_operator_ge = g.getTmp_elem_operator_ge();
		this.hst_elem_operator_lt = g.getTmp_elem_operator_lt();
		this.hst_elem_operator_le = g.getTmp_elem_operator_le();
		this.statusID = g.getStatusID();
		this.actionID = g.getActionID();
		this.creatorUserID = g.getCreatorUserID();
		this.dateCreated = g.getDateCreated();
		this.approverUserID = g.getApproverUserID();
		this.dateApproved = g.getDateApproved();
		this.lastModifierUserID = g.getLastModifierUserID();
		this.dateLastModified = g.getDateLastModified();
	}

	public String getHst_elem_id() {
		return hst_elem_id;
	}

	public void setHst_elem_id(String hst_elem_id) {
		this.hst_elem_id = hst_elem_id;
	}

	public Integer getHst_elemtype_id() {
		return hst_elemtype_id;
	}

	public void setHst_elemtype_id(Integer hst_elemtype_id) {
		this.hst_elemtype_id = hst_elemtype_id;
	}

	public Integer getHst_elem_format_id() {
		return hst_elem_format_id;
	}

	public void setHst_elem_format_id(Integer hst_elem_format_id) {
		this.hst_elem_format_id = hst_elem_format_id;
	}

	public Integer getHst_elem_length() {
		return hst_elem_length;
	}

	public void setHst_elem_length(Integer hst_elem_length) {
		this.hst_elem_length = hst_elem_length;
	}

	public Integer getHst_elem_decimal() {
		return hst_elem_decimal;
	}

	public void setHst_elem_decimal(Integer hst_elem_decimal) {
		this.hst_elem_decimal = hst_elem_decimal;
	}

	public Integer getHst_elem_dateformat_id() {
		return hst_elem_dateformat_id;
	}

	public void setHst_elem_dateformat_id(Integer hst_elem_dateformat_id) {
		this.hst_elem_dateformat_id = hst_elem_dateformat_id;
	}

	public String getHst_elem_desc() {
		return hst_elem_desc;
	}

	public void setHst_elem_desc(String hst_elem_desc) {
		this.hst_elem_desc = hst_elem_desc;
	}

	public String getHst_elem_operator_eq() {
		return hst_elem_operator_eq;
	}

	public void setHst_elem_operator_eq(String hst_elem_operator_eq) {
		this.hst_elem_operator_eq = hst_elem_operator_eq;
	}

	public String getHst_elem_operator_ne() {
		return hst_elem_operator_ne;
	}

	public void setHst_elem_operator_ne(String hst_elem_operator_ne) {
		this.hst_elem_operator_ne = hst_elem_operator_ne;
	}

	public String getHst_elem_operator_gt() {
		return hst_elem_operator_gt;
	}

	public void setHst_elem_operator_gt(String hst_elem_operator_gt) {
		this.hst_elem_operator_gt = hst_elem_operator_gt;
	}

	public String getHst_elem_operator_ge() {
		return hst_elem_operator_ge;
	}

	public void setHst_elem_operator_ge(String hst_elem_operator_ge) {
		this.hst_elem_operator_ge = hst_elem_operator_ge;
	}

	public String getHst_elem_operator_lt() {
		return hst_elem_operator_lt;
	}

	public void setHst_elem_operator_lt(String hst_elem_operator_lt) {
		this.hst_elem_operator_lt = hst_elem_operator_lt;
	}

	public String getHst_elem_operator_le() {
		return hst_elem_operator_le;
	}

	public void setHst_elem_operator_le(String hst_elem_operator_le) {
		this.hst_elem_operator_le = hst_elem_operator_le;
	}

	public String getHst_elem_record_type() {
		return hst_elem_record_type;
	}

	public void setHst_elem_record_type(String hst_elem_record_type) {
		this.hst_elem_record_type = hst_elem_record_type;
	}

	public ElementTypes getHst_elemTypes() {
		return hst_elemTypes;
	}

	public void setHst_elemTypes(ElementTypes hst_elemTypes) {
		this.hst_elemTypes = hst_elemTypes;
	}

	public ElementFormats getHst_elemFormats() {
		return hst_elemFormats;
	}

	public void setHst_elemFormats(ElementFormats hst_elemFormats) {
		this.hst_elemFormats = hst_elemFormats;
	}

	public ElementFormats getHst_dateFormats() {
		return hst_dateFormats;
	}

	public void setHst_dateFormats(ElementFormats hst_dateFormats) {
		this.hst_dateFormats = hst_dateFormats;
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

	public Integer getHis_elem_trans_id() {
		return his_elem_trans_id;
	}

	public void setHis_elem_trans_id(Integer his_elem_trans_id) {
		this.his_elem_trans_id = his_elem_trans_id;
	}
	
	@Override
	public String getId() {
		return hst_elem_id;
	}

	@Override
	public void setId(String id) {
		hst_elem_id = id;
	}

	@Override
	public String getDesc() {
		return hst_elem_desc;
	}

	@Override
	public void setDesc(String desc) {
		hst_elem_desc = desc;
	}

	@Override
	public String getEntityDesc() {
		return EntityType.ELEMENT.getDesc();
	}
}

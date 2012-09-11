package com.isg.ifrend.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author kristine.furto
 *
 */
public class Element extends Global implements Serializable {

	private static final long serialVersionUID = -7595672853753604224L;

	private boolean isNew;
	private String element_id;

	private Integer elementtype_id;
	private Integer element_format_id;
	private Integer element_length;
	private Integer element_decimal;
	private Integer element_dateformat_id;
	private String element_desc;
	private String element_operator_eq;
	private String element_operator_ne;
	private String element_operator_gt;
	private String element_operator_ge;
	private String element_operator_lt;
	private String element_operator_le;
	private String element_status_desc;

	private int statusID;
	private int actionID;
	private String creatorUserID;
	private Date dateCreated;
	private String approverUserID;
	private Date dateApproved;
	private String lastModifierUserID;
	private Date dateLastModified;

	private ElementTypes elementTypes;
	private ElementFormats elementFormats;
	private ElementFormats dateFormats;

	public Element() {
	}

	public Element(Element elem) {
		this.element_id = elem.getElement_id();
		this.elementtype_id = elem.getElementtype_id();
		this.element_format_id = elem.getElement_format_id();
		this.element_length = elem.getElement_length();
		this.element_decimal = elem.getElement_decimal();
		this.element_dateformat_id = elem.getElement_dateformat_id();
		this.element_desc = elem.getElement_desc();
		this.element_operator_eq = elem.getElement_operator_eq();
		this.element_operator_ne = elem.getElement_operator_ne();
		this.element_operator_gt = elem.getElement_operator_gt();
		this.element_operator_ge = elem.getElement_operator_ge();
		this.element_operator_lt = elem.getElement_operator_lt();
		this.element_operator_le = elem.getElement_operator_le();
		this.statusID = elem.getStatusID();
		this.creatorUserID = elem.getCreatorUserID();
		this.dateCreated = elem.getDateCreated();
		this.approverUserID = elem.getApproverUserID();
		this.dateApproved = elem.getDateApproved();
		this.lastModifierUserID = elem.getLastModifierUserID();
		this.dateLastModified = elem.getDateLastModified();
	}

	/**
	 * Copies TmpElement to Element
	 * 
	 * @param t
	 */
	public Element(TmpElement t) {
		this.element_id = t.getTmp_elem_id();
		this.elementtype_id = t.getTmp_elemtype_id();
		this.element_format_id = t.getTmp_elem_format_id();
		this.element_length = t.getTmp_elem_length();
		this.element_decimal = t.getTmp_elem_decimal();
		this.element_dateformat_id = t.getTmp_elem_dateformat_id();
		this.element_desc = t.getTmp_elem_desc();
		this.element_operator_eq = t.getTmp_elem_operator_eq();
		this.element_operator_ne = t.getTmp_elem_operator_ne();
		this.element_operator_gt = t.getTmp_elem_operator_gt();
		this.element_operator_ge = t.getTmp_elem_operator_ge();
		this.element_operator_lt = t.getTmp_elem_operator_lt();
		this.element_operator_le = t.getTmp_elem_operator_le();

		this.statusID = t.getStatusID();
		this.creatorUserID = t.getCreatorUserID();
		this.dateCreated = t.getDateCreated();
		this.approverUserID = t.getApproverUserID();
		this.dateApproved = t.getDateApproved();
		this.lastModifierUserID = t.getLastModifierUserID();
		this.dateLastModified = t.getDateLastModified();
	}

	public String getElement_id() {
		return element_id;
	}

	public void setElement_id(String element_id) {
		this.element_id = element_id;
	}

	public Integer getElementtype_id() {
		return elementtype_id;
	}

	public void setElementtype_id(Integer elementtype_id) {
		this.elementtype_id = elementtype_id;
	}

	public Integer getElement_format_id() {
		return element_format_id;
	}

	public void setElement_format_id(Integer element_format_id) {
		this.element_format_id = element_format_id;
	}

	public Integer getElement_length() {
		return element_length;
	}

	public void setElement_length(Integer element_length) {
		this.element_length = element_length;
	}

	public Integer getElement_decimal() {
		return element_decimal;
	}

	public void setElement_decimal(Integer element_decimal) {
		this.element_decimal = element_decimal;
	}

	public Integer getElement_dateformat_id() {
		return element_dateformat_id;
	}

	public void setElement_dateformat_id(Integer element_dateformat_id) {
		this.element_dateformat_id = element_dateformat_id;
	}

	public String getElement_desc() {
		return element_desc;
	}

	public void setElement_desc(String element_desc) {
		this.element_desc = element_desc;
	}

	public String getElement_operator_eq() {
		return element_operator_eq;
	}

	public void setElement_operator_eq(String element_operator_eq) {
		this.element_operator_eq = element_operator_eq;
	}

	public String getElement_operator_ne() {
		return element_operator_ne;
	}

	public void setElement_operator_ne(String element_operator_ne) {
		this.element_operator_ne = element_operator_ne;
	}

	public String getElement_operator_gt() {
		return element_operator_gt;
	}

	public void setElement_operator_gt(String element_operator_gt) {
		this.element_operator_gt = element_operator_gt;
	}

	public String getElement_operator_ge() {
		return element_operator_ge;
	}

	public void setElement_operator_ge(String element_operator_ge) {
		this.element_operator_ge = element_operator_ge;
	}

	public String getElement_operator_lt() {
		return element_operator_lt;
	}

	public void setElement_operator_lt(String element_operator_lt) {
		this.element_operator_lt = element_operator_lt;
	}

	public String getElement_operator_le() {
		return element_operator_le;
	}

	public void setElement_operator_le(String element_operator_le) {
		this.element_operator_le = element_operator_le;
	}

	public ElementTypes getElementTypes() {
		return elementTypes;
	}

	public void setElementTypes(ElementTypes elementTypes) {
		this.elementTypes = elementTypes;
	}

	public ElementFormats getElementFormats() {
		return elementFormats;
	}

	public void setElementFormats(ElementFormats elementFormats) {
		this.elementFormats = elementFormats;
	}

	public ElementFormats getDateFormats() {
		return dateFormats;
	}

	public void setDateFormats(ElementFormats dateFormats) {
		this.dateFormats = dateFormats;
	}

	public String getElement_status_desc() {
		return element_status_desc;
	}

	public void setElement_status_desc(String element_status_desc) {
		this.element_status_desc = element_status_desc;
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
	public String getId() {
		return element_id;
	}

	@Override
	public void setId(String id) {
		element_id = id;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	@Override
	public String getDesc() {
		return element_desc;
	}
	
	@Override
	public void setDesc(String desc) {
		element_desc = desc;
	}

	@Override
	public String getEntityDesc() {
		return EntityType.ELEMENT.getDesc();
	}
}

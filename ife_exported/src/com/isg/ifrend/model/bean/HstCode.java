package com.isg.ifrend.model.bean;

import java.io.Serializable;

/**
 * 
 * @author kristine.furto
 * 
 */
public class HstCode implements Serializable {

	private static final long serialVersionUID = 1L;

	private int hstCodeID;

	private int codeID;
	private String codeTypeID;

	private String codeValue;
	private String codeDesc;

	private HstCodeType hstCodeType;

	public HstCode() {
	}

	public HstCode(TmpCode code, HstCodeType hstCodeType) {
		this.setCodeID(code.getCodeID());
		this.setCodeTypeID(hstCodeType.getCodeTypeID());
		this.setCodeValue(code.getCodeValue());
		this.setCodeDesc(code.getCodeDesc());
		this.setHstCodeType(hstCodeType);
	}

	public HstCode(TmpCode code) {
		this.setCodeID(code.getCodeID());
		this.setCodeTypeID(code.getCodeTypeID());
		this.setCodeValue(code.getCodeValue());
		this.setCodeDesc(code.getCodeDesc());
	}

	public int getHstCodeID() {
		return hstCodeID;
	}

	public void setHstCodeID(int hstCodeID) {
		this.hstCodeID = hstCodeID;
	}

	public int getCodeID() {
		return codeID;
	}

	public void setCodeID(int codeID) {
		this.codeID = codeID;
	}

	public String getCodeTypeID() {
		return codeTypeID;
	}

	public void setCodeTypeID(String codeTypeID) {
		this.codeTypeID = codeTypeID;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getCodeDesc() {
		return codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public HstCodeType getHstCodeType() {
		return hstCodeType;
	}

	public void setHstCodeType(HstCodeType hstCodeType) {
		this.hstCodeType = hstCodeType;
	}
}

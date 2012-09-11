package com.isg.ifrend.model.bean;

import java.io.Serializable;

/**
 * 
 * @author kristine.furto
 * 
 */
public class Code implements Serializable {

	private static final long serialVersionUID = 5339123956806763655L;

	private int codeID;
	private String codeTypeID;
	private String codeValue;
	private String codeDesc;

	private CodeType codeType;

	private String codeForDisplay;

	public Code() {
	}

	public Code(TmpCode tmpCode, CodeType codeType) {
		this.setCodeID(tmpCode.getCodeID());
		this.setCodeTypeID(tmpCode.getCodeTypeID());
		this.setCodeValue(tmpCode.getCodeValue());
		this.setCodeDesc(tmpCode.getCodeDesc());
		this.setCodeType(codeType);
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

	public CodeType getCodeType() {
		return codeType;
	}

	public void setCodeType(CodeType codeType) {
		this.codeType = codeType;
	}

	public int getCodeID() {
		return codeID;
	}

	public void setCodeID(int codeID) {
		this.codeID = codeID;
	}

	public String getCodeForDisplay() {
		codeForDisplay = codeValue + " - " + codeDesc;
		return codeForDisplay;
	}

	public void setCodeForDisplay(String codeForDisplay) {
		this.codeForDisplay = codeForDisplay;
	}
}

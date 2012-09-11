package com.isg.ifrend.model.bean;

import java.io.Serializable;

/**
 * 
 * @author kristine.furto
 * 
 */
public class TmpCode implements Serializable {

	private static final long serialVersionUID = -4371907975428892825L;

	private int codeID;
	private String codeTypeID;

	private String codeValue;
	private String codeDesc;

	private TmpCodeType tmpCodeType;

	public TmpCode() {
	}

	public TmpCode(Code code, TmpCodeType tmpCodeType) {
		this.setCodeID(code.getCodeID());
		this.setCodeTypeID(code.getCodeTypeID());
		this.setCodeValue(code.getCodeValue());
		this.setCodeDesc(code.getCodeDesc());
		this.setTmpCodeType(tmpCodeType);
	}

	public TmpCode(HstCode hstCode, TmpCodeType tmpCodeType) {
		this.setCodeID(hstCode.getCodeID());
		this.setCodeTypeID(hstCode.getCodeTypeID());
		this.setCodeValue(hstCode.getCodeValue());
		this.setCodeDesc(hstCode.getCodeDesc());
		this.setTmpCodeType(tmpCodeType);
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

	public TmpCodeType getTmpCodeType() {
		return tmpCodeType;
	}

	public void setTmpCodeType(TmpCodeType tmpCodeType) {
		this.tmpCodeType = tmpCodeType;
	}
}

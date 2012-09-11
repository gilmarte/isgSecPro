package com.isg.ifrend.model.bean;

import java.io.Serializable;

/**
 * 
 * @author kristine.furto
 * 
 */
public class LanguageCode implements Serializable {

	private static final long serialVersionUID = 1L;
	private String lcLanguageCodeID;
	private String lcLanguageCodeDesc;

	// TODO Create formatter
	private String languageCodeForDisplay;

	public LanguageCode() {

	}

	public LanguageCode(String lcLanguageCodeID, String lcLanguageCodeDesc) {
		this.lcLanguageCodeID = lcLanguageCodeID;
		this.lcLanguageCodeDesc = lcLanguageCodeDesc;
	}

	public String getLcLanguageCodeID() {
		return lcLanguageCodeID;
	}

	public void setLcLanguageCodeID(String lcLanguageCodeID) {
		this.lcLanguageCodeID = lcLanguageCodeID;
	}

	public String getLcLanguageCodeDesc() {
		return lcLanguageCodeDesc;
	}

	public void setLcLanguageCodeDesc(String lcLanguageCodeDesc) {
		this.lcLanguageCodeDesc = lcLanguageCodeDesc;
	}

	public String getLanguageCodeForDisplay() {
		languageCodeForDisplay = lcLanguageCodeID + " - " + lcLanguageCodeDesc;
		return languageCodeForDisplay;
	}

	public void setLanguageCodeForDisplay(String languageCodeForDisplay) {
		this.languageCodeForDisplay = languageCodeForDisplay;
	}

}

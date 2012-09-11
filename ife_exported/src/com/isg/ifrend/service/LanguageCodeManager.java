package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.LanguageCode;

/**
 * 
 * @author kristine.furto
 *
 */
public interface LanguageCodeManager {

	public boolean save(LanguageCode languageCode);
	public boolean delete(LanguageCode languageCode);
	public LanguageCode findById(String languagecode_id);
	public List<LanguageCode> getLanguageCodeList();
}

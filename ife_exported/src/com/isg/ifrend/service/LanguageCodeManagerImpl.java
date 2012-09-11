package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.LanguageCode;
import com.isg.ifrend.model.dao.LanguageCodeDAO;
import com.isg.ifrend.utils.GlobalUtils;

/**
 * 
 * @author kristine.furto
 *
 */
public class LanguageCodeManagerImpl implements LanguageCodeManager {

	private LanguageCodeDAO languageCodeDAO;

	public LanguageCodeDAO getLanguageCodeDAO() {
		return languageCodeDAO;
	}

	public void setLanguageCodeDAO(LanguageCodeDAO languageCodeDAO) {
		this.languageCodeDAO = languageCodeDAO;
	}

	@Transactional
	public boolean save(LanguageCode languageCode) {
		try {
			languageCodeDAO.saveOrUpdate(languageCode);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(LanguageCode languageCode) {
		try {
			languageCodeDAO.delete(languageCode);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public LanguageCode findById(String languagecode_id) {
		try {
			return languageCodeDAO.find(LanguageCode.class, languagecode_id);
		}
		catch (IllegalArgumentException e) {
			Exception exception = (Exception)GlobalUtils.getRootCauseException(e);
			if(exception.getMessage().contains("id to load")) {
				LanguageCode langCode = new LanguageCode();
				langCode.setLcLanguageCodeID("");
				langCode.setLcLanguageCodeDesc("");
				return langCode;
			}
			e.printStackTrace();
			return null;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<LanguageCode> getLanguageCodeList() {
		try {
			return languageCodeDAO.findAll(LanguageCode.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

}

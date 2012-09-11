package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.DateFormats;
import com.isg.ifrend.model.dao.DateFormatsDAO;

public class DateFormatsImpl implements DateFormatsManager {

	private DateFormatsDAO dateFormatsDAO;

	public DateFormatsDAO getDateFormatsDAO() {
		return dateFormatsDAO;
	}

	public void setDateFormatsDAO(DateFormatsDAO dateFormatsDAO) {
		this.dateFormatsDAO = dateFormatsDAO;
	}
	
	@Transactional
	public boolean save(DateFormats dateFormats) {
		try {
			dateFormatsDAO.saveOrUpdate(dateFormats);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(DateFormats dateFormats) {
		try {
			dateFormatsDAO.delete(dateFormats);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public DateFormats findById(Integer dateFormat_id) {
		try {
			return dateFormatsDAO.find(DateFormats.class, dateFormat_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<DateFormats> getDateFormatsList() {
		try {
			return dateFormatsDAO.findAll(DateFormats.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}

package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.isg.ifrend.model.bean.ElementFormats;
import com.isg.ifrend.model.dao.ElementFormatsDAO;

public class ElementFormatsImpl implements ElementFormatsManager {

	private ElementFormatsDAO elementFormatsDAO;
	
	public ElementFormatsDAO getElementFormatsDAO() {
		return elementFormatsDAO;
	}

	public void setElementFormatsDAO(ElementFormatsDAO elementFormatsDAO) {
		this.elementFormatsDAO = elementFormatsDAO;
	}

	@Override
	public boolean save(ElementFormats elementFormats) {
		try {
			elementFormatsDAO.saveOrUpdate(elementFormats);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(ElementFormats elementFormats) {
		try {
			elementFormatsDAO.delete(elementFormats);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ElementFormats findById(Integer elementFormat_id) {
		try {
			return elementFormatsDAO.find(ElementFormats.class, elementFormat_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ElementFormats> getElementFormatsList() {
		try {
			return elementFormatsDAO.findAll(ElementFormats.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

}

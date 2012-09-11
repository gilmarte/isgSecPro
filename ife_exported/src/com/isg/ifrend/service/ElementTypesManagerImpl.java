package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.isg.ifrend.model.bean.ElementTypes;
import com.isg.ifrend.model.dao.ElementTypesDAO;

public class ElementTypesManagerImpl implements ElementTypesManager {

	private ElementTypesDAO elementTypesDAO;

	public ElementTypesDAO getElementTypesDAO() {
		return elementTypesDAO;
	}

	public void setElementTypesDAO(ElementTypesDAO elementTypesDAO) {
		this.elementTypesDAO = elementTypesDAO;
	}

	@Override
	public boolean save(ElementTypes elementType) {
		try {
			elementTypesDAO.saveOrUpdate(elementType);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(ElementTypes elementType) {
		try {
			elementTypesDAO.delete(elementType);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ElementTypes findById(Integer elementType_id) {
		try {
			return elementTypesDAO.find(ElementTypes.class, elementType_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ElementTypes> getElementTypesList() {
		try {
			return elementTypesDAO.findAll(ElementTypes.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

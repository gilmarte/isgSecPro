package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.SelectedFunctions;
import com.isg.ifrend.model.dao.SelectedFunctionsDAO;

public class SelectedFunctionsManagerImpl implements SelectedFunctionsManager {

	
	SelectedFunctionsDAO selectedfunctionsDAO;
	
	
	public SelectedFunctionsDAO getSelectedfunctionsDAO() {
		return selectedfunctionsDAO;
	}

	public void setSelectedfunctionsDAO(SelectedFunctionsDAO selectedfunctionsDAO) {
		this.selectedfunctionsDAO = selectedfunctionsDAO;
	}

	@Transactional
	public boolean save(SelectedFunctions selectedfunctions) {
		// TODO Auto-generated method stub
		try {
			selectedfunctionsDAO.saveOrUpdate(selectedfunctions);			
			return true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(SelectedFunctions selectedfunctions) {
		// TODO Auto-generated method stub
		try {
			selectedfunctionsDAO.delete(selectedfunctions);
			return true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public SelectedFunctions getSelectedFunctions(Integer id) {
		// TODO Auto-generated method stub
		try {
			return selectedfunctionsDAO.find(SelectedFunctions.class, id);
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public List<SelectedFunctions> getSelectedFunctionsList() {
		// TODO Auto-generated method stub
		try {
			return selectedfunctionsDAO.findAll(SelectedFunctions.class);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}

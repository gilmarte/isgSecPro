package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.SaSelectedFunctions;
import com.isg.ifrend.model.dao.SaSelectedFunctionsDAO;

public class SaSelectedFunctionsManagerImpl implements
SaSelectedFunctionsManager {

	SaSelectedFunctionsDAO saselectedfunctionsDAO;


	public SaSelectedFunctionsDAO getSaselectedfunctionsDAO() {
		return saselectedfunctionsDAO;
	}

	public void setSaselectedfunctionsDAO(
			SaSelectedFunctionsDAO saselectedfunctionsDAO) {
		this.saselectedfunctionsDAO = saselectedfunctionsDAO;
	}

	@Transactional
	public boolean save(SaSelectedFunctions saselectedfunctions) {
		// TODO Auto-generated method stub
		try {
			saselectedfunctionsDAO.saveOrUpdate(saselectedfunctions);			
			return true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean delete(SaSelectedFunctions saselectedfunctions) {
		// TODO Auto-generated method stub
		try {
			saselectedfunctionsDAO.delete(saselectedfunctions);
			return true;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public SaSelectedFunctions getSaSelectedFunctions(Integer id) {
		// TODO Auto-generated method stub
		try {
			return saselectedfunctionsDAO.find(SaSelectedFunctions.class, id);

		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public List<SaSelectedFunctions> getSaSelectedFunctionsList() {
		// TODO Auto-generated method stub
		try {
			return saselectedfunctionsDAO.findAll(SaSelectedFunctions.class);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}

package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.isg.ifrend.model.bean.BusinessEntity;
import com.isg.ifrend.model.dao.BusinessEntityDAO;
import com.isg.ifrend.utils.GlobalUtils;

public class BusinessEntityManagerImpl implements BusinessEntityManager {
	
	private BusinessEntityDAO businessEntityDAO;

	@Override
	public boolean save(BusinessEntity businessEntity) {
		try {
			businessEntityDAO.saveOrUpdate(businessEntity);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(BusinessEntity businessEntity) {
		try {
			businessEntityDAO.delete(businessEntity);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public BusinessEntity findById(String businessEntityCode) {
		try {
			return businessEntityDAO.find(BusinessEntity.class, businessEntityCode);
		}
		catch (IllegalArgumentException e) {
			Exception exception = (Exception)GlobalUtils.getRootCauseException(e);
			if(exception.getMessage().contains("id to load")) {
				BusinessEntity busEntity = new BusinessEntity();
				busEntity.setBus_ent_code("");
				busEntity.setBus_ent_desc("");
				return busEntity;
			}
			e.printStackTrace();
			return null;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BusinessEntity> getBusinessEntityList() {
		try {
			return businessEntityDAO.findAll(BusinessEntity.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public BusinessEntityDAO getBusinessEntityDAO() {
		return businessEntityDAO;
	}

	public void setBusinessEntityDAO(BusinessEntityDAO busEntityDAO) {
		this.businessEntityDAO = busEntityDAO;
	}


}

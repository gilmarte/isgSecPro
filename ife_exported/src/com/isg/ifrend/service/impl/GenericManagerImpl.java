package com.isg.ifrend.service.impl;

import java.util.Date;
import java.util.List;

import com.isg.ifrend.model.bean.Global;
import com.isg.ifrend.model.dao.impl.GenericDAOImpl;
import com.isg.ifrend.service.GenericManager;

public class GenericManagerImpl implements GenericManager {

	public GenericDAOImpl genericDao;

	@Override
	public <T extends Global> List<T> searchGlobalList(T t,
			String idPropertyName) {
		return genericDao.findAll(t, idPropertyName);
	}

	@Override
	public <T extends Global> List<T> searchGlobalList(T t,
			String idPropertyName, String descPropertyName) {
		return genericDao.findAll(t, idPropertyName, descPropertyName);
	}

	@Override
	public <T extends Global> List<T> searchGlobalList(T t,
			String idPropertyName, String descPropertyName, Date startDate,
			Date endDate) {
		return genericDao.findAll(t, idPropertyName, descPropertyName,
				startDate, endDate);
	}

	@Override
	public <T> T viewDetails(Class<T> clazz, String id) {
		return genericDao.find(clazz, id);
	}

	@Override
	public <T> String save(T t) {
		return genericDao.save(t);
	}

	@Override
	public <T> void update(T t) {
		genericDao.update(t);
	}

	@Override
	public <T> void delete(T t) {
		genericDao.delete(t);
	}

	public void setGenericDao(GenericDAOImpl genericDao) {
		this.genericDao = genericDao;
	}
}

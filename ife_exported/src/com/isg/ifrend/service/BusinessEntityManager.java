package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.BusinessEntity;

public interface BusinessEntityManager {

	public boolean save(BusinessEntity businessEntity);
	public boolean delete(BusinessEntity businessEntity);
	public BusinessEntity findById(String businessEntityCode);
	public List<BusinessEntity> getBusinessEntityList();
}

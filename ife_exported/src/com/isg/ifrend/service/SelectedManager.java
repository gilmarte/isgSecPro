package com.isg.ifrend.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface SelectedManager {

	@SuppressWarnings("rawtypes")
	public List getRecordList(Object obj);
	public Object findTemp(Object tempObj, Object id);
	public Object findMast(Object mastObj, Object id);
	@SuppressWarnings("rawtypes")
	public List findChildren(Object classObj, Object id);
	@SuppressWarnings("rawtypes")
	public List findEntityByStatus(Object classObj, int status_id);
	@SuppressWarnings("rawtypes")
	public List findAllMasterDataNotDeleted(Object classObj);
	public boolean requestDeletion(Object obj);
	public boolean authorizeEntities(Object obj);
	public boolean cancelRequest(Object obj);
	@SuppressWarnings("rawtypes")
	public List searchEntityByCriteria(DetachedCriteria criteria);
}

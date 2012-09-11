package com.isg.ifrend.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.EnhancedCriterion;
import com.isg.ifrend.model.bean.HistEnhancedCriteria;
import com.isg.ifrend.model.bean.TempEnhancedCriterion;

public class EnhancedCriteriaDAO extends HibernateDaoSupport {

	public void saveOrUpdateMaster(EnhancedCriterion enhancedCriterion) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(enhancedCriterion);
	}

	public void deleteMaster(EnhancedCriterion enhancedCriterion) throws DataAccessException {
		getHibernateTemplate().delete(enhancedCriterion);
	}

	public EnhancedCriterion findMaster(Class<EnhancedCriterion> enhancedCriterionClass, Integer enhancedcriteria_id) throws DataAccessException {
		EnhancedCriterion enhancedCriteriaBean = (EnhancedCriterion) getHibernateTemplate().load(enhancedCriterionClass, enhancedcriteria_id);
		return enhancedCriteriaBean;
	}
	
	@SuppressWarnings("unchecked")
	public List<EnhancedCriterion> findAllMaster(Class<EnhancedCriterion> enhancedCriterionClass) throws DataAccessException {
		List<EnhancedCriterion> list = getHibernateTemplate().find("from " + enhancedCriterionClass.getName());
		return list;
	}
	
	public void saveOrUpdateTemp(TempEnhancedCriterion tempEnhancedCriterion) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(tempEnhancedCriterion);
	}

	public void deleteTemp(TempEnhancedCriterion tempEnhancedCriterion) throws DataAccessException {
		getHibernateTemplate().delete(tempEnhancedCriterion);
	}

	public TempEnhancedCriterion findTemp(Class<TempEnhancedCriterion> tempEnhancedCriterionClass, Integer enhancedcriteria_id) throws DataAccessException {
		TempEnhancedCriterion tempEnhancedCriterion = (TempEnhancedCriterion) getHibernateTemplate().load(tempEnhancedCriterionClass, enhancedcriteria_id);
		return tempEnhancedCriterion;
	}
	
	@SuppressWarnings("unchecked")
	public List<TempEnhancedCriterion> findAllTemp(Class<TempEnhancedCriterion> tempEnhancedCriterionClass) throws DataAccessException {
		List<TempEnhancedCriterion> list = getHibernateTemplate().find("from " + tempEnhancedCriterionClass.getName());
		return list;
	}

	public void saveOrUpdateHist(HistEnhancedCriteria histEnhancedCriteria) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(histEnhancedCriteria);
	}

	public void deleteHist(HistEnhancedCriteria histEnhancedCriteria) throws DataAccessException {
		getHibernateTemplate().delete(histEnhancedCriteria);
	}

	public HistEnhancedCriteria findHist(Class<HistEnhancedCriteria> histEnhancedCriteriaClass, Integer enhancedcriteria_id) throws DataAccessException {
		HistEnhancedCriteria histEnhancedCriteria = (HistEnhancedCriteria) getHibernateTemplate().load(histEnhancedCriteriaClass, enhancedcriteria_id);
		return histEnhancedCriteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<HistEnhancedCriteria> findAllHist(Class<HistEnhancedCriteria> histEnhancedCriteriaClass) throws DataAccessException {
		List<HistEnhancedCriteria> list = getHibernateTemplate().find("from " + histEnhancedCriteriaClass.getName());
		return list;
	}
}

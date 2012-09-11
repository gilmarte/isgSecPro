package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.HistCriteria;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TempCriteria;
import com.isg.ifrend.model.dao.CriteriaDAO;

public class CriteriaImpl implements CriteriaManager {

	private CriteriaDAO criteriaDAO;

	public CriteriaDAO getCriteriaDAO() {
		return criteriaDAO;
	}

	public void setCriteriaDAO(CriteriaDAO criteriaDAO) {
		this.criteriaDAO = criteriaDAO;
	}

	@Transactional
	public boolean saveMaster(Criteria criteria) {
		try {
			criteriaDAO.saveOrUpdateMaster(criteria);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean deleteMaster(Criteria criteria) {
		try {
			criteriaDAO.deleteMaster(criteria);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public Criteria findByIdMaster(int criteria_id) {
		try {
			return criteriaDAO.findMaster(Criteria.class, criteria_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<Criteria> getMasterCriteriaList() {
		try {
			return criteriaDAO.findAllMaster(Criteria.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<Criteria> getNotDeletedMasterCriteriaList() {
		try {
			return criteriaDAO.findAllNotDeletedMaster(Criteria.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public boolean updateMasterStatus(Criteria criteria){
		try {
			criteriaDAO.updateMasterStatus(criteria);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Transactional
	public String saveTemp(TempCriteria tempCriteria) {
		try {
			tempCriteria.setStatusID(StatusType.PENDING.getId());
				criteriaDAO.saveOrUpdateTemp(tempCriteria);
				return tempCriteria.getId();
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional
	public boolean deleteTemp(TempCriteria tempCriteria) {
		try {
			criteriaDAO.deleteTemp(tempCriteria);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public TempCriteria findByIdTemp(Integer criteria_id) {
		try {
			return criteriaDAO.findTemp(TempCriteria.class, criteria_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<TempCriteria> getTempCriteriaList() {
		try {
			return criteriaDAO.findAllTemp(TempCriteria.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public boolean saveHist(HistCriteria histCriteria) {
		try {
			criteriaDAO.saveHist(histCriteria);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean deleteHist(HistCriteria histCriteria) {
		try {
			criteriaDAO.deleteHist(histCriteria);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public List<HistCriteria> getHistCriteriaList() {
		try {
			return criteriaDAO.findAllHist(HistCriteria.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public boolean requestDeletion(Criteria criteria) {
		try {
			criteriaDAO.requestDeletion(criteria);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Transactional
	public List<TempCriteria> findPendingAuthorizationStatus(int status_id) {
		try {
			return criteriaDAO.findPendingAuthorizationStatus(status_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

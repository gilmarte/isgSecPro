package com.isg.ifrend.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.EnhancedCriterion;
import com.isg.ifrend.model.bean.HistEnhancedCriteria;
import com.isg.ifrend.model.bean.TempEnhancedCriterion;
import com.isg.ifrend.model.dao.EnhancedCriteriaDAO;

public class EnhancedCriteriaImpl implements EnhancedCriteriaManager {

	private EnhancedCriteriaDAO enhancedCriteriaDAO;


	public EnhancedCriteriaDAO getEnhancedCriteriaDAO() {
		return enhancedCriteriaDAO;
	}

	public void setEnhancedCriteriaDAO(EnhancedCriteriaDAO enhancedCriteriaDAO) {
		this.enhancedCriteriaDAO = enhancedCriteriaDAO;
	}

	@Transactional
	public boolean saveMaster(EnhancedCriterion enhancedCriterion) {
		try {
			enhancedCriteriaDAO.saveOrUpdateMaster(enhancedCriterion);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean deleteMaster(EnhancedCriterion enhancedCriterion) {
		try {
			enhancedCriteriaDAO.deleteMaster(enhancedCriterion);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public EnhancedCriterion findByIdMaster(int enhancedcriteria_id) {
		try {
			return enhancedCriteriaDAO.findMaster(EnhancedCriterion.class, enhancedcriteria_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<EnhancedCriterion> getEnhancedMasterCriterionList() {
		try {
			return enhancedCriteriaDAO.findAllMaster(EnhancedCriterion.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public boolean saveTemp(TempEnhancedCriterion tempEnhancedCriterion) {
		try {
			enhancedCriteriaDAO.saveOrUpdateTemp(tempEnhancedCriterion);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean deleteTemp(TempEnhancedCriterion tempEnhancedCriterion) {
		try {
			enhancedCriteriaDAO.deleteTemp(tempEnhancedCriterion);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public TempEnhancedCriterion findByIdTemp(int enhancedcriteria_id) {
		try {
			return enhancedCriteriaDAO.findTemp(TempEnhancedCriterion.class, enhancedcriteria_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<TempEnhancedCriterion> getEnhancedTempCriterionList() {
		try {
			return enhancedCriteriaDAO.findAllTemp(TempEnhancedCriterion.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public boolean saveHist(HistEnhancedCriteria histEnhancedCriteria) {
		try {
			enhancedCriteriaDAO.saveOrUpdateHist(histEnhancedCriteria);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean deleteHist(HistEnhancedCriteria histEnhancedCriteria) {
		try {
			enhancedCriteriaDAO.deleteHist(histEnhancedCriteria);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional(readOnly = true)
	public HistEnhancedCriteria findByIdHist(int enhancedcriteria_id) {
		try {
			return enhancedCriteriaDAO.findHist(HistEnhancedCriteria.class, enhancedcriteria_id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<HistEnhancedCriteria> getEnhancedHistCriterionList() {
		try {
			return enhancedCriteriaDAO.findAllHist(HistEnhancedCriteria.class);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

}

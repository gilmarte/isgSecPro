package com.isg.ifrend.service;

import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.isg.ifrend.model.bean.HistCriteria;
import com.isg.ifrend.model.dao.HistoryCriteriaDAO;

public class HistoryCriteriaManagerImpl implements HistoryCriteriaManager {
	
	private HistoryCriteriaDAO historycriteriaDAO;
	
	
	
	public HistoryCriteriaDAO getHistorycriteriaDAO() {
		return historycriteriaDAO;
	}



	public void setHistorycriteriaDAO(HistoryCriteriaDAO historycriteriaDAO) {
		this.historycriteriaDAO = historycriteriaDAO;
	}



	@Transactional(readOnly = true)
	public List<HistCriteria> findAllHist(){
		try{
			return historycriteriaDAO.findAllHist(HistCriteria.class);
		} catch (DataAccessException e){
			e.printStackTrace();
			return null;
		}
	}



	@Override
	public HistCriteria findByID(Integer histCriterion_id) {
		// TODO Auto-generated method stub
		return historycriteriaDAO.findByID(histCriterion_id) ;
	}

}

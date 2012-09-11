package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.HistCriteria;
import com.isg.ifrend.model.bean.TempCriteria;

public interface CriteriaManager {

	public boolean saveMaster(Criteria criteria);
	public boolean deleteMaster(Criteria criteria);
	public Criteria findByIdMaster(int criteria_id);
	public List<Criteria> getMasterCriteriaList();
	public List<Criteria> getNotDeletedMasterCriteriaList();
	public boolean updateMasterStatus(Criteria criteria);
	
	public String saveTemp(TempCriteria tempCriteria);
	public boolean deleteTemp(TempCriteria tempCriteria);
	public TempCriteria findByIdTemp(Integer criteria_id);
	public List<TempCriteria> getTempCriteriaList();
	
	public boolean saveHist(HistCriteria histCriteria);
	public boolean deleteHist(HistCriteria histCriteria);
	public List<HistCriteria> getHistCriteriaList();
	
	public boolean requestDeletion(Criteria criteria);
	public List<TempCriteria> findPendingAuthorizationStatus(int status_id);
}

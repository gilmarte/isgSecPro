package com.isg.ifrend.service;

import java.util.List;

import com.isg.ifrend.model.bean.EnhancedCriterion;
import com.isg.ifrend.model.bean.HistEnhancedCriteria;
import com.isg.ifrend.model.bean.TempEnhancedCriterion;

public interface EnhancedCriteriaManager {

	public boolean saveMaster(EnhancedCriterion enhancedCriterion);
	public boolean deleteMaster(EnhancedCriterion enhancedCriterion);
	public EnhancedCriterion findByIdMaster(int enhanced_criteria_id);
	public List<EnhancedCriterion> getEnhancedMasterCriterionList();
	
	public boolean saveTemp(TempEnhancedCriterion tempEnhancedCriterion);
	public boolean deleteTemp(TempEnhancedCriterion tempEnhancedCriterion);
	public TempEnhancedCriterion findByIdTemp(int enhanced_criteria_id);
	public List<TempEnhancedCriterion> getEnhancedTempCriterionList();
	
	public boolean saveHist(HistEnhancedCriteria histEnhancedCriteria);
	public boolean deleteHist(HistEnhancedCriteria histEnhancedCriteria);
	public HistEnhancedCriteria findByIdHist(int enhanced_criteria_id);
	public List<HistEnhancedCriteria> getEnhancedHistCriterionList();
}

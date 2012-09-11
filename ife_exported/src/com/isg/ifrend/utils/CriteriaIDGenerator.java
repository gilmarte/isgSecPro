package com.isg.ifrend.utils;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.isg.ifrend.model.bean.CriteriaMaxID;
import com.isg.ifrend.model.bean.TempCriteria;
import com.isg.ifrend.service.CriteriaMaxIDManager;
import com.isg.ifrend.service.ServiceLocator;

public class CriteriaIDGenerator implements IdentifierGenerator{

	public Serializable generate(SessionImplementor arg0, Object entityClass) throws HibernateException { 

		CriteriaMaxIDManager criteriaMaxIDManager = ServiceLocator.getCriteriaMaxIDManager();

		TempCriteria tempCriteria = (TempCriteria) entityClass;
		CriteriaMaxID criteriaMaxID = new CriteriaMaxID();
		
		if ((tempCriteria.isNew()) || StringUtils.isEmpty(tempCriteria.getCriteria_id())){
			int idFinal = criteriaMaxIDManager.getCriteriaMaxID();
			criteriaMaxID.setCriteriaMax_id(idFinal);
			criteriaMaxIDManager.delete(criteriaMaxID);
			
			criteriaMaxID.setCriteriaMax_id(idFinal +1);
			criteriaMaxIDManager.save(criteriaMaxID);
				
			return GlobalUtils.formatID(Commons.PREFIX_CRITERIA, String.valueOf(idFinal + 1));
			
		} else {
			return GlobalUtils.formatID(Commons.PREFIX_CRITERIA, tempCriteria.getCriteria_id());

		}
	} 
}

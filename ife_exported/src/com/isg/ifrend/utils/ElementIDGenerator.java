package com.isg.ifrend.utils;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.isg.ifrend.model.bean.FunctionMaxID;
import com.isg.ifrend.model.bean.GlobalMaxID;
import com.isg.ifrend.model.bean.TmpElement;
import com.isg.ifrend.service.FunctionMaxIDManager;
import com.isg.ifrend.service.GlobalMaxIDManager;
import com.isg.ifrend.service.ServiceLocator;

public class ElementIDGenerator implements IdentifierGenerator{

	public Serializable generate(SessionImplementor arg0, Object entityClass) throws HibernateException { 

		GlobalUtils globalUtils = new GlobalUtils();
		GlobalMaxIDManager globalMaxIDManager = ServiceLocator.getGlobalMaxIDManager();
		FunctionMaxIDManager functionMaxIDManager = ServiceLocator.getFunctionMaxIDManager();

		FunctionMaxID functionMaxID = new FunctionMaxID();
		GlobalMaxID globalMaxID = new GlobalMaxID();

		TmpElement tmpElementsBean = (TmpElement) entityClass;
		String elemID = null;
		String elemIDNo = null;
		String prefix = null;

		List<GlobalMaxID> globalMaxIDList;
		List<FunctionMaxID> functionsMaxIDList;

		if (tmpElementsBean.isNew()){
			int elemType = tmpElementsBean.getTmp_elemtype_id();
			if (elemType == 2){
				prefix = globalUtils.getGlobalPropValue(Commons.FUNC_MAX_ID_PREFIX);
				functionsMaxIDList = functionMaxIDManager.getFunctionMaxIDList();
				if (functionsMaxIDList.size() > 0){
					FunctionMaxID fMaxIDBean = functionsMaxIDList.get(0);
					elemIDNo = this.getElementIDNo(fMaxIDBean);
					elemID = prefix + elemIDNo;

					functionMaxID.setFunctionMax_id(Integer.valueOf(elemIDNo));
					functionMaxIDManager.delete(fMaxIDBean);
					functionMaxIDManager.save(functionMaxID);
				} else {
					elemIDNo = globalUtils.getGlobalPropValue(Commons.ELEM_ID_DEFAULT);
					elemID = prefix + globalUtils.getGlobalPropValue(Commons.ELEM_IDNO_DEFAULT); 

					functionMaxID.setFunctionMax_id(Integer.valueOf(elemIDNo));
					functionMaxIDManager.save(functionMaxID);
				}

			} else {
				prefix = globalUtils.getGlobalPropValue(Commons.GLOB_MAX_ID_PREFIX);
				globalMaxIDList = globalMaxIDManager.getGlobalMaxIDList();
				if (globalMaxIDList.size() > 0){
					GlobalMaxID gMaxIDBean = globalMaxIDList.get(0);
					elemIDNo = this.getElementIDNo(gMaxIDBean);
					elemID = prefix + elemIDNo;

					globalMaxID.setGlobalMax_id(Integer.valueOf(elemIDNo));
					globalMaxIDManager.delete(gMaxIDBean);
					globalMaxIDManager.save(globalMaxID);
				} else {
					elemIDNo = globalUtils.getGlobalPropValue(Commons.ELEM_ID_DEFAULT);
					elemID = prefix + globalUtils.getGlobalPropValue(Commons.ELEM_IDNO_DEFAULT); 

					globalMaxID.setGlobalMax_id(Integer.valueOf(elemIDNo));
					globalMaxIDManager.save(globalMaxID);
				}
			}
		} else {
			elemID = tmpElementsBean.getTmp_elem_id();
		}

		return elemID;
	} 

	private String getElementIDNo(Object bean){

		String elementIDNo = null;

		GlobalMaxID globalMaxIDBean = new GlobalMaxID();
		FunctionMaxID functionMaxIDBean = new FunctionMaxID();
		GlobalUtils globalUtils = new GlobalUtils();

		int maxID = 0;
		if (bean instanceof GlobalMaxID){
			globalMaxIDBean = (GlobalMaxID) bean;
			maxID = globalMaxIDBean.getGlobalMax_id() + 1;
		} else {
			functionMaxIDBean = (FunctionMaxID) bean;
			maxID = functionMaxIDBean.getFunctionMax_id() + 1;
		}

		if (maxID >= 0 && maxID <= 9 ){
			elementIDNo = globalUtils.getGlobalPropValue(Commons.RANGE_0_9_PAD) + String.valueOf(maxID);
		} else if (maxID >= 10 && maxID <= 99 ){
			elementIDNo = globalUtils.getGlobalPropValue(Commons.RANGE_10_99_PAD) + String.valueOf(maxID);
		} else if (maxID >= 100 && maxID <= 999 ){
			elementIDNo = globalUtils.getGlobalPropValue(Commons.RANGE_100_999_PAD) +  String.valueOf(maxID);
		} else {
			elementIDNo = String.valueOf(maxID++);
		}

		return elementIDNo;
	}
}

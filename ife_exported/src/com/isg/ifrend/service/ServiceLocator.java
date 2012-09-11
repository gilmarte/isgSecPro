package com.isg.ifrend.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.zkoss.zkplus.spring.SpringUtil;

import com.isg.ifrend.model.bean.CodeType;
import com.isg.ifrend.model.bean.Criteria;
import com.isg.ifrend.model.bean.Element;
import com.isg.ifrend.model.bean.Label;
import com.isg.ifrend.model.bean.Mli;
import com.isg.ifrend.model.bean.Script;
import com.isg.ifrend.model.bean.TempCriteria;
import com.isg.ifrend.model.bean.TmpCodeType;
import com.isg.ifrend.model.bean.TmpElement;
import com.isg.ifrend.model.bean.TmpLabel;
import com.isg.ifrend.model.bean.TmpMli;
import com.isg.ifrend.model.bean.TmpScript;
import com.isg.ifrend.service.impl.GenericManagerImpl;

public class ServiceLocator {

	private ServiceLocator() {}
	
	public static Session getHibernateSession() {
		return ((SessionFactory) SpringUtil.getBean("sessionFactory", SessionFactory.class)).getCurrentSession();
	}
	
	public static SelectedManager getSelectedManager() {
		return (SelectedManager) SpringUtil.getBean("selectedManager", SelectedManager.class);
	}
	
	public static ElementTypesManager getElementTypesManager() {
		return (ElementTypesManager) SpringUtil.getBean("elementTypesManager", ElementTypesManager.class);
	}
	
	public static ElementFormatsManager getElementFormatsManager() {
		return (ElementFormatsManager) SpringUtil.getBean("elementFormatsManager", ElementFormatsManager.class);
	}
	
	public static DateFormatsManager getDateFormatsManager() {
		return (DateFormatsManager) SpringUtil.getBean("dateFormatsManager", DateFormatsManager.class);
	}
	
	public static GlobalMaxIDManager getGlobalMaxIDManager() {
		return (GlobalMaxIDManager) SpringUtil.getBean("globalMaxIDManager", GlobalMaxIDManager.class);
	}
	
	public static FunctionMaxIDManager getFunctionMaxIDManager() {
		return (FunctionMaxIDManager) SpringUtil.getBean("functionMaxIDManager", FunctionMaxIDManager.class);
	}
	
	public static CriteriaMaxIDManager getCriteriaMaxIDManager() {
		return (CriteriaMaxIDManager) SpringUtil.getBean("criteriaMaxIDManager", CriteriaMaxIDManager.class);
	}
	
	public static CountryManager getCountryManager() {
		return (CountryManager) SpringUtil.getBean("countryManager", CountryManager.class);
	}
	
	public static FunctionManager getFunctionManager() {
		return (FunctionManager) SpringUtil.getBean("functionManager", FunctionManager.class);
	}
	
	public static PriorityManager getPriorityManager() {
		return (PriorityManager) SpringUtil.getBean("priorityManager", PriorityManager.class);
	}
	
	public static UserFieldManager getUserFieldManager() {
		return (UserFieldManager) SpringUtil.getBean("userFieldManager", UserFieldManager.class);
	}
	
	public static ActionManager getActionManager() {
		return (ActionManager) SpringUtil.getBean("actionManager", ActionManager.class);
	}

	public static OperatorManager getOperatorManager() {
		return (OperatorManager) SpringUtil.getBean("operatorManager", OperatorManager.class);
	}

	public static CriteriaManager getCriteriaManager() {
		return (CriteriaManager) SpringUtil.getBean("criteriaManager", CriteriaManager.class);
	}
	
	@SuppressWarnings("unchecked")
	public static GlobalManager<Criteria, TempCriteria> getCriteriaManager2() {
		return (GlobalManager<Criteria, TempCriteria>) SpringUtil.getBean("criteriaManager2");
	}

		
	public static HistoryCriteriaManager getHistoryCriteriaManager() {
		return (HistoryCriteriaManager) SpringUtil.getBean("historycriteriaManager", HistoryCriteriaManager.class);
	}
	
	public static EnhancedCriteriaManager getEnhancedCriteriaManager() {
		return (EnhancedCriteriaManager) SpringUtil.getBean("enhancedCriteriaManager", EnhancedCriteriaManager.class);
	}
	
	public static LanguageCodeManager getLanguageCodeManager() {
		return (LanguageCodeManager) SpringUtil.getBean("languageCodeManager", LanguageCodeManager.class);
	}
	
	public static CodeManager getCodeManager() {
		return (CodeManager) SpringUtil.getBean("codeManager", CodeManager.class);
	}
	
	/*Global Criteria*/

	public static GenericManagerImpl getGenericManager() {
		return (GenericManagerImpl) SpringUtil.getBean("genericManager");
	}

	@SuppressWarnings("unchecked")
	public static GlobalManager<Element, TmpElement> getElementManager() {
		return (GlobalManager<Element, TmpElement>) SpringUtil.getBean("elementManager");
	}
	
	@SuppressWarnings("unchecked")
	public static GlobalManager<Mli, TmpMli> getMliManager() {
		return (GlobalManager<Mli, TmpMli>) SpringUtil.getBean("mliManager");
	}

	@SuppressWarnings("unchecked")
	public static GlobalManager<Script, TmpScript> getScriptManager() {
		return (GlobalManager<Script, TmpScript>) SpringUtil.getBean("scriptManager");
	}

	@SuppressWarnings("unchecked")
	public static GlobalManager<Label, TmpLabel> getLabelManager() {
		return (GlobalManager<Label, TmpLabel>) SpringUtil.getBean("labelManager");
	}

	@SuppressWarnings("unchecked")
	public static GlobalManager<CodeType, TmpCodeType> getCodeTypeManager() {
		return (GlobalManager<CodeType, TmpCodeType>) SpringUtil.getBean("codeTypeManager");
	}

	/*Security Admin Use*/
	
	public static FunctionsManager getFunctionsManager() {
		return (FunctionsManager) SpringUtil.getBean("functionsManager", FunctionsManager.class);
	}
	
	public static SelectedFunctionsManager getSelectedFunctionsManager() {
		return (SelectedFunctionsManager) SpringUtil.getBean("selectedfunctionsManager", SelectedFunctionsManager.class);
	}
	
	public static SaSelectedFunctionsManager getSaSelectedFunctionsManager() {
		return (SaSelectedFunctionsManager) SpringUtil.getBean("saselectedfunctionsManager", SaSelectedFunctionsManager.class);
	}
		
	public static TmpSaUserGroupManager getTmpSaUserGroupManager() {
		return (TmpSaUserGroupManager) SpringUtil.getBean("tmpsausergroupManager", TmpSaUserGroupManager.class);
	}
	
	public static SaUserGroupHistoryManager getSaUserGroupHistoryManager() {
		return (SaUserGroupHistoryManager) SpringUtil.getBean("sausergrouphistoryManager", SaUserGroupHistoryManager.class);
	}
	
	public static SaUserGroupManager getSaUserGroupManager() {
		return (SaUserGroupManager) SpringUtil.getBean("sausergroupManager", SaUserGroupManager.class);
	}
	
	public static TempUserOrganizationManager getTempUserOrganizationManager() {
		return (TempUserOrganizationManager) SpringUtil.getBean("tempUserOrgManager", TempUserOrganizationManager.class);
	}
	
	public static UserOrganizationManager getUserOrganizationManager() {
		return (UserOrganizationManager) SpringUtil.getBean("userOrgManager", UserOrganizationManager.class);
	}
	
	public static HistUserOrganizationManager getHistUserOrganizationManager() {
		return (HistUserOrganizationManager) SpringUtil.getBean("histUserOrgManager", HistUserOrganizationManager.class);
	}

	public static UserManager getUserManager() {
		return (UserManager) SpringUtil.getBean("userManager", UserManager.class);
	}
	
	public static TempUserManager getTempUserManager() {
		return (TempUserManager) SpringUtil.getBean("tempUserManager", TempUserManager.class);
	}
	
	public static HistUserManager getHistUserManager() {
		return (HistUserManager) SpringUtil.getBean("histUserManager", HistUserManager.class);
	}

	public static BusinessEntityManager getBusinessEntity() {
		return (BusinessEntityManager) SpringUtil.getBean("businessEntityManager", BusinessEntityManager.class);
	}
	
}

package com.isg.ifrend.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.Script;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TmpScript;

/**
 * 
 * @author kristine.furto
 * 
 */
public class ScriptDAO extends HibernateDaoSupport {

	public void saveOrUpdateTmp(TmpScript tmpScript) throws DataAccessException {

		getHibernateTemplate().saveOrUpdate(tmpScript);
	}

	public void deleteTmp(TmpScript tmpScript) throws DataAccessException {
		getHibernateTemplate().delete(tmpScript);
	}

	public void saveOrUpdate(Script script) throws DataAccessException {

		getHibernateTemplate().saveOrUpdate(script);
	}

	public void delete(Script script) throws DataAccessException {
		getHibernateTemplate().delete(script);
	}

	public Script find(Class<Script> scriptClass, int scriptID)
			throws DataAccessException {
		Script script = (Script) getHibernateTemplate().load(scriptClass,
				scriptID);
		return script;
	}

	public TmpScript findTmp(Class<TmpScript> tmpScriptClass, int tmpScriptID)
			throws DataAccessException {
		TmpScript tmpScript = (TmpScript) getHibernateTemplate().load(
				tmpScriptClass, tmpScriptID);
		return tmpScript;
	}

	@SuppressWarnings("unchecked")
	public List<Script> findAllNotDeleted(Class<Script> scriptClass) throws DataAccessException {
		List<Script> list = getHibernateTemplate().find("from " + scriptClass.getName() + " where scriptID != 14");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Script> findAll(Class<Script> scriptClass)
			throws DataAccessException {
		List<Script> list = getHibernateTemplate().find(
				"from " + scriptClass.getName());
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<TmpScript> findAllTmp(Class<TmpScript> tmpScriptClass)
			throws DataAccessException {
		List<TmpScript> list = getHibernateTemplate().find(
				"from " + tmpScriptClass.getName());
		return list;
	}

	public void requestDeletion(Script script){
		script.setStatusID(StatusType.PENDING.getId());
		script.setActionID(ActionType.DELETE.getId());
		getHibernateTemplate().update(script);
		
		this.insertCopyRecordToTemp(script);
		
//		HistScript histScript = new HistScript(new TmpScript(script));
		//		getHibernateTemplate().save(histScript);
	}
	
	public void insertCopyRecordToTemp(Script script){
		
		StringBuilder queryStr = new StringBuilder();
		queryStr.append("INSERT INTO TmpScript (");
		queryStr.append("scriptID,");
		queryStr.append("scriptCountryCode,");
		queryStr.append("scriptFunctionTypeID,");
		queryStr.append("scriptFunctionCode,");
		queryStr.append("scriptUserFieldID,");
		queryStr.append("scriptPriorityID,");
		queryStr.append("scriptDesc,");
		queryStr.append("scriptMessageTypeID,");
		queryStr.append("scriptMessage,");
		queryStr.append("scriptCommentTypeID,");
		queryStr.append("scriptComment,");
		queryStr.append("scriptStatusID,");
		queryStr.append("scriptCreatorUserID,");
		queryStr.append("scriptDateCreated,");
		queryStr.append("scriptApproverUserID,");
		queryStr.append("scriptDateApproved,");
		queryStr.append("scriptLastModifiedUserID,");
		queryStr.append("scriptLastDateModified ");
		
		queryStr.append("SELECT ");
		queryStr.append("a.scriptID,");
		queryStr.append("a.scriptCountryCode,");
		queryStr.append("a.scriptFunctionTypeID,");
		queryStr.append("a.scriptFunctionCode,");
		queryStr.append("a.scriptUserFieldID,");
		queryStr.append("a.scriptPriorityID,");
		queryStr.append("a.scriptDesc,");
		queryStr.append("a.scriptMessageTypeID,");
		queryStr.append("a.scriptMessage,");
		queryStr.append("a.scriptCommentTypeID,");
		queryStr.append("a.scriptComment,");
		queryStr.append("a.scriptStatusID,");
		queryStr.append("a.scriptCreatorUserID,");
		queryStr.append("a.scriptDateCreated,");
		queryStr.append("a.scriptApproverUserID,");
		queryStr.append("a.scriptDateApproved,");
		queryStr.append("a.scriptLastModifiedUserID,");
		queryStr.append("a.scriptLastDateModified ");
		queryStr.append("from Script a where a.scriptID=?");

		Query query = getSession().createQuery(queryStr.toString());
		query.setParameter(0,script.getScriptID());
		query.executeUpdate();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<TmpScript> findPendingAuthorizationStatus(int status_id){
		StringBuilder queryStr = new StringBuilder();
		queryStr.append("SELECT ");
		queryStr.append("scriptID,");
		queryStr.append("scriptCountryCode,");
		queryStr.append("scriptFunctionTypeID,");
		queryStr.append("scriptFunctionCode,");
		queryStr.append("scriptUserFieldID,");
		queryStr.append("scriptPriorityID,");
		queryStr.append("scriptDesc,");
		queryStr.append("scriptMessageTypeID,");
		queryStr.append("scriptMessage,");
		queryStr.append("scriptCommentTypeID,");
		queryStr.append("scriptComment,");
		queryStr.append("scriptStatusID,");
		queryStr.append("scriptCreatorUserID,");
		queryStr.append("scriptDateCreated,");
		queryStr.append("scriptApproverUserID,");
		queryStr.append("scriptDateApproved,");
		queryStr.append("scriptLastModifiedUserID,");
		queryStr.append("scriptLastDateModified ");
		queryStr.append("FROM ");
		queryStr.append("TmpScript ");
		queryStr.append("WHERE ");
		queryStr.append("scriptStatusID = :statusID");
		
		Query query = getSession().createQuery(queryStr.toString());
		query.setInteger("statusID", status_id);

		return query.list();
	}

}

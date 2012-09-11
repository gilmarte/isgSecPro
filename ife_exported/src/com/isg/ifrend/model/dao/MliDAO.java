package com.isg.ifrend.model.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.ActionType;
import com.isg.ifrend.model.bean.Mli;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.bean.TmpMli;

/**
 * 
 * @author kristine.furto
 * 
 */
public class MliDAO extends HibernateDaoSupport {

	public void saveOrUpdateTmp(TmpMli tmpMli) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(tmpMli);
	}

	public void deleteTmp(TmpMli tmpMli) throws DataAccessException {
		getHibernateTemplate().delete(tmpMli);
	}

	public void saveOrUpdate(Mli mli) throws DataAccessException {
		getHibernateTemplate().saveOrUpdate(mli);
	}

	public void delete(Mli mli) throws DataAccessException {
		getHibernateTemplate().delete(mli);
	}

	@SuppressWarnings("unchecked")
	public List<Mli> findAllNotDeleted(Class<Mli> mliClass) throws DataAccessException {
		List<Mli> list = getHibernateTemplate().find("from " + mliClass.getName() + " where mliStatusID != 14");
		return list;
	}

	public Mli find(Class<Mli> mliClass, int mliID) throws DataAccessException {
		Mli mli = (Mli) getHibernateTemplate().load(mliClass, mliID);
		return mli;
	}

	@SuppressWarnings("unchecked")
	public List<Mli> findAll(Class<Mli> mliClass) throws DataAccessException {
		List<Mli> list = getHibernateTemplate().find(
				"from " + mliClass.getName());
		return list;
	}

	public TmpMli findTmp(Class<TmpMli> tmpMliClass, int tmpMliID)
	throws DataAccessException {
		TmpMli tmpMli = (TmpMli) getHibernateTemplate().load(tmpMliClass,
				tmpMliID);
		return tmpMli;
	}

	@SuppressWarnings("unchecked")
	public List<TmpMli> findAllTmp(Class<TmpMli> tmpMliClass)
	throws DataAccessException {
		List<TmpMli> list = getHibernateTemplate().find(
				"from " + tmpMliClass.getName());
		return list;
	}

	public void requestDeletion(Mli mli){
		mli.setStatusID(StatusType.PENDING.getId());
		mli.setActionID(ActionType.DELETE.getId());
		getHibernateTemplate().update(mli);

		this.insertCopyRecordToTemp(mli);
		
		//		HistMli histMli = new HistMli(new TmpMli(mli));
		//		getHibernateTemplate().save(histMli);
	}

	public void insertCopyRecordToTemp(Mli mli){

		StringBuilder queryStr = new StringBuilder();
		queryStr.append("INSERT INTO TmpMli (");
		queryStr.append("mliID,");
		queryStr.append("mliCountryCode,");
		queryStr.append("mliFunctionTypeID,");
		queryStr.append("mliFunctionCode,");
		queryStr.append("mliUserFieldID,");
		queryStr.append("mliResponseCodeID,");
		queryStr.append("mliDesc,");
		queryStr.append("mliMessageTypeID,");
		queryStr.append("mliMessage,");
		queryStr.append("mliCommentTypeID,");
		queryStr.append("mliComment,");
		queryStr.append("mliStatusID,");
		queryStr.append("mliCreatorUserID,");
		queryStr.append("mliDateCreated,");
		queryStr.append("mliApproverUserID,");
		queryStr.append("mliDateApproved,");
		queryStr.append("mliLastModifiedUserID,");
		queryStr.append("mliLastDateModified ");

		queryStr.append("SELECT ");
		queryStr.append("a.mliID,");
		queryStr.append("a.mliCountryCode,");
		queryStr.append("a.mliFunctionTypeID,");
		queryStr.append("a.mliFunctionCode,");
		queryStr.append("a.mliUserFieldID,");
		queryStr.append("a.mliResponseCodeID,");
		queryStr.append("a.mliDesc,");
		queryStr.append("a.mliMessageTypeID,");
		queryStr.append("a.mliMessage,");
		queryStr.append("a.mliCommentTypeID,");
		queryStr.append("a.mliComment,");
		queryStr.append("a.mliStatusID,");
		queryStr.append("a.mliCreatorUserID,");
		queryStr.append("a.mliDateCreated,");
		queryStr.append("a.mliApproverUserID,");
		queryStr.append("a.mliDateApproved,");
		queryStr.append("a.mliLastModifiedUserID,");
		queryStr.append("a.mliLastDateModified ");
		queryStr.append("from Mli a where a.mliID=?");

		Query query = getSession().createQuery(queryStr.toString());
		query.setParameter(0,mli.getMliID());
		query.executeUpdate();

	}

	@SuppressWarnings("unchecked")
	public List<TmpMli> findPendingAuthorizationStatus(int status_id){
		StringBuilder queryStr = new StringBuilder();
		queryStr.append("SELECT ");
		queryStr.append("mliID,");
		queryStr.append("mliCountryCode,");
		queryStr.append("mliFunctionTypeID,");
		queryStr.append("mliFunctionCode,");
		queryStr.append("mliUserFieldID,");
		queryStr.append("mliResponseCodeID,");
		queryStr.append("mliDesc,");
		queryStr.append("mliMessageTypeID,");
		queryStr.append("mliMessage,");
		queryStr.append("mliCommentTypeID,");
		queryStr.append("mliComment,");
		queryStr.append("mliStatusID,");
		queryStr.append("mliCreatorUserID,");
		queryStr.append("mliDateCreated,");
		queryStr.append("mliApproverUserID,");
		queryStr.append("mliDateApproved,");
		queryStr.append("mliLastModifiedUserID,");
		queryStr.append("mliLastDateModified ");
		queryStr.append("FROM ");
		queryStr.append("TmpMli ");
		queryStr.append("WHERE ");
		queryStr.append("mliStatusID = :statusID");
		
		Query query = getSession().createQuery(queryStr.toString());
		query.setInteger("statusID", status_id);

		return query.list();
	}

}

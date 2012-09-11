package com.isg.ifrend.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.Global;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.dao.GlobalDAO;

public abstract class GlobalDAOImpl<M extends Global, T extends Global, H>
		extends HibernateDaoSupport implements GlobalDAO<M, T> {

	private static final String ZERO = "0";
	private static final String QUERY_NEW_ENTITY_START = "SELECT ";
	private static final String QUERY_NEW_ENTITY_END = ".NEXTVAL FROM DUAL";

	private static final String QUERY_UPDATE = "UPDATE ";
	private static final String QUERY_SET = " SET ";
	private static final String QUERY_EQUAL = " = :";
	private static final String QUERY_WHERE = " WHERE ";

	private static final String PROPERTY_ID = "id";
	private static final String PROPERTY_STATUS_ID = "statusID";
	private static final String PROPERTY_ACTION_ID = "actionID";
	private static final String PROPERTY_DELIMITER = ", ";

	protected abstract void setMstSearchCriteria(M m, DetachedCriteria criteria);

	protected abstract void setTmpSearchCriteria(T t, DetachedCriteria criteria);

	protected abstract void setGlobalNewId(T t);

	protected abstract String getMstGlobalName();

	protected abstract Class<M> getMstGlobalClass();

	protected abstract Class<T> getTmpGlobalClass();

	protected abstract M getNewMstInstance();

	protected abstract T getNewTmpInstance();

	protected abstract M toMst(T t);

	protected abstract H toHst(T t);

	protected abstract M getUniqueAttributes(T t);

	@Override
	public List<M> findAllMst() {
		return findAllMst(getNewMstInstance());
	}

	@Override
	public List<T> findAllTmp() {
		return findAllTmp(getNewTmpInstance());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<M> findAllMst(M m) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getMstGlobalClass());

		if (0 == m.getStatusID()) {
			criteria.add(Restrictions.ne(PROPERTY_STATUS_ID,
					StatusType.DELETED.getId()));
		} else {
			criteria.add(Restrictions.eq(PROPERTY_STATUS_ID, m.getStatusID()));
		}

		if (0 != m.getActionID()) {
			criteria.add(Restrictions.eq(PROPERTY_ACTION_ID, m.getActionID()));
		}

		setMstSearchCriteria(m, criteria);

		return getHibernateTemplate().findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllTmp(T t) {
		DetachedCriteria criteria = DetachedCriteria.forClass(t.getClass());

		if (0 != t.getActionID()) {
			criteria.add(Restrictions.eq(PROPERTY_ACTION_ID, t.getActionID()));
		}

		setTmpSearchCriteria(t, criteria);
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public M findMst(String id) {
		return (M) getHibernateTemplate().load(getMstGlobalClass(), id);
	}

	@Override
	public T findTmp(String id) {
		return (T) getHibernateTemplate().load(getTmpGlobalClass(), id);
	}

	@Override
	public void saveMst(T t) {
		getHibernateTemplate().save(toMst(t));
	}

	@Override
	public String saveTmp(T t) {
		if (null == t.getId() || t.getId().isEmpty() || ZERO.equals(t.getId())) {
			setGlobalNewId(t);
		}

		getHibernateTemplate().merge(t);
		return t.getId();
	}

	protected String getSeqNextVal(String sequenceName) {
		StringBuilder sb = new StringBuilder(QUERY_NEW_ENTITY_START).append(
				sequenceName).append(QUERY_NEW_ENTITY_END);

		return ((BigDecimal) getSession().createSQLQuery(sb.toString())
				.uniqueResult()).toString();
	}

	@Override
	public void saveHst(T t) {
		getHibernateTemplate().save(toHst(t));
	}

	@Override
	public void updateMst(T t) {
		getHibernateTemplate().update(toMst(t));
	}

	@Override
	public void updateMstStatusAndAction(T t) {
		StringBuilder sb = new StringBuilder(QUERY_UPDATE)
				.append(getMstGlobalName()).append(QUERY_SET)

				.append(PROPERTY_STATUS_ID).append(QUERY_EQUAL)
				.append(PROPERTY_STATUS_ID)

				.append(PROPERTY_DELIMITER)

				.append(PROPERTY_ACTION_ID).append(QUERY_EQUAL)
				.append(PROPERTY_ACTION_ID)

				.append(QUERY_WHERE)

				.append(PROPERTY_ID).append(QUERY_EQUAL).append(PROPERTY_ID);

		Query query = getSession().createQuery(sb.toString());

		query.setInteger(PROPERTY_STATUS_ID, t.getStatusID());
		query.setInteger(PROPERTY_ACTION_ID, t.getActionID());
		query.setString(PROPERTY_ID, t.getId());

		query.executeUpdate();
	}

	@Override
	public void updateTmp(T t) {
		getHibernateTemplate().update(t);
	}

	@Override
	public void deleteMst(T t) {
		getHibernateTemplate().delete(toMst(t));
	}

	@Override
	public void deleteTmp(T t) {
		getHibernateTemplate().delete(t);
	}

	@Override
	public boolean isNotOnMst(T t) {
		return 0 == findAllMst(getUniqueAttributes(t)).size();
	}
}
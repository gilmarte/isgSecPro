package com.isg.ifrend.model.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.isg.ifrend.model.bean.Global;
import com.isg.ifrend.model.bean.StatusType;
import com.isg.ifrend.model.dao.GenericDAO;
import com.isg.ifrend.utils.DateUtil;

public class GenericDAOImpl extends HibernateDaoSupport implements GenericDAO {

	private static final String PROPERTY_STATUS_ID = "statusID";
	private static final String PROPERTY_ACTION_ID = "actionID";
	private static final String PROPERTY_DATE_LAST_MODIFIED = "dateLastModified";
	private static final String PROPERTY_DATE_APPROVED = "dateApproved";

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Global> List<T> findAll(T t, String idPropertyName,
			String descPropertyName) {
		return getHibernateTemplate().findByCriteria(
				setCrtieria(t, idPropertyName, descPropertyName).addOrder(
						Order.asc(idPropertyName)));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Global> List<T> findAll(T t, String idPropertyName) {
		return getHibernateTemplate().findByCriteria(
				setCrtieria(t, idPropertyName, null).addOrder(
						Order.asc(idPropertyName)));
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Global> List<T> findAll(T t, String idPropertyName,
			String descPropertyName, Date startDate, Date endDate) {

		DetachedCriteria criteria = setCrtieria(t, idPropertyName,
				descPropertyName);

		endDate = DateUtil.add(endDate, 1);

		criteria.add(Restrictions.or(Restrictions.between(
				PROPERTY_DATE_LAST_MODIFIED, startDate, endDate), Restrictions
				.between(PROPERTY_DATE_APPROVED, startDate, endDate)));

		criteria.addOrder(Order.desc(PROPERTY_DATE_LAST_MODIFIED));
		criteria.addOrder(Order.desc(PROPERTY_DATE_APPROVED));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	private <T extends Global> DetachedCriteria setCrtieria(T t,
			String idPropertyName, String descPropertyName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(t.getClass());

		if (StringUtils.isNotEmpty(t.getId())) {
			criteria.add(Restrictions.ilike(idPropertyName, t.getId(),
					MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotEmpty(t.getDesc())) {
			criteria.add(Restrictions.ilike(descPropertyName, t.getDesc(),
					MatchMode.ANYWHERE));
		}

		if (0 == t.getStatusID()) {
			criteria.add(Restrictions.ne(PROPERTY_STATUS_ID,
					StatusType.DELETED.getId()));
		} else {
			criteria.add(Restrictions.eq(PROPERTY_STATUS_ID, t.getStatusID()));
		}

		if (0 != t.getActionID()) {
			criteria.add(Restrictions.eq(PROPERTY_ACTION_ID, t.getActionID()));
		}
		return criteria;
	}

	@Override
	public <T> T find(Class<T> clazz, String id) {
		if (StringUtils.isNumeric(id)) {
			return (T) getHibernateTemplate().load(clazz, Integer.parseInt(id));

		} else {
			return (T) getHibernateTemplate().load(clazz, id);
		}
	}

	@Override
	public <T> String save(T t) {
		return getHibernateTemplate().save(t).toString();
	}

	@Override
	public <T> void update(T t) {
		getHibernateTemplate().update(t);
	}

	@Override
	public <T> void delete(T t) {
		getHibernateTemplate().delete(t);
	}
}
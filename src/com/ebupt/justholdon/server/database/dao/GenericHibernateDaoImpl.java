package com.ebupt.justholdon.server.database.dao;

import java.io.Serializable;
//import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.BaseEntity;

@Transactional
public class GenericHibernateDaoImpl<T extends BaseEntity<?>, PK extends Serializable>
		implements GenericHibernateDao<T, PK> {

	@Autowired
	SessionFactory sessionFactory;
	private Class<T> type;
	private List<Order> orders = null;
	private List<Order> globalOrders = null;
	
	public GenericHibernateDaoImpl(Class<T> type) {
		this.type = type;
	}

	@Override
	public void setOrder(List<Order> order) {
		this.orders = order;
	}

	public Session currentSession() {
		if (null == sessionFactory)
			return null;
		return sessionFactory.getCurrentSession();
	}

	@Override
	public PK create(T newInstance) {
		return save(newInstance);
	}

	// @Override
	// public T read(PK id) {
	// return get(id);
	// }

	@Override
	public void update(T transientObject) {
		// transientObject.setModifyTime(new Date());
		currentSession().update(transientObject);
	}

	@Override
	public void delete(T persistentObject) {
		currentSession().delete(persistentObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PK save(T newInstance) {
		if (null == newInstance)
			throw new java.lang.NullPointerException("can't save NULL obj!");
		return (PK) currentSession().save(newInstance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(PK id) {
		T obj = (T) currentSession().get(type, id);
		if (null == obj)
			throw new java.lang.NullPointerException(
					"can't get object from [id]" + id);
		return obj;
	}

	@Override
	public void delete(PK id) {
		currentSession().delete(currentSession().get(type, id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		String tbName = type.getName();
		StringBuilder hql = new StringBuilder().append("from ").append(tbName);
		Query query = currentSession().createQuery(hql.toString());
		return query.list();
	}

	public List<T> findByCriteria(int maxResults,Criterion... criterion) {
		return findByCriteria(null,maxResults,criterion);
	}

	@Override
	public List<?> find(String sql, Integer limit, Object... args) {
		Query query = currentSession().createQuery(sql);
		int count = 0;
		if (null != args)
			for (Object arg : args) {
				query.setParameter(count, arg);
				count++;
			}
		if (null != limit)
			query.setMaxResults(limit);
		return query.list();

	}

	@Override
	public int update(PK id, Map<String, Object> infos) {
		if (null == infos)
			return -1;
		if (infos.isEmpty())
			return -1;
		String tbName = type.getName();
		StringBuilder update = new StringBuilder();
		update.append("update ").append(tbName).append(" set ");
		for (String key : infos.keySet()) {
			update.append(key + " = :" + key).append(",");
		}
		update.deleteCharAt(update.length() - 1);
		update.append(" where ").append("id = ").append(id.toString());
		Query query = currentSession().createQuery(update.toString());
		for (Entry<String, Object> entry : infos.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.executeUpdate();
	}

	// @SuppressWarnings("unchecked")
	// @Override
	// public T load(PK id) {
	// return (T) currentSession().load(type, id);
	// }

	@Override
	public void saveOrUpdate(T transientObject) {
		currentSession().saveOrUpdate(transientObject);
	}

	// @Override
	// public void merge(T transientObject) {
	// currentSession().merge(transientObject);
	// }

	@Override
	public void flush() {
		currentSession().flush();
	}

	@Override
	public Integer countByCriteria(Criterion... criterion) {
		Criteria crit = currentSession().createCriteria(type);
		for (Criterion c : criterion) {
			crit.add(c);
		}
		crit.setProjection(Projections.rowCount());
		List<?> ret = crit.list();
		return ((Long)ret.get(0)).intValue();
	}

	@Override
	public List<T> findByCriteria(Criterion... criterion) {
		return findByCriteria(null,-1,criterion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Order order) {
		Criteria crit = currentSession().createCriteria(type);
		if(null != order)
			crit.addOrder(order);
		return crit.list();
	}
	private void addOrders(Criteria crit,List<Order>orders)
	{
		for(Order order:orders)
			if(null != order)
				crit.addOrder(order);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(ProjectionList list,Integer maxResults,Criterion... criterion) {
		Criteria crit = currentSession().createCriteria(type);
		for (Criterion c : criterion) {
			crit.add(c);
		}
		if (null != orders)
			addOrders(crit,orders);
		else if(null != globalOrders)
			addOrders(crit,globalOrders);
		if(-1 != maxResults)
			crit.setMaxResults(maxResults);
		if(null != list)
			crit.setProjection(list);
		return crit.list();
	}

	@Override
	public List<T> findByCriteria(ProjectionList list, Criterion... criterion) {
		return findByCriteria(list,-1,criterion);
	}

	@Override
	public void setGlobalOrder(List<Order> order) {
		this.globalOrders = order;
	}
}

package com.ebupt.justholdon.server.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

 
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GenericHibernateDaoImpl<T, PK extends Serializable> implements
		GenericHibernateDao<T, PK> {

	@Autowired
	SessionFactory sessionFactory;
	private Class<T> type;

	public GenericHibernateDaoImpl(Class<T> type) {
		this.type = type;
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

	@Override
	public T read(PK id) {
		return get(id);
	}

	@Override
	public void update(T transientObject) {
		//currentSession().update(transientObject);
		currentSession().saveOrUpdate(transientObject);
	//	currentSession().flush();
	}

	@Override
	public void delete(T persistentObject) {
		currentSession().delete(persistentObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PK save(T newInstance) {
		return (PK) currentSession().save(newInstance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(PK id) {
		return (T) currentSession().get(type, id);
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

	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = currentSession().createCriteria(type);
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String sql,Object... args) {
		Query query = currentSession().createQuery(sql);
		int count = 0;
		for(Object arg:args){
			query.setParameter(count, arg);
			count ++;
		}
		return query.list();
	}

	@Override
	public int update(PK id, Map<String, Object> infos) {
		if(null == infos) return -1;
		if(infos.isEmpty()) return -1;
		String tbName = type.getName();
		StringBuilder update = new StringBuilder();
		update.append("update ").append(tbName).append(" set ");
		for(String key:infos.keySet())
		{
			update.append(key+" = :"+key).append(",");
		}
		update.deleteCharAt(update.length()-1);
		update.append(" where ").append("id = ").append(id.toString());
		
		Query query = currentSession().createQuery(update.toString());
		for(Entry<String,Object> entry: infos.entrySet())
		{
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return  query.executeUpdate();
	}
	@SuppressWarnings("unchecked")
	@Override
	public T load(PK id) {
		return (T) currentSession().load(type, id);
	}
}

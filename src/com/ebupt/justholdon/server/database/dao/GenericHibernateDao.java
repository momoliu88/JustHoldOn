package com.ebupt.justholdon.server.database.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;

public interface GenericHibernateDao<T, PK extends Serializable> {

	/** Persist the newInstance object into database */
	PK create(T newInstance);

	PK save(T newInstance);

	/**
	 * Retrieve an object that was previously persisted to the database using
	 * the indicated id as primary key
	 */
	// T read(PK id);

	T get(PK id);

	// T load(PK id);

	/** Save changes made to a persistent object. */
	void update(T transientObject);
	void setOrder(List<Order> order);
	void setGlobalOrder(List<Order> order);

	int update(PK id, Map<String, Object> infos);

	// void merge(T transientObject);
	void saveOrUpdate(T transientObject);

	/** Remove an object from persistent storage in the database */
	void delete(T persistentObject);

	void delete(PK id);

	void flush();

	List<?> find(String sql, Integer limit, Object... args);

	List<T> findAll();
	List<T> findAll(Order order);
	List<T> findByCriteria(Criterion... criterion);
	List<T> findByCriteria(ProjectionList list,Integer maxResults,Criterion... criterion);
	List<T> findByCriteria(ProjectionList list,Criterion... criterion);
	List<T> findByCriteria(int maxResults,Criterion... criterion);
	Integer countByCriteria(Criterion...criterion);

}
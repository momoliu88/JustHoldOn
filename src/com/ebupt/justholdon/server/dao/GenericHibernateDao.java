package com.ebupt.justholdon.server.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

public interface GenericHibernateDao<T, PK extends Serializable> {

	/** Persist the newInstance object into database */
	PK create(T newInstance);

	PK save(T newInstance);

	/**
	 * Retrieve an object that was previously persisted to the database using
	 * the indicated id as primary key
	 */
	T read(PK id);
	
	T get(PK id);
	T load(PK id);

	/** Save changes made to a persistent object. */
	void update(T transientObject);
	int update(PK id,Map<String,Object> infos);

	/** Remove an object from persistent storage in the database */
	void delete(T persistentObject);

	void delete(PK id);


	List<T> find(String sql, Object... args);

	List<T> findAll();

	List<T> findByCriteria(Criterion... criterion);

}
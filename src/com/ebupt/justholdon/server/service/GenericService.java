package com.ebupt.justholdon.server.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface GenericService<T,PK extends Serializable> {
	/** Persist the newInstance object into database */
	PK save(T newInstance);

	/**
	 * Retrieve an object that was previously persisted to the database using
	 * the indicated id as primary key
	 */
	T get(PK id);
	T load(PK id);

	/** Save changes made to a persistent object. */
	void update(T transientObject);
	int update(PK id,Map<String,Object> infos);
	
	/** Remove an object from persistent storage in the database */
	void delete(T persistentObject);

	void delete(PK id);

	List<T> findAll();
}

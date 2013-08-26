package com.ebupt.justholdon.server.database.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.Impression;

@Repository("impressionDao")
@Transactional
public class ImpressionDaoImpl extends
		GenericHibernateDaoImpl<Impression, Integer> implements ImpressionDao {

	public ImpressionDaoImpl(Class<Impression> type) {
		super(type);
	}

	public ImpressionDaoImpl() {
		this(Impression.class);
	}
}

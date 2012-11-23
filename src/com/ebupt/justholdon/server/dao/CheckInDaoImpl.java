package com.ebupt.justholdon.server.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.entity.CheckIn;

@Repository("checkinDao")
@Transactional
public class CheckInDaoImpl extends GenericHibernateDaoImpl<CheckIn, Integer>
		implements CheckInDao {

	public CheckInDaoImpl(Class<CheckIn> type) {
		super(type);
	}

	public CheckInDaoImpl() {
		this(CheckIn.class);
	}

}

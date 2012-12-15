package com.ebupt.justholdon.server.database.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.SystemInfoSended;

@Repository("systemInfoSendedDao")
@Transactional
public class SystemInfoSendedDaoImpl extends
		GenericHibernateDaoImpl<SystemInfoSended, Integer> implements
		SystemInfoSendedDao {

	public SystemInfoSendedDaoImpl(Class<SystemInfoSended> type) {
		super(type);
	}

	public SystemInfoSendedDaoImpl() {
		this(SystemInfoSended.class);
	};
}

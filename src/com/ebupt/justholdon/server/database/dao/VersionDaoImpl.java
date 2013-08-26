package com.ebupt.justholdon.server.database.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.Version;

@Repository("versionDao")
@Transactional
public class VersionDaoImpl  extends GenericHibernateDaoImpl<Version,Integer>implements VersionDao {

	public VersionDaoImpl(Class<Version> type) {
		super(type);
	}

	public VersionDaoImpl(){
		this(Version.class);
	}
}

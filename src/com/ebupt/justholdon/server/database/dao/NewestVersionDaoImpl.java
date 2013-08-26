package com.ebupt.justholdon.server.database.dao;

import java.util.Arrays;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.NewestVersion;

@Repository("newestVersionDao")
@Transactional
public class NewestVersionDaoImpl extends
		GenericHibernateDaoImpl<NewestVersion, Integer> implements
		NewestVersionDao {

	public NewestVersionDaoImpl(Class<NewestVersion> type) {
		super(type);
	}

	public NewestVersionDaoImpl() {
		this(NewestVersion.class);
		setGlobalOrder(Arrays.asList(Order.desc("version")));
	}
}

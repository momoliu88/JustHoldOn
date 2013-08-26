package com.ebupt.justholdon.server.database.dao;
 
import java.util.Arrays;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.SystemInfo;

@Repository("systemInfoDao")
@Transactional
public class SystemInfoDaoImpl extends GenericHibernateDaoImpl<SystemInfo, Integer> implements
		SystemInfoDao {

	public SystemInfoDaoImpl(Class<SystemInfo> type) {
		super(type);
	}
	public SystemInfoDaoImpl()
	{
		this(SystemInfo.class);
		setGlobalOrder(Arrays.asList(Order.desc("createTime")));
	}
}

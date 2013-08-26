package com.ebupt.justholdon.server.database.dao;

import java.util.Arrays;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.WeeklySummary;

@Repository("weeklySummary")
@Transactional
public class WeeklySummaryImpl extends GenericHibernateDaoImpl<WeeklySummary, Integer> implements
		WeeklySummaryDao {

	public WeeklySummaryImpl(Class<WeeklySummary> type) {
		super(type);
	}
	public WeeklySummaryImpl()
	{
		this(WeeklySummary.class);
		setGlobalOrder(Arrays.asList(Order.desc("createTime")));
	}
	 
}

package com.ebupt.justholdon.server.database.dao;

import java.util.Arrays;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.Event;

@Repository("eventDao")
@Transactional
public class EventDaoImpl extends GenericHibernateDaoImpl<Event, Integer>
		implements EventDao {

	public EventDaoImpl(Class<Event> type) {
		super(type);
	}

	public EventDaoImpl() {
		this(Event.class);
		setGlobalOrder(Arrays.asList(Order.desc("createTime")));
	};

}

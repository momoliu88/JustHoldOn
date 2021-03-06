package com.ebupt.justholdon.server.database.dao;

import java.util.Arrays;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.Habit;

@Repository("habitDao")
@Transactional
public class HabitDaoImpl extends GenericHibernateDaoImpl<Habit, Integer> implements
		HabitDao {

	public HabitDaoImpl(Class<Habit> type) {
		super(type);
	}
	public HabitDaoImpl()
	{
		this(Habit.class);
		setGlobalOrder(Arrays.asList(Order.desc("activeUserNum")));
	}
}

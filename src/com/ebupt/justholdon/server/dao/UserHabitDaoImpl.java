package com.ebupt.justholdon.server.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.entity.UserHabit;

@Repository("userHabitDao")
@Transactional 
public class UserHabitDaoImpl extends GenericHibernateDaoImpl<UserHabit, Integer> implements
		UserHabitDao {

	public UserHabitDaoImpl(Class<UserHabit> type) {
		super(type);
	}
	public UserHabitDaoImpl()
	{
		this(UserHabit.class);
	}
}

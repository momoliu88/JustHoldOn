package com.ebupt.justholdon.server.database.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.UserHabit;

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
	@Override
	public void delete(UserHabit persistentObject)
	{	
		persistentObject.setDeleted(true);
		update(persistentObject);
	}
	@Override
	public void delete(Integer id){
		UserHabit userHabit = get(id);
		userHabit.setDeleted(true);
		update(userHabit);
	}
}

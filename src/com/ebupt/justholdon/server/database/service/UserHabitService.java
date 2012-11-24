package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;

public interface UserHabitService extends GenericService<UserHabit,Integer>{
	public boolean connectUserHabit(User user,Habit habit,UserHabit userHabit);
	public boolean connectUserHabit(Long user,Integer habit,UserHabit userHabit);
	public boolean cancelUserHabit(User user,Habit habit,UserHabit userHabit);
	public boolean cancelUserHabit(Long user,Integer habit,UserHabit userHabit);

}

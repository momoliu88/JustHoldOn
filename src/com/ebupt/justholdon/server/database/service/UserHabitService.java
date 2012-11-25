package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.UserHabit;

public interface UserHabitService extends GenericService<UserHabit,Integer>{
	public boolean connectUserHabit(Long uid,Integer hid,UserHabit uHid);
	public boolean cancelUserHabit(Long uid,Integer hid);
	public UserHabit findUserHabit(Long uid,Integer hid);
}

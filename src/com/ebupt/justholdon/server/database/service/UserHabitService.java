package com.ebupt.justholdon.server.database.service;

import java.util.List;

import com.ebupt.justholdon.server.database.entity.UserHabit;

public interface UserHabitService extends GenericService<UserHabit,Integer>{
	public boolean connectUserHabit(Long uid,Integer hid,UserHabit uHid);
	public boolean cancelUserHabit(Long uid,Integer hid);
	
	public UserHabit getUserHabit(Long uid,Integer hid);
	public List<UserHabit> getUserHabits(Long uid);
	public List<UserHabit> getUserHabits(Long uid,Integer startpos,Integer endpos);
	
	public List<UserHabit> getUserHabits(Long uid,Long beWatched);
	public List<UserHabit> getUserHabits(Long uid,Long beWatched,Integer startpos,Integer endpos);

}

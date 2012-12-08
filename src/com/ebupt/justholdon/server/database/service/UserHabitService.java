package com.ebupt.justholdon.server.database.service;

import java.util.List;

import com.ebupt.justholdon.server.database.entity.UserHabit;

public interface UserHabitService extends GenericService<UserHabit,Integer>{
	
	public boolean connectUserHabit(Long uid,Integer hid,UserHabit uHid);
	//user attend habit, and create event
	public void connectUserHabitAndCreateEvent(Long uid,Integer hid,UserHabit uHid,String content);	
	//public boolean exitHabit(Long uid,Integer hid);
	public boolean updateState(Long uid,Integer hid,HabitState state);
	//update state and create event: ING=>CONSOLIDING,CONSOLIDING=>COMPELETED
	public void updateStateAndCteateEvent(Long uid,Integer hid,HabitState state,String content);
	
	public UserHabit getUserHabit(Long uid,Integer hid);
	public UserHabit getUserHabit(Long uid,Long beWatched,Integer hid);

	public List<UserHabit> getUserHabits(Long uid);
	public List<UserHabit> getUserHabits(Long uid,Integer startpos,Integer endpos);
	public List<UserHabit> getUserHabits(Long uid,HabitState habitState);
	public List<UserHabit> getUserHabits(Long uid,HabitState habitState,Integer startpos,Integer endpos);
	
	public List<UserHabit> getUserHabits(Long uid,Long beWatched);
	public List<UserHabit> getUserHabits(Long uid,Long beWatched,HabitState habitState);
	public List<UserHabit> getUserHabits(Long uid,Long beWatched,Integer startpos,Integer endpos);
	public List<UserHabit> getUserHabits(Long uid,Long beWatched,HabitState habitState,Integer startpos,Integer endpos);


}

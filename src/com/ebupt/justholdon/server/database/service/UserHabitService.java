package com.ebupt.justholdon.server.database.service;

import java.util.List;

import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;

public interface UserHabitService extends GenericService<UserHabit,Integer>{
	
	public boolean connectUserHabit(Long uid,Integer hid,UserHabit uHid);
	//user attend habit, and create event
	//used
	public void connectUserHabitAndCreateEvent(Long uid,Integer hid,UserHabit uHid,String content);	
	//used
	public boolean AddUserHabitAndCreateEvent(Long uid,Integer hid,UserHabit uHid,String content);	
	//public boolean exitHabit(Long uid,Integer hid);
	public boolean updateState(Long uid,Integer hid,HabitState state);
	//update state and create event: ING=>CONSOLIDING,CONSOLIDING=>COMPELETED
	public void updateStateAndCteateEvent(Long uid,Integer hid,HabitState state,String content);
	//used
	public UserHabit getUserHabit(Long uid,Integer hid);
	//used
	public UserHabit getUserHabit(Long uid,Long beWatched,Integer hid);

	public Integer countUserHabits(Integer hid,HabitState state);
	// used
	public Integer countUserHabits(Long uid);

		// used
	public Integer countNotDeletedUserHabits(Long uid);

		// used
	public Integer countNotDeletedUserHabits(Long uid, Integer hid);

	public List<User> findParticipate(Integer hid);
	//used
	public List<User> findParticipateAndFriends(Integer hid, Long uid);
	
	public List<UserHabit> getUserHabits(Long uid);
	public List<UserHabit> getUserHabits(Long uid,Integer startId,Integer length,boolean after);
	public List<UserHabit> getUserHabits(Long uid,HabitState habitState);
	public List<UserHabit> getUserHabits(Long uid,HabitState habitState,Integer startId,Integer length,boolean after);
	
	public boolean hasPrivilegeToSee(Long uid,Long beWatched,Integer hid);
	//used
	public List<UserHabit> getUserHabits(Long uid,Long beWatched);
	public List<UserHabit> getUserHabits(Long uid,Long beWatched,HabitState habitState);
	public List<UserHabit> getUserHabits(Long uid,Long beWatched,Integer startId,Integer length,boolean after);
	public List<UserHabit> getUserHabits(Long uid,Long beWatched,HabitState habitState,Integer startId,Integer length,boolean after);
	
	public boolean hasParticipateHabit(Long uid,Integer hid);
	
	//used
	public void deleteUserHabit(Long uid ,Integer hid);
}

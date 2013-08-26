package com.ebupt.justholdon.server.database.service;

import java.util.List;
import java.util.Set;

import com.ebupt.justholdon.server.database.entity.Flag;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.User;

public interface FlagService extends GenericService<Flag,Integer>{
	public Integer createFlag(String content);
	//used
	public List<Flag> findAll(boolean byHot,Integer startId,Integer length,boolean after);
	public List<Flag> findAll(boolean byHot);
	//used
	public List<Flag> findAType(String targetType,Integer startId,Integer length,boolean after);
	// is this need sort?
	public Set<Habit> getHabits(Integer fid);
	public Integer countHabits(Integer fid);
	
	public Set<User> getUsers(Integer fid);
	public Integer countUsers(Integer fid);
	
	public void addUser(Long uid,Integer fid);
	public void removeUser(Long uid,Integer fid);
	public void addHabit(Integer hid,Integer fid);
	public void removeHabit(Integer hid,Integer fid);
	public Boolean hasFlags(Long uid);
	public int findHabitCounts(List<Integer>flagIds);
	public List<Habit> findHabits(List<Integer>flagIds);
	public List<Habit> findHabits(Long uid);
	//used
	public List<Habit> findHabits(List<Integer>flagIds,Integer startId,Integer length,boolean after);

}

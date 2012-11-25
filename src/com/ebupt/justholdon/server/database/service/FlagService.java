package com.ebupt.justholdon.server.database.service;

import java.util.List;

import com.ebupt.justholdon.server.database.entity.Flag;
import com.ebupt.justholdon.server.database.entity.Habit;

public interface FlagService extends GenericService<Flag,Integer>{
	public List<Flag> findAll(boolean byHot,Integer start,Integer end);
	public List<Flag> findAll(boolean byHot);
	public List<Flag> findAType(String targetType,Integer start,Integer end);

	public void addUser(Long uid,Integer fid);
	public void removeUser(Long uid,Integer fid);
	public void addHabit(Integer hid,Integer fid);
	public void removeHabit(Integer hid,Integer fid);
	
	public int findHabitCounts(List<Integer>flagIds);
	public List<Habit> findHabits(List<Integer>flagIds);
	public List<Habit> findHabits(List<Integer>flagIds,Integer start,Integer end);

}

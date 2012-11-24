package com.ebupt.justholdon.server.database.service;

import java.util.List;

import com.ebupt.justholdon.server.database.entity.Flag;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.User;

public interface FlagService extends GenericService<Flag,Integer>{
	public List<Flag> findAll(boolean byHot,Integer start,Integer end);
	public List<Flag> findAll(boolean byHot);
	public List<Flag> findAType(String targetType,Integer start,Integer end);

	public void addUser(User user,Flag flag);
	public void removeUser(User user,Flag flag);
	public void addHabit(Habit habit,Flag flag);
	public void removeHabit(Habit habit,Flag flag);
	
	public int findHabitCounts(List<Integer>flagIds);
	public List<Habit> findHabits(List<Integer>flagIds);
	
}

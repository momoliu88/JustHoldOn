package com.ebupt.justholdon.server.database.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ebupt.justholdon.server.database.entity.Flag;
import com.ebupt.justholdon.server.database.entity.Habit;

public interface HabitService extends GenericService<Habit, Integer> {
	public List<Habit> findAll(boolean byHot);
	//used
	public List<Habit> findAll(boolean byHot,Integer start,Integer length,boolean after);
	public Integer countHabits();
	//used
	public List<Habit> findAGroup(String groupName,Integer startId,Integer length,boolean after);

	public Map<String, Integer> groupNames();
	//used
	public List<Habit> search(String key,Integer startId,Integer length,boolean after,boolean byHot);
	//used
	public List<HabitGroupName> groupNamesList();
	//used
	public int findParticipateNum(Integer hid);

	//used
	public Integer countNotDeletedUserHabits(Integer hid);

	//used
	public Set<Flag> getFlags(Integer hid);
	public Integer countFlags(Integer hid);
	
}

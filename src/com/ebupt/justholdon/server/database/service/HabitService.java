package com.ebupt.justholdon.server.database.service;

import java.util.List;
import java.util.Map;

import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.User;

public interface HabitService extends GenericService<Habit, Integer> {
	public List<Habit> findAll(boolean byHot);
	public List<Habit> findAll(boolean byHot,Integer start,Integer length,boolean after);

	public List<Habit> findAGroup(String groupName);
	public List<Habit> findAGroup(String groupName,Integer startId,Integer length,boolean after);

	public Map<String, Integer> groupNames();

	public List<HabitGroupName> groupNamesList();

	public int findParticipateNum(Integer hid);

	public List<User> findParticipate(Integer hid);

	public List<User> findParticipateAndFriends(Integer hid, Long uid);

}

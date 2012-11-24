package com.ebupt.justholdon.server.database.service;

import java.util.List;
import java.util.Map;

import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.User;

public interface HabitService extends GenericService<Habit, Integer> {
	public List<Habit> findAll(boolean byHot);

	public List<Habit> findAGroup(String groupName);

	public Map<String, Integer> groupNames();
	public List<HabitGroupName> groupNamesList();


	public int findParticipateNum(Integer hid);

	public List<User> findParticipate(Integer hid);

	public List<User> findParticipateAndFriends(Integer hid, Long uid);

}

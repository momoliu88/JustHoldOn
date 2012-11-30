package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.HabitDao;
import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;

@Service("habitService")
@Transactional
public class HabitServiceImpl implements HabitService {
	@Autowired
	private HabitDao habitDao;
	@Autowired
	private UserDao userDao;

	private String habitTbName = Habit.class.getName();

	@Override
	public Integer save(Habit newInstance) {
		return habitDao.save(newInstance);
	}

	@Override
	public Habit get(Integer id) {
		return habitDao.get(id);
	}
//
//	@Override
//	public Habit load(Integer id) {
//		return habitDao.load(id);
//	}

	@Override
	public void update(Habit transientObject) {
		habitDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return habitDao.update(id, infos);
	}
	
//	@Override
//	public void delete(Habit persistentObject) {
//		habitDao.delete(persistentObject);
//	}

	@Override
	public void delete(Integer id) {
		habitDao.delete(id);
	}

	@Override
	public List<Habit> findAll() {
		return habitDao.findAll();
	}

	@Override
	public List<Habit> findAll(boolean byHot) {
		List<Habit> list = habitDao.findAll();
		if(null == list) return null;
		if (byHot)
			Collections.sort(list, Habit.getHotComparator());
		return list;
	}

	@Override
	public Map<String, Integer> groupNames() {
		StringBuilder groupName = new StringBuilder().append("H.").append(
				"groupName");
		String hql = new StringBuilder().append("select ").append("count(")
				.append(groupName).append("),").append(groupName)
				.append(" from ").append(habitTbName).append(" H ")
				.append(" group by ").append(groupName).toString();
		List<?> results = habitDao.find(hql);
		if (results.isEmpty())
			return null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		Iterator<?> itr = results.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			map.put(obj[1].toString(), Integer.valueOf(obj[0].toString()));
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Habit> findAGroup(String groupName) {
		StringBuilder columnName = new StringBuilder().append("H.").append(
				"groupName");
		String hql = new StringBuilder().append("from ").append(habitTbName)
				.append(" H ").append(" where ").append(columnName)
				.append(" =?").toString();
		List<?> results = habitDao.find(hql, groupName);
		if (results.isEmpty())
			return null;
		List<Habit> habits = (List<Habit>) results;
		Collections.sort(habits, Habit.getHotComparator());
		return habits;
	}

	@Override
	public int findParticipateNum(Integer hid) {
		return findParticipate(hid).size();
	}

	@Override
	public List<User> findParticipate(Integer hid) {
		Habit habit = habitDao.get(hid);
		Set<UserHabit> userHabits = habit.getUserHabits();
		Set<User> participaters = new HashSet<User>();
		for (UserHabit userHabit : userHabits) {
			if (userHabit.getStat() == HabitState.DELETED)
				continue;
			participaters.add(userHabit.getUser());
		}
		List<User> result = new ArrayList<User>(participaters);
		return result;
	}

	@Override
	public List<User> findParticipateAndFriends(Integer hid, Long uid) {
		List<User> users = findParticipate(hid);
		User user = userDao.get(uid);
		Set<User> friends = user.getFriends();
		List<User> friendsList = new ArrayList<User>(friends);
		users.retainAll(friendsList);
		return users;
	}

	@Override
	public List<HabitGroupName> groupNamesList() {
		Map<String,Integer> groupNames = groupNames();
		List<HabitGroupName> ret = new ArrayList<HabitGroupName>();
		if( null ==  groupNames) return null;
		for(Entry<String,Integer> entry:groupNames.entrySet())
		{
			ret.add(new HabitGroupName().setHabitnum(entry.getValue()).setName(entry.getKey()));
		}
		return ret;
	}

	@Override
	public List<Habit> findAll(boolean byHot, Integer start, Integer end) {
		List<Habit> habits = findAll(byHot);
		return Utils.subList(start, end, habits);
	}

	@Override
	public List<Habit> findAGroup(String groupName, Integer start, Integer end) {
		 List<Habit> habits = findAGroup(groupName);
		return Utils.subList(start, end, habits);
	}

}

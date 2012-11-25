package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.FlagDao;
import com.ebupt.justholdon.server.database.dao.HabitDao;
import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.Flag;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.User;

@Service("flagService")
@Transactional
public class FlagServiceImpl implements FlagService {
	public FlagServiceImpl() {
	};
	@Autowired
	private FlagDao flagDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private HabitDao habitDao;

	private String flagTbName = Flag.class.getName();

	@Override
	public List<Flag> findAll(boolean byHot) {
		List<Flag> list = findAll();
		if (byHot)
			Collections.sort(list, Flag.getHotComparator());
		return list;
	}

	public List<Flag> findAll() {
		return flagDao.findAll();
	}

	public FlagDao getFlagDao() {
		return flagDao;
	}

	public void setFlagDao(FlagDao flagDao) {
		this.flagDao = flagDao;
	}

	@Override
	@Transactional(readOnly = false)
	public Integer save(Flag newInstance) {
		return flagDao.save(newInstance);
	}

	@Override
	public Flag get(Integer id) {
		return flagDao.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Flag transientObject) {
		flagDao.update(transientObject);
	}

	@Override
	@Transactional(readOnly = false)
	public int update(Integer id, Map<String, Object> infos) {
		return flagDao.update(id, infos);
	}

	// @Override
	// @Transactional(readOnly = false)
	// public void delete(Flag persistentObject) {
	// flagDao.delete(persistentObject);
	// }

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		flagDao.delete(id);
	}

	/*
	 * @Override public Flag load(Integer id) { return flagDao.load(id); }
	 */
	@Override
	@Transactional(readOnly = false)
	public void addUser(Long uid, Integer flagid) {
		User user = userDao.get(uid);
		Flag flag = flagDao.get(flagid);
		if (null == flag || null == user)
			return;
		if (user.getFlags().contains(flag) && flag.getUsers().contains(user))
			return;
		flag.getUsers().add(user);
		user.getFlags().add(flag);
		flagDao.update(flag);
		userDao.update(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void removeUser(Long uid, Integer fid) {
		User user = userDao.get(uid);
		Flag flag = flagDao.get(fid);
		if (null == flag || null == user)
			return;
		if (!user.getFlags().contains(flag) && !flag.getUsers().contains(user))
			return;
		System.out.println("begin removes");
		flag.getUsers().remove(user);
		user.getFlags().remove(flag);
		flagDao.update(flag);
		userDao.update(user);

	}

	@Override
	@Transactional(readOnly = false)
	public void addHabit(Integer hid, Integer fid) {
		Habit habit = habitDao.get(hid);
		Flag flag = flagDao.get(fid);
		if (null == flag || null == habit)
			return;
		if (habit.getFlags().contains(flag) && flag.getHabits().contains(habit))
			return;
		flag.getHabits().add(habit);
		habit.getFlags().add(flag);
		flagDao.update(flag);
		habitDao.update(habit);
	}

	@Override
	@Transactional(readOnly = false)
	public void removeHabit(Integer hid, Integer fid) {
		Habit habit = habitDao.get(hid);
		Flag flag = flagDao.get(fid);
		if (null == flag || null == habit)
			return;
		if (!habit.getFlags().contains(flag)
				&& !flag.getHabits().contains(habit))
			return;
		flag.getHabits().remove(habit);
		habit.getFlags().remove(flag);
		flagDao.update(flag);
		habitDao.update(habit);
	}

	@Override
	public List<Flag> findAll(boolean byHot, Integer start, Integer end) {
		List<Flag> flags = findAll(byHot);
//		for (Flag flag : flags)
//			System.out.println(flag.getContent());
//		System.out.println(start + " => " + end + "[0]" + flags.get(0));
		
		return Utils.subList(start, end, flags);
	}

	@Override
	public List<Flag> findAType(String targetType, Integer start, Integer end) {
		StringBuilder columnName = new StringBuilder().append("F.").append(
				"target");
		String hql = new StringBuilder().append("from ").append(flagTbName)
				.append(" F ").append(" where ").append(columnName)
				.append(" =?").toString();
		@SuppressWarnings("unchecked")
		List<Flag> flags = (List<Flag>) flagDao.find(hql, targetType);
		if (null == flags)
			return null;
		Collections.sort(flags, Flag.getHotComparator());
		return Utils.subList(start, end, flags);

	}

	@Override
	public int findHabitCounts(List<Integer> flagIds) {
		int size = 0;
		List<Habit> habits = findHabits(flagIds);
		if (null != habits)
			size = habits.size();
		return size;
	}

	@Override
	public List<Habit> findHabits(List<Integer> flagIds) {
		Set<Habit> result = new HashSet<Habit>();
		for (Integer flagId : flagIds) {
			Flag flag = flagDao.get(flagId);
			Set<Habit> habits = flag.getHabits();
			for (Habit habit : habits)
				result.add(habit);
		}
		List<Habit> habits = new ArrayList<Habit>(result);
		Collections.sort(habits, Habit.getHotComparator());
		return habits;
	}

	@Override
	public List<Habit> findHabits(List<Integer> flagIds, Integer start,
			Integer end) {
		List<Habit> habits = findHabits(flagIds);
		return Utils.subList(start, end, habits);
	}
}

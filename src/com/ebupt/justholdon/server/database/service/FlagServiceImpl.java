package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
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

	private Logger logger = Logger.getLogger(FlagServiceImpl.class);
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

	@Override
	@Transactional(readOnly = false)
	public void delete(Flag persistentObject) {
		flagDao.delete(persistentObject);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		flagDao.delete(id);
	}

	@Override
	public Flag load(Integer id) {
		return flagDao.load(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void addUser(User user, Flag flag) {
		flag.getUsers().add(user);
		user.getFlags().add(flag);
		flagDao.update(flag);
		userDao.update(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void removeUser(User user, Flag flag) {
		flag.getUsers().remove(user);
		user.getFlags().remove(flag);
		flagDao.update(flag);
		userDao.update(user);

	}

	@Override
	@Transactional(readOnly = false)
	public void addHabit(Habit habit, Flag flag) {
		flag.getHabits().add(habit);
		habit.getFlags().add(flag);
		flagDao.update(flag);
		habitDao.update(habit);
	}

	@Override
	@Transactional(readOnly = false)
	public void removeHabit(Habit habit, Flag flag) {
		flag.getHabits().remove(habit);
		habit.getFlags().remove(flag);
		flagDao.update(flag);
		habitDao.update(habit);
	}

	@Override
	public List<Flag> findAll(boolean byHot, Integer start, Integer end) {
		List<Flag> flags = findAll(byHot);
		for (Flag flag : flags)
			System.out.println(flag.getContent());
		System.out.println(start + " => " + end + "[0]" + flags.get(0));
		if (null == flags || flags.isEmpty()) {
			logger.debug("flags is empty");
			return null;
		}
		if (start < 0 || end < 0) {
			logger.warn("startIndex:" + start + ",endIndex:" + end
					+ " can't < 0");
			return null;
		}
		if (start >= end) {
			logger.warn("startIndex:" + start + ",endIndex:" + end + " error");
			return null;
		}
		if (end > flags.size())
			end = flags.size();
		return flags.subList(start, end);
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
		if (null == flags || flags.isEmpty())
			return null;
		Collections.sort(flags, Flag.getHotComparator());

		if (start >= 0 && start < end && end <= flags.size())
			return flags.subList(start, end);
		return flags;
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
			for(Habit habit:habits)
				result.add(habit);		
		}
		List<Habit> habits = new ArrayList<Habit>(result);
		Collections.sort(habits,Habit.getHotComparator());
		return habits;
	}

}

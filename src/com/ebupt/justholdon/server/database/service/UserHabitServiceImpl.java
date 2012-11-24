package com.ebupt.justholdon.server.database.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.HabitDao;
import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.dao.UserHabitDao;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;

@Service("userHabitService")
@Transactional 
public class UserHabitServiceImpl implements UserHabitService {
	@Autowired
	private UserHabitDao userHabitDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private HabitDao habitDao;

	@Override
	public Integer save(UserHabit newInstance) {
		return userHabitDao.save(newInstance);
	}

	@Override
	public UserHabit get(Integer id) {
		return userHabitDao.get(id);
	}

	@Override
	public UserHabit load(Integer id) {
		return userHabitDao.load(id);
	}

	@Override
	public void update(UserHabit transientObject) {
		userHabitDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return userHabitDao.update(id, infos);
	}

	@Override
	public void delete(UserHabit persistentObject) {
		userHabitDao.delete(persistentObject);
	}

	@Override
	public void delete(Integer id) {
		userHabitDao.delete(id);
	}

	@Override
	public List<UserHabit> findAll() {
		return userHabitDao.findAll();
	}

	@Override
	public boolean connectUserHabit(User user, Habit habit, UserHabit userHabit) {
		if(userHabit.isDeleted()) return false;
		userHabit.setUser(user).setHabit(habit);
		userHabitDao.saveOrUpdate(userHabit);
		userDao.update(user);
		habitDao.update(habit);
		return true;
	}

	@Override
	public boolean connectUserHabit(Long uid, Integer hid, UserHabit userHabit) {
		User user = userDao.get(uid);
		Habit habit = habitDao.get(hid);
		return connectUserHabit(user,habit,userHabit);
		
	}

	@Override
	public boolean cancelUserHabit(User user, Habit habit, UserHabit userHabit) {
		if(userHabit.isDeleted()) return false;
		userHabit.setDeleted(true);
		userHabit.setDeleteTime(new Date());
		
		user.getUserHabits().remove(userHabit);
		habit.getUserHabits().remove(userHabit);
		
		userHabitDao.saveOrUpdate(userHabit);
		userDao.update(user);
		habitDao.update(habit);
		return true;
	}

	@Override
	public boolean cancelUserHabit(Long uid, Integer hid, UserHabit userHabit) {
		User user = userDao.get(uid);
		Habit habit = habitDao.get(hid);
		return cancelUserHabit(user,habit,userHabit);
	}

}

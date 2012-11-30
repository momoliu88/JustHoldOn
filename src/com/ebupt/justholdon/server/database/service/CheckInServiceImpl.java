package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.CheckInDao;
import com.ebupt.justholdon.server.database.dao.HabitDao;
import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.CheckIn;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.User;

@Service("checkInService")
@Transactional
public class CheckInServiceImpl implements CheckInService{
	@Autowired
	private CheckInDao checkInDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private HabitDao habitDao;
	
//	private Logger logger = Logger.getLogger(this.getClass());
	@Override
	public Integer save(CheckIn newInstance) {
		return checkInDao.save(newInstance);
	}

	@Override
	public CheckIn get(Integer id) {
		return checkInDao.get(id);
	}

	@Override
	public void update(CheckIn transientObject) {
		checkInDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		checkInDao.update(id,infos);
		return 0;
	}

	@Override
	public void delete(Integer id) {
		checkInDao.delete(id);
	}

	@Override
	public List<CheckIn> findAll() {
		return checkInDao.findAll();
	}

	@Override
	public Integer checkIn(Long uid, Integer hid) {
		User user = userDao.get(uid);
		Habit habit = habitDao.get(hid);
		if(user == null || habit == null) 
			return -1;
		CheckIn checkIn = new CheckIn().setUser(user).setHabit(habit);
		user.getCheckIns().add(checkIn);
		habit.getCheckIns().add(checkIn);
 		Integer id = checkInDao.save(checkIn);
 		userDao.update(user);
 		habitDao.update(habit);
 		return id;
	}

	@Override
	public Integer checkIn(CheckIn checkIn) {
		User user = checkIn.getUser();
		Habit habit = checkIn.getHabit();
		if(user == null || habit == null) return -1;
		user.getCheckIns().add(checkIn);
		habit.getCheckIns().add(checkIn);
 		Integer id = checkInDao.save(checkIn);
 		userDao.update(user);
 		habitDao.update(habit);
 		return id;
	}

	@Override
	public List<CheckIn> getCheckIns(Long uid, Integer hid) {
		User user = userDao.get(uid);
		Set<CheckIn> checkIns = user.getCheckIns();
		List<CheckIn> results = new ArrayList<CheckIn>();
		for(CheckIn checkIn: checkIns)
			if(checkIn.getHabit().getId() == hid)
				results.add(checkIn);
		return results;
	}

	@Override
	public int getCheckInNum(Long uid, Integer hid) {
		List<CheckIn> checkIns = getCheckIns(uid,hid);
		return checkIns.size();
	}

	@Override
	public List<CheckIn> getCheckIns(Long uid, Integer hid, Integer start,
			Integer end) {
		return Utils.subList(start, end, getCheckIns(uid,hid));
	}

	@Override
	public int getCheckInNum(Long uid, Integer hid, Date start, Date end) {
		List<CheckIn> checkIns = getCheckIns(uid,hid);
		List<CheckIn> inRange = new ArrayList<CheckIn>();
		for(CheckIn checkIn: checkIns)
		{
			if(checkIn.getCheckInTime().getTime() >= start.getTime() 
					&& checkIn.getCheckInTime().getTime() <= end.getTime())
				inRange.add(checkIn);
		}
		return inRange.size();
	}

}

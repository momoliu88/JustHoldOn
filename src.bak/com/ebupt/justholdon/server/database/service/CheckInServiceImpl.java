package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Collections;
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
	@Autowired
	private EventService eventService;
	
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
		CheckIn checkIn = checkInDao.get(id);
		checkIn.getUser().getCheckIns().remove(checkIn);
		checkIn.getHabit().getCheckIns().remove(checkIn);
		checkIn.setUser(null);
		checkIn.setHabit(null);
		checkInDao.delete(checkIn);
	}

	@Override
	public List<CheckIn> findAll() {
		return checkInDao.findAll();
	}

	@Override
	public Integer checkIn(Long uid, Integer hid) {
		User user = userDao.get(uid);
		Habit habit = habitDao.get(hid);
//		if(user == null || habit == null) 
//			return -1;
		CheckIn checkIn = new CheckIn().setUser(user).setHabit(habit);
//		user.getCheckIns().add(checkIn);
//		habit.getCheckIns().add(checkIn);
 		Integer id = checkInDao.save(checkIn);
// 		userDao.update(user);
// 		habitDao.update(habit);
 		return id;
	}

	@Override
	public Integer checkIn(Long uid,Integer hid,CheckIn checkIn){
		User user = userDao.get(uid);
		Habit habit = habitDao.get(hid);
		if(null != checkIn)
			checkIn.setUser(user).setHabit(habit);
 		return checkInDao.save(checkIn);
	}

	@Override
	public List<CheckIn> getCheckIns(Long uid, Integer hid) {
		User user = userDao.get(uid);
		Set<CheckIn> checkIns = user.getCheckIns();
		List<CheckIn> results = new ArrayList<CheckIn>();
		for(CheckIn checkIn: checkIns)
			if(checkIn.getHabit().getId() == hid)
				results.add(checkIn);
		Collections.sort(results,CheckIn.getDateComparator());
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
	public int getCheckInInTimeRangeNum(Long uid, Integer hid, Date start, Date end) {
		return getCheckInInTimeRange(uid,hid,start,end).size();
	}

	@Override
	public List<CheckIn> getCheckInInTimeRange(Long uid, Integer hid, Date start, Date end) {
		List<CheckIn> checkIns = getCheckIns(uid,hid);
//		System.out.println("checkins "+checkIns.size());
		List<CheckIn> inRange = new ArrayList<CheckIn>();
		//Collections.sort(checkIns,CheckIn.getDateComparator());

		for(CheckIn checkIn: checkIns)
		{
//			System.out.println("#checkin "+checkIn.getCheckInTime());
			if(checkIn.getCheckInTime().getTime() > end.getTime())
			{
				
//				System.out.println("continue");
				continue;
			}
			if(checkIn.getCheckInTime().getTime() < start.getTime())
			{
				//				System.out.println("break");
				break;
			}
//			System.out.println("add");
			inRange.add(checkIn);
		}
		return inRange;
	}

	@Override
	public List<CheckIn> getCheckInInTimeRange(Long uid, Integer hid, Date start,
			Date end, Integer startpos, Integer endpos) {
		return Utils.subList(startpos, endpos, getCheckInInTimeRange(uid,hid,start,end));
	}

	@Override
	public void deleteCheckIn(Integer cid) {
		CheckIn checkIn  = checkInDao.get(cid);
		checkIn.getUser().getCheckIns().remove(checkIn);
		checkIn.getHabit().getCheckIns().remove(checkIn);
		checkIn.setUser(null);
		checkIn.setHabit(null);
		checkInDao.delete(checkIn);
	}

	@Override
	public Integer checkInAndCreateEvent(Long uid, Integer hid, String content) {
		Integer id = checkIn(uid,hid);
		eventService.createHabitEvent(uid, hid, id,EventType.SOMEBODY_CHECKIN, content);
		return id;
	}
	@Override
	public Integer checkInAndCreateEvent(Long uid,Integer hid,CheckIn checkIn, String content) {
		Integer id = checkIn(uid,hid,checkIn);
		eventService.createHabitEvent(uid,hid, id,EventType.SOMEBODY_CHECKIN, content);
		return id;
	}
}

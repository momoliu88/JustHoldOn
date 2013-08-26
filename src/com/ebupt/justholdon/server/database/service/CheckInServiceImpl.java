package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.CheckInDao;
import com.ebupt.justholdon.server.database.entity.Approve;
import com.ebupt.justholdon.server.database.entity.CheckIn;
import com.ebupt.justholdon.server.database.entity.Comment;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.User;

@Service("checkInService")
@Transactional
public class CheckInServiceImpl implements CheckInService {
	@Autowired
	private CheckInDao checkInDao;
	@Autowired
	private UserService userService;
	@Autowired
	private HabitService habitService;
	@Autowired
	private EventService eventService;
	@Autowired
	private UserHabitService userHabitService;

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
		return checkInDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
		deleteCheckIn(id);
	}

	@Override
	public List<CheckIn> findAll() {
		return checkInDao.findAll();
	}

	@Override
	public Integer checkIn(Long uid, Integer hid) {
		User user = userService.get(uid);
		Habit habit = habitService.get(hid);
		CheckIn checkIn = new CheckIn().setUser(user).setHabit(habit);
		return checkInDao.save(checkIn);
	}

	@Override
	public Integer checkIn(Long uid, Integer hid, CheckIn checkIn) {
		User user = userService.get(uid);
		Habit habit = habitService.get(hid);
		if (null != checkIn)
			checkIn.setUser(user).setHabit(habit);
		return checkInDao.save(checkIn);
	}
	private Criterion[] Restriction(Long uid) {
		Criterion[] criteriation = { Restrictions.eq("user", userService.get(uid)),
				Restrictions.eq("isDeleted", false) };
		return criteriation;
	}
	private Criterion[] Restriction(Date start,Date end) {
		Criterion[] criteriation = { 
				Restrictions.eq("isDeleted", false),
				Restrictions.between("checkInTime", start,end)
				};
		return criteriation;
	}
	private Criterion[] Restriction(Long uid, Integer hid) {
		Criterion[] criteriation = { Restrictions.eq("user", userService.get(uid)),
				Restrictions.eq("habit", habitService.get(hid)),
				Restrictions.eq("isDeleted", false) };
		return criteriation;
	}

//	private Criterion[] Restriction(Long uid, Integer hid, Integer startId,
//			boolean after) {
//		List<Criterion> criteriation = new ArrayList<Criterion>(
//				Arrays.asList(Restriction(uid, hid)));
//		if (after)
//			criteriation.add(Restrictions.lt("id", startId));
//		else
//			criteriation.add(Restrictions.gt("id", startId));
//		Criterion[] ret = new Criterion[criteriation.size()];
//		return criteriation.toArray(ret);
//	}

	private Criterion[] Restriction(Long uid, Integer hid, Date start, Date end) {
		List<Criterion> criteriation = new ArrayList<Criterion>(
				Arrays.asList(Restriction(uid, hid)));
		criteriation.add(Restrictions.ge("checkInTime", start));
		criteriation.add(Restrictions.le("checkInTime", end));
		Criterion[] ret = new Criterion[criteriation.size()];
		return criteriation.toArray(ret);
	}

//	private Criterion[] Restriction(Long uid, Integer hid, Date start,
//			Date end, Integer startId, boolean after) {
//		List<Criterion> criteriation = new ArrayList<Criterion>(
//				Arrays.asList(Restriction(uid, hid, start, end)));
//		if (after)
//			criteriation.add(Restrictions.lt("id", startId));
//		else
//			criteriation.add(Restrictions.gt("id", startId));
//		Criterion[] ret = new Criterion[criteriation.size()];
//		return criteriation.toArray(ret);
//	}

	@Override
	public List<CheckIn> getCheckIns(Long uid, Integer hid) {
		return checkInDao.findByCriteria(Restriction(uid, hid));
	}

	@Override
	public int getCheckInNum(Long uid, Integer hid) {
		return checkInDao.countByCriteria(Restriction(uid, hid));
	}
	private List<CheckIn> warpCriterions(Integer length,Integer startId,boolean after,Criterion...crits){
		if(Utils.checkIdIsZero(startId)) 
			return checkInDao.findByCriteria(length,crits);
		CheckIn ck = get(startId);
		return checkInDao.findByCriteria(length,
				Utils.warpIdRangeLimit(ck.getCheckInTime(),"checkInTime",after, crits));
	}
	@Override
	public List<CheckIn> getCheckIns(Long uid, Integer hid, Integer startId,
			Integer length, boolean after) {
		return warpCriterions(length,startId,after,Restriction(uid,hid));
	}

	@Override
	public int getCheckInInTimeRangeNum(Long uid, Integer hid, Date start,
			Date end) {
		return getCheckInInTimeRange(uid, hid, start, end).size();
	}

	@Override
	public List<CheckIn> getCheckInInTimeRange(Long uid, Integer hid,
			Date start, Date end) {
		return checkInDao.findByCriteria(Restriction(uid, hid, start, end));
	}

	@Override
	public List<CheckIn> getCheckInInTimeRange(Long uid, Integer hid,
			Date start, Date end, Integer startId, Integer length, boolean after) {
		return warpCriterions(length,startId,after,Restriction(uid, hid, start, end));
//		return checkInDao.findByCriteria(length,
//				Utils.warpIdRangeLimit(after, startId, Restriction(uid, hid, start, end)));
	}

	@Override
	public void deleteCheckIn(Integer cid) {
		CheckIn checkIn = checkInDao.get(cid);
		checkIn.getUser().getCheckIns().remove(checkIn);
		checkIn.getHabit().getCheckIns().remove(checkIn);

		List<Comment> comments = checkIn.getComments();
		List<Approve> approves = checkIn.getApproves();
		for (Comment comment : comments) {
			comment.getSponsor().getSponsorComments().remove(comment);
			comment.getReceiver().getReceiverComments().remove(comment);
			comment.setSponsor(null);
			comment.setReceiver(null);
		}
		for (Approve approve : approves) {
			approve.getUser().getApproves().remove(approve);
			approve.setUser(null);
		}
		checkIn.setUser(null);
		checkIn.setHabit(null);
	//	checkInDao.update(checkIn);
		checkInDao.delete(checkIn);
	}

	@Override
	public Integer checkInAndCreateEvent(Long uid, Integer hid, String content) {
		Integer id = checkIn(uid, hid);
		eventService.createHabitEvent(uid, hid, id, EventType.SOMEBODY_CHECKIN,
				content);
		return id;
	}

	@Override
	public Integer checkInAndCreateEvent(Long uid, Integer hid,
			CheckIn checkIn, String content) {
		Integer id = checkIn(uid, hid, checkIn);
		eventService.createHabitEvent(uid, hid, id, EventType.SOMEBODY_CHECKIN,
				content);
		return id;
	}
	
	private List<CheckIn> getPrivilegesCheckIns(Long uid, Long beWatched, Integer hid,
			Integer startId, Integer length, boolean after,Criterion... crits){
		List<CheckIn> cks;
		List<CheckIn> result = new ArrayList<CheckIn>();
		int nums = 0;
		int id = startId.intValue();
		Integer fetchSize = Integer.valueOf(length);
		while (true) {
			
			cks = warpCriterions(Utils.getMulti(fetchSize),id,after,crits);
//			cks = checkInDao.findByCriteria(Utils.getMulti(fetchSize),
//					Utils.warpIdRangeLimit(after,id,crits));
			if (null == cks || cks.size() <= 0)
				return result;
			for (CheckIn ck : cks) {
				if (userHabitService.hasPrivilegeToSee(uid, beWatched, ck
						.getHabit().getId())) {
					result.add(ck);
					nums++;
					if (length.intValue() <= nums)
						return result;
				}
			}
			id = Utils.getNewestId(after, cks);
		}
	}
	@Override
	public List<CheckIn> getCheckIns(Long uid, Long beWatched, Integer hid,
			Integer startId, Integer length, boolean after) {
		 return getPrivilegesCheckIns(uid,beWatched,hid,startId,length,after,Restriction(beWatched,hid));
	}

	@Override
	public List<CheckIn> getCheckIns(Long uid, Long beWatched, Integer hid) {
		List<CheckIn> cks;
		List<CheckIn> result = new ArrayList<CheckIn>();
		cks = checkInDao.findByCriteria(Restriction(beWatched, hid));
		for (CheckIn ck : cks) {
			if (userHabitService.hasPrivilegeToSee(uid, beWatched, ck.getHabit()
					.getId())) {
				result.add(ck);
			}
		}
		return result;
	}

	@Override
	public List<Approve> getApproves(Integer cid) {
		return new ArrayList<Approve>(get(cid).getApproves());
		// Collections.sort(approves,
		// GenericComparator.getInstance().getDateComparator());
	}

	@Override
	public Integer countApproves(Integer cid) {
		return get(cid).getApproves().size();
	}

	@Override
	public List<Comment> getComments(Integer cid) {
		return new ArrayList<Comment>(get(cid).getComments());
	}

	@Override
	public Integer countComments(Integer cid) {
		return get(cid).getComments().size();
	}

	@Override
	public void saveOrUpdate(CheckIn transientObject) {
		checkInDao.saveOrUpdate(transientObject);		
	}

	@Override
	public int getCheckInNum(Long uid) {
		return checkInDao.countByCriteria(Restriction(uid));
	}

	@Override
	public List<CheckIn> getHotCheckins(Long uid, Date start, Date end,
			int length) {
		List<CheckIn> cks = checkInDao.findByCriteria(Restriction(start,end));
		List<CheckIn> results = new ArrayList<CheckIn>();
		for(CheckIn ck:cks)
			if(userHabitService.hasPrivilegeToSee(uid, ck.getUser().getId(), ck.getHabit().getId()))
				results.add(ck);
		if(results.isEmpty()) return results;
		Collections.sort(results,CheckIn.hotCheckInComp);
		if(length>results.size())
			length = results.size();
		return results.subList(0, length);
	}
}

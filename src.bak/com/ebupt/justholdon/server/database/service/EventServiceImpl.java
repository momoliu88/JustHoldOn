package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.EventDao;
import com.ebupt.justholdon.server.database.dao.HabitDao;
import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.Event;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.MessageFlag;
import com.ebupt.justholdon.server.database.entity.User;

@Service("eventService")
@Transactional
public class EventServiceImpl implements EventService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private HabitDao habitDao;
	@Autowired
	private EventDao eventDao;
	@Autowired
	private UserHabitService userHabitService ;
	@Autowired
	private UserService userService;

	
	@Override
	public Integer save(Event newInstance) {
		return eventDao.save(newInstance);
	}

	@Override
	public Event get(Integer id) {
		return eventDao.get(id);
	}

	@Override
	public void update(Event transientObject) {
		eventDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return eventDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
		Event event = eventDao.get(id);
		User sponsor = event.getSponsor();
		User receiver = event.getReceiver();
		Habit habit = event.getHabit();
		if (null != sponsor)
			sponsor.getSponsorEvent().remove(event);
		if (null != receiver)
			receiver.getReceiverEvent().remove(event);
		if (null != habit)
			habit.getEvents().remove(event);
		event.setSponsor(null);
		event.setReceiver(null);
		event.setHabit(null);
		eventDao.delete(event);
	}

	@Override
	public List<Event> findAll() {
		return eventDao.findAll();
	}

	@Override
	public Integer createGenericEvent(Long sponsorId, Long receiverId,
			Integer habitId, EventType type, Integer relationId,
			String content, MessageFlag flag) {
		if (null == type || null == flag)
			throw new java.lang.NullPointerException(
					"event type or message flag can't be NULL!");
		User sponsor = null;
		User receiver = null;
		if (null != sponsorId)
			sponsor = userDao.get(sponsorId);
		if (null != receiverId)
			receiver = userDao.get(receiverId);
		Habit habit = null;
		if (null != habitId)
			habit = habitDao.get(habitId);
		Event event = new Event().setContent(content).setFlag(flag)
				.setSponsor(sponsor).setReceiver(receiver).setType(type)
				.setRelationId(relationId);
		if (habit != null)
			event.setHabit(habit);
		return eventDao.save(event);

	}

	@Override
	public Integer createFriendInfo(Long sponsorId, Long receiverId,
			EventType type, String content) {
		if (null == type)
			throw new java.lang.NullPointerException(
					"event type or message flag can't be NULL!");
		User sponsor = null;
		User receiver = null;
		if (null != sponsorId)
			sponsor = userDao.get(sponsorId);
		if (null != receiverId)
			receiver = userDao.get(receiverId);
		Event event = new Event().setContent(content)
				.setFlag(MessageFlag.UNREADED).setSponsor(sponsor)
				.setReceiver(receiver).setType(type);
		return eventDao.save(event);
	}

	@Override
	public Integer createHabitInfo(Long sponsorId, Long receiverId,
			Integer habitId, EventType type, String content, Integer relatioId) {
		if (null == type)
			throw new java.lang.NullPointerException(
					"event type or message flag can't be NULL!");
		User sponsor = null;
		User receiver = null;
		if (null != sponsorId)
			sponsor = userDao.get(sponsorId);
		if (null != receiverId)
			receiver = userDao.get(receiverId);
		Habit habit = habitDao.get(habitId);

		Event event = new Event().setContent(content)
				.setFlag(MessageFlag.UNREADED).setSponsor(sponsor)
				.setReceiver(receiver).setType(type).setHabit(habit);
		return eventDao.save(event);
	}

	@Override
	public Integer createHabitEvent(Long sponsorId, Integer habitId,
			EventType type, String content) {
		return createHabitEvent(sponsorId, habitId, null, type, content);
	}

	@Override
	public Integer createHabitEvent(Long sponsortId, Integer habitId,
			Integer relationId, EventType type, String content) {
		if (null == type)
			throw new java.lang.NullPointerException(
					"event type or message flag can't be NULL!");
		User user = userDao.get(sponsortId);
		Habit habit = habitDao.get(habitId);

		Event event = new Event().setContent(content)
				.setFlag(MessageFlag.JUST_EVENT).setSponsor(user).setType(type)
				.setHabit(habit).setRelationId(relationId);

		return eventDao.save(event);
	}

	@Override
	public void readAInformation(Integer infoId) {
		Event event = eventDao.get(infoId);
		event.setFlag(MessageFlag.READED);
		eventDao.update(event);
	}

	@Override
	public List<Event> getUnreadInformation(Long uid) {
		User user = userDao.get(uid);
		Set<Event> events = user.getReceiverEvent();
		List<Event> results = new ArrayList<Event>();
		for (Event event : events) {
			if (event.getFlag().equals(MessageFlag.UNREADED))
				results.add(event);
		}
		Collections.sort(results, Event.getDateComparator());
		return results;
	}

	private List<Event> getRelevantEventOri(Long uid) {
		User user = userDao.get(uid);
		Set<Event> sponsortEvents = user.getSponsorEvent();
		Set<Event> receiverEvents = user.getReceiverEvent();
		List<Event> ret = new ArrayList<Event>();

		for (Event event : sponsortEvents)
			if (event.getFlag().equals(MessageFlag.JUST_EVENT))
				ret.add(event);
		for (Event event : receiverEvents)
			if (event.getFlag().equals(MessageFlag.JUST_EVENT))
				ret.add(event);
		return ret;
	}

	@Override
	public List<Event> getRelevantEvent(Long uid) {
		List<Event> ret = getRelevantEventOri(uid);
		Collections.sort(ret, Event.getDateComparator());
		return ret;
	}

	@Override
	public List<Event> getRelevantEvent(Long uid, Integer start, Integer end) {
		return Utils.subList(start, end, getRelevantEvent(uid));
	}

	@Override
	public void deleteEvent(Integer eventid) {
		delete(eventid);
	}

	private List<Event> cutEventList(List<Event> events, Integer startId,
			Integer length, boolean after) {
		
		Collections.sort(events, Event.getIdComparator());
		Integer startpos = null;
		Integer endpos = null;
		if (events.isEmpty())
			return new ArrayList<Event>();

		if (null == startId)
			return Utils.subList(0, length, events);
		Integer indx = 0;
		boolean finded = false;
		for (Event event : events) {
			if (event.getId() == startId) {
				finded = true;
				break;
			}
			indx++;
		}
		if (!finded)
			 return new ArrayList<Event>();

		if (after) {
			startpos = indx + 1;
			endpos = indx + length + 1;
		} else {
			startpos = indx - length;
			if (startpos < 0)
				startpos = 0;
			endpos = indx;
		}
		return Utils.subList(startpos, endpos, events);
	}

	@Override
	public List<Event> getRelevantEventFromId(Long uid, Integer startId,
			Integer length, boolean after) {
		List<Event> ori = getRelevantEventOri(uid);
	//	Collections.sort(ori, Event.getIdComparator());
		return cutEventList(ori, startId, length, after);

	}
	private List<Event> getPrivilegeEvents(List<Event> events,Long uid)
	{
		List<Event> ret = new ArrayList<Event>();
		for(Event event:events)
		{
			User sponsor = event.getSponsor();
			Habit habit = event.getHabit();
			if(null == sponsor || habit == null)
				ret.add(event);
			else if(userHabitService.hasPrivilegeToSee(uid, sponsor.getId(), habit.getId()))
				ret.add(event);
		}
		return ret;
	}
	private List<Event> getRelevantEvent(Long uid, Long beWatched)
	{
		List<Event> ori = getRelevantEventOri(beWatched);
		return getPrivilegeEvents(ori,uid);
	}
	@Override
	public List<Event> getRelevantEventFromId(Long uid, Long beWatched,
			Integer startId, Integer length, boolean after) {
		List<Event>ret = getRelevantEvent(uid,beWatched);
		System.out.println("in relevant events "+ret.size());
		return cutEventList(ret, startId, length, after);
	}

	@Override
	public List<Event> getAllFriendsRelevantEventFromId(Long uid,
			Integer startId, Integer length, boolean after) {
		List<User> friends = userService.findFriends(uid);
		List<Event> events = new ArrayList<Event>();
		
		for(User user:friends)
			events.addAll(getRelevantEvent(uid,user.getId()));
		return cutEventList(events,startId,length,after);
	}

	@Override
	public List<Event> getAHabitEvents(Long uid,Integer hid, Integer startId,
			Integer length, boolean after) {
		String tbName = Event.class.getName();
		Habit habit = habitDao.get(hid);
		
		String hql = new StringBuilder().append("from ").append(tbName).append(" as event").append(" where event.habit = ?").toString();
		@SuppressWarnings("unchecked")
		List<Event> events = (List<Event>) eventDao.find(hql, habit);
		System.out.println("size "+events.size());
		List<Event> ret = getPrivilegeEvents(events,uid);
		System.out.println("get privilege size "+ret.size());
		return cutEventList(ret,startId,length,after);
	}

	@Override
	public List<Event> getAUserHabitEvent(Long uid, Long beWatched,
			Integer hid, Integer startId, Integer length, boolean after) {
		String tbName = Event.class.getName();
		Habit habit = habitDao.get(hid);
		String hql = new StringBuilder().append("from ").append(tbName).append(" as event ").append(" where event.habit = ? and event.sponsor = ?").toString();
		@SuppressWarnings("unchecked")
		List<Event> events = (List<Event>) eventDao.find(hql, habit,userDao.get(beWatched));
		List<Event> ret = getPrivilegeEvents(events,uid);
		return cutEventList(ret,startId,length,after);
	}


}
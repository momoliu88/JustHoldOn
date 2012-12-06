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
import com.ebupt.justholdon.server.database.entity.EventType;
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
		if(null != sponsor)
			sponsor.getSponsorEvent().remove(event);
		if(null != receiver)
			receiver.getReceiverEvent().remove(event);
		if(null != habit)
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
		if(null != sponsorId)
			sponsor = userDao.get(sponsorId);
		if(null != receiverId)
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
		if(null != sponsorId)
			sponsor = userDao.get(sponsorId);
		if(null != receiverId)
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
		if(null != sponsorId)
			sponsor = userDao.get(sponsorId);
		if(null != receiverId)
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
		if (null == type)
			throw new java.lang.NullPointerException(
					"event type or message flag can't be NULL!");
		User user = userDao.get(sponsorId);
		Habit habit = habitDao.get(habitId);

		Event event = new Event().setContent(content)
				.setFlag(MessageFlag.JUST_EVENT).setSponsor(user).setType(type)
				.setHabit(habit);

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
		for(Event event : events)
		{
			if(event.getFlag().equals(MessageFlag.UNREADED))
				results.add(event);
		}
		Collections.sort(results,Event.getDateComparator());
		return results;
	}

	@Override
	public List<Event> getRelevantEvent(Long uid) {
		User user = userDao.get(uid);
		Set<Event> sponsortEvents = user.getSponsorEvent();
		Set<Event> receiverEvents = user.getReceiverEvent();
		List<Event> ret = new ArrayList<Event>();

		for(Event event:sponsortEvents)
			if(event.getFlag().equals(MessageFlag.JUST_EVENT))
				ret.add(event);
		for(Event event:receiverEvents)
			if(event.getFlag().equals(MessageFlag.JUST_EVENT))
				ret.add(event);
		Collections.sort(ret,Event.getDateComparator());
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

}

package com.ebupt.justholdon.server.database.service;

import java.util.List;
import java.util.Map;

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
	private EventDao eventDao ;
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
		eventDao.delete(id);
	}

	@Override
	public List<Event> findAll() {
		return eventDao.findAll();
	}
	@Override
	public Integer createGenericEvent(Long sponsorId, Long receiverId,
			Integer habitId, EventType type, Integer relationId,
			String content, MessageFlag flag) {
		if(null == type || null == flag)
			throw new java.lang.NullPointerException("event type or message flag can't be NULL!");
		User sponsor = userDao.get(sponsorId);
		User receiver = userDao.get(receiverId);
		Habit habit = null;
		if(null != habitId)
			 habit = habitDao.get(habitId);
		Event event = new Event()
							.setContent(content)
							.setFlag(flag)
							.setSponsor(sponsor)
							.setReceiver(receiver)
							.setType(type)
							.setRelationId(relationId);
		if(habit != null)
			event.setHabit(habit);
		return eventDao.save(event);
		
	}
	@Override
	public Integer createFriendInfo(Long sponsorId, Long receiverId,
			EventType type, String content) {
		if(null == type)
			throw new java.lang.NullPointerException("event type or message flag can't be NULL!");
		User sponsor = userDao.get(sponsorId);
		User receiver = userDao.get(receiverId);
		Event event = new Event()
						.setContent(content)
						.setFlag(MessageFlag.UNREADED)
						.setSponsor(sponsor)
						.setReceiver(receiver)
						.setType(type);
		return eventDao.save(event);
	}
	@Override
	public Integer createHabitInfo(Long sponsorId, Long receiverId,
			Integer habitId, EventType type, String content, Integer relatioId) {
		if(null == type)
			throw new java.lang.NullPointerException("event type or message flag can't be NULL!");
		User sponsor = userDao.get(sponsorId);
		User receiver = userDao.get(receiverId);
		Habit habit = habitDao.get(habitId);

		Event event = new Event()
						.setContent(content)
						.setFlag(MessageFlag.UNREADED)
						.setSponsor(sponsor)
						.setReceiver(receiver)
						.setType(type)
						.setHabit(habit);
		return eventDao.save(event);
	}
	@Override
	public Integer createHabitEvent(Long sponsortId, Integer habitId,
			EventType type, String content) {
		// TODO Auto-generated method stub
		return null;
	}

}

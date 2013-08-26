package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collections;
import java.util.List;
import java.util.Map;
//import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.EventDao;
//import com.ebupt.justholdon.server.database.dao.HabitDao;
//import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.Event;
//import com.ebupt.justholdon.server.database.entity.GenericComparator;
import com.ebupt.justholdon.server.database.entity.Habit;
//import com.ebupt.justholdon.server.database.entity.TbName;
//import com.ebupt.justholdon.server.database.entity.CheckIn;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;

@Service("eventService")
@Transactional
public class EventServiceImpl implements EventService {
	@Autowired
	private UserService userService;
	@Autowired
	private HabitService habitService;
	@Autowired
	private RelationShipService relationShipService;
	@Autowired
	private EventDao eventDao;
	@Autowired
	private UserHabitService userHabitService;

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
		System.out.println("[DELETE EVENT]:do nothing.");
		/*
		 * Event event = eventDao.get(id); User sponsor = event.getSponsor();
		 * User receiver = event.getReceiver(); Habit habit = event.getHabit();
		 * if (null != sponsor) sponsor.getSponsorEvent().remove(event); if
		 * (null != receiver) receiver.getReceiverEvent().remove(event); if
		 * (null != habit) habit.getEvents().remove(event);
		 * event.setSponsor(null); event.setReceiver(null);
		 * event.setHabit(null); eventDao.delete(event);
		 */
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
			sponsor = userService.get(sponsorId);
		if (null != receiverId)
			receiver = userService.get(receiverId);
		Habit habit = null;
		if (null != habitId)
			habit = habitService.get(habitId);
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
			sponsor = userService.get(sponsorId);
		if (null != receiverId)
			receiver = userService.get(receiverId);
		Event event = new Event().setContent(content)
				.setFlag(MessageFlag.UNREADED).setSponsor(sponsor)
				.setReceiver(receiver).setType(type);
		return eventDao.save(event);
	}

	@Override
	public Integer createHabitInfo(Long sponsorId, Long receiverId,
			Integer habitId, EventType type, String content, Integer relationId) {
		if (null == type)
			throw new java.lang.NullPointerException(
					"event type or message flag can't be NULL!");
		User sponsor = null;
		User receiver = null;
		if (null != sponsorId)
			sponsor = userService.get(sponsorId);
		if (null != receiverId)
			receiver = userService.get(receiverId);
		Habit habit = habitService.get(habitId);

		Event event = new Event().setContent(content)
				.setFlag(MessageFlag.UNREADED).setSponsor(sponsor)
				.setReceiver(receiver).setType(type).setHabit(habit)
				.setRelationId(relationId);
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
		User user = userService.get(sponsortId);
		Habit habit = habitService.get(habitId);

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
	private List<Event> warpCriterions(Integer length,Integer startId,boolean after,Criterion...crits){
		if(Utils.checkIdIsZero(startId)) 
			return eventDao.findByCriteria(length,crits);
		Event event = get(startId);
		return eventDao.findByCriteria(length,
				Utils.warpIdRangeLimit(event.getCreateTime(),"createTime",after, crits));
	}
	@Override
	public List<Event> getUnreadInformation(Long uid, Integer startId,
			Integer length, boolean after) {
		return warpCriterions(length,startId,after,unreadInfoCriterions(uid));
	//	return eventDao.findByCriteria(length,Utils.warpIdRangeLimit(after, startId, unreadInfoCriterions(uid)));
	}
	private Criterion[] unreadInfoCriterions(Long uid){
		User user = userService.get(uid);
		Criterion[] criterions = { 
				Restrictions.eq("receiver", user),
				Restrictions.eq("flag", MessageFlag.UNREADED) };
		return criterions;
	}
	private Criterion[] friendsHabitRelevantEvents(Long uid,Integer hid,List<User> friends){
		List<Criterion> criterions = new ArrayList<Criterion>(Arrays.asList(friendsRelevantEvents(uid,friends)));
		criterions.add(Restrictions.eq("habit", habitService.get(hid)));
		Criterion[] ret = new Criterion[criterions.size()];
		return criterions.toArray(ret);
	}
	private Criterion[] friendsAndMyRelevantEvents(Long uid){
		List<User> users = relationShipService.findFriends(uid);
		users.add(userService.get(uid));
		Criterion criterions [] = {
				Restrictions.in("sponsor",users),
				Restrictions.eq("isDeleted", false),
				Restrictions.eq("flag",MessageFlag.JUST_EVENT)
			};
			return criterions;
	}
	private Criterion[] friendsRelevantEvents(Long uid,List<User> friends){
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("isDeleted", false));
		criterions.add(Restrictions.eq("flag",MessageFlag.JUST_EVENT));
		criterions.add(Restrictions.in("sponsor",friends));
		Criterion [] ret = new Criterion[criterions.size()]; 
		return criterions.toArray(ret);
	}
	private Criterion[] baseEventCriterion(){
		Criterion[] criterions = { 
				Restrictions.eq("flag", MessageFlag.JUST_EVENT),
				Restrictions.eq("isDeleted", false)
		};
		return criterions;		
	}
	private Criterion[] relevantEventCriterion(Long uid){
		User user = userService.get(uid);
		Criterion[] criterions = { 
				Restrictions.or(Restrictions.eq("receiver", user),Restrictions.eq("sponsor", user)),
				Restrictions.eq("flag", MessageFlag.JUST_EVENT),
				Restrictions.eq("isDeleted", false)
		};
		return criterions;
	}
	private Criterion[] allInfosCriterion(Long uid){
		Criterion criterions [] = {
				Restrictions.eq("receiver", userService.get(uid)),
				Restrictions.ne("flag",MessageFlag.JUST_EVENT)
			};
			return criterions;	
	}
	@Override
	public List<Event> getRelevantEvent(Long uid) {
		return eventDao.findByCriteria(relevantEventCriterion(uid));
	}
	@Override
	public void deleteEvent(Integer eventid) {
		delete(eventid);
	}

	@Override
	public List<Event> getRelevantEventFromId(Long uid, Integer startId,
			Integer length, boolean after) {
		return warpCriterions(length,startId,after,relevantEventCriterion(uid));
//		return eventDao.findByCriteria(length,Utils.warpIdRangeLimit(after, startId,relevantEventCriterion(uid)));
	}
	private boolean checkPrivilege(Long uid,Event event){
		User sponsor = event.getSponsor();
		Habit habit = event.getHabit();
		if (null == sponsor || habit == null)
			return true;
		else if (event.getIsDeleted() == false
				&& userHabitService.hasPrivilegeToSee(uid, sponsor.getId(),
						habit.getId()))
			return true;
		return false;
	}

	private List<Event> getPrivilegeEvents(boolean after,Integer startId,Integer length,Long uid,Criterion... criterions){
		int id = startId.intValue();
		List<Event> ret = new ArrayList<Event>();
		List<Event> events ;
		int nums = 0 ;
		Integer fetchSize = Integer.valueOf(length);
		while(true){
			events = warpCriterions(Utils.getMulti(fetchSize),id,after,criterions);
			//	events = eventDao.findByCriteria(Utils.getMulti(fetchSize),  Utils.warpIdRangeLimit(after, id,criterions));
			if(null == events|| events.isEmpty()) return ret;
			for(Event event:events)
			{
				if(checkPrivilege(uid,event))
				{	
					ret.add(event);
					nums++;
					if(nums >= length.intValue()) return ret;
				}
			}
			id = Utils.getNewestId(after,events);
		}
		
	}
	@Override
	public List<Event> getRelevantEventFromId(Long uid, Long beWatched,
			Integer startId, Integer length, boolean after) {
		return getPrivilegeEvents(after,startId,length,uid,relevantEventCriterion(beWatched));
	}
	
	@Override
	public List<Event> getAllFriendsRelevantEventFromId(Long uid,
			Integer startId, Integer length, boolean after) {
		List<User> friends = relationShipService.findFriends(uid);
		if(friends == null || friends.isEmpty())
			return new ArrayList<Event>();
		return getPrivilegeEvents(after,startId,length,uid,friendsRelevantEvents(uid,friends));
	}
	private  Criterion[] habitEventCriterion(Integer hid){
		Criterion [] criterions = {
				Restrictions.eq("habit", habitService.get(hid)),
				Restrictions.eq("isDeleted", false),
				Restrictions.eq("flag", MessageFlag.JUST_EVENT)
		};
		return criterions;
	}
	private  Criterion[] userHabitEventCriterion(Integer hid,Long uid){
		Criterion [] criterions = {
				Restrictions.eq("habit", habitService.get(hid)),
				Restrictions.eq("sponsor", userService.get(uid)),
				Restrictions.eq("isDeleted", false),
				Restrictions.eq("flag", MessageFlag.JUST_EVENT)
		};
		return criterions;
	}
	@Override
	public List<Event> getAHabitEvents(Long uid, Integer hid, Integer startId,
			Integer length, boolean after) {
		return getPrivilegeEvents(after,startId,length,uid,habitEventCriterion(hid));
  	}

	@Override
	public List<Event> getAUserHabitEvent(Long uid, Long beWatched,
			Integer hid, Integer startId, Integer length, boolean after) {
		return getPrivilegeEvents(after,startId,length,uid,userHabitEventCriterion(hid,beWatched));
	}
	private List<Event> getUnreadInformation(Long uid) {
		return eventDao.findByCriteria(unreadInfoCriterions(uid));
	}
	@Override
	public void readAllUnreadInformations(Long uid) {
		List<Event> events = getUnreadInformation(uid);
		for (Event event : events)
			readAInformation(event.getId());
	}

	@Override
	public List<Event> getFriendsEventsInHabit(Long uid, Integer hid,
			Integer startId, Integer length, boolean after) {
		List<User> friends = relationShipService.findFriends(uid);
		if(null == friends||friends.isEmpty()) return new ArrayList<Event>();
		return getPrivilegeEvents(after,startId,length,uid,friendsHabitRelevantEvents(uid,hid,friends));
	}

	@Override
	public Integer createRemindInfo(Long sponsorId, Integer receiverHabitId,
			String content) {
		UserHabit uH = userHabitService.get(receiverHabitId);
		if (null != uH)
			return createHabitInfo(sponsorId, uH.getUser().getId(), uH
					.getHabit().getId(), EventType.REMAIND_SOMEBODY, content,
					null);
		return null;
	}

	@Override
	public List<Event> getAllInformation(Long uid, Integer startId,
			Integer length, boolean after) {
		return warpCriterions(length,startId,after,allInfosCriterion(uid));
	//	return eventDao.findByCriteria(length, Utils.warpIdRangeLimit(after, startId, allInfosCriterion(uid)));
	}

	@Override
	public List<Event> getAllEvents(Integer startId, Integer length,
			boolean after) {
		return warpCriterions(length,startId,after,baseEventCriterion());
		//return eventDao.findByCriteria(length,Utils.warpIdRangeLimit(after, startId, baseEventCriterion()));
	}

	@Override
	public List<Event> getMyAndFriendsEvent(Long uid, Integer startId,
			Integer length, boolean after) {
		return getPrivilegeEvents(after,startId,length,uid,friendsAndMyRelevantEvents(uid));
	}
	@Override
	public List<Event> getEvents(Long uid,Integer hid){
		return eventDao.findByCriteria(userHabitEventCriterion(hid,uid));
	}

	@Override
	public void saveOrUpdate(Event transientObject) {
		eventDao.saveOrUpdate(transientObject);		
	}
}

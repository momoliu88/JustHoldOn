package com.ebupt.justholdon.server.database.service;

import java.util.List;

import com.ebupt.justholdon.server.database.entity.Event;

public interface EventService extends GenericService<Event, Integer> {
	public Integer createGenericEvent(Long sponsorId, Long receiverId,
			Integer habitId, EventType type, Integer relationId,
			String content, MessageFlag flag);

	public Integer createFriendInfo(Long sponsorId, Long receiverId,
			EventType type, String content);
	public Integer createHabitInfo(Long sponsorId,Long receiverId,Integer habitId, EventType type,String content,Integer relatioId);	
	
	public Integer createRemindInfo(Long sponsorId,Integer receiverHabitId,String content);
	
	public Integer createHabitEvent(Long sponsortId,Integer habitId,EventType type,String content);
	public Integer createHabitEvent(Long sponsortId,Integer habitId,Integer relatioId,EventType type,String content);
	
	public void readAllUnreadInformations(Long uid);
	public void readAInformation(Integer infoId);
	
	public List<Event> getUnreadInformation(Long uid,Integer startId,Integer length,boolean after);
	public List<Event> getAllInformation(Long uid,Integer startId,Integer length,boolean after);
	public List<Event> getRelevantEvent(Long uid);
	//public List<Event> getRelevantEvent(Long uid,Integer start,Integer end);
	
	//get event from startId;
	//if startId == null,get newest event 
	public List<Event> getRelevantEventFromId(Long uid,Integer startId ,Integer length,boolean after);
	
	public List<Event> getRelevantEventFromId(Long uid,Long beWatched,Integer startId ,Integer length,boolean after);
	public List<Event> getAllFriendsRelevantEventFromId(Long uid,Integer startId ,Integer length,boolean after);
	public List<Event> getAHabitEvents(Long uid,Integer hid,Integer startId,Integer length,boolean after);
	public List<Event> getAUserHabitEvent(Long uid,Long beWatched,Integer hid,Integer startId,Integer length,boolean after);

	public List<Event> getFriendsEventsInHabit(Long uid,Integer hid,Integer startId,Integer length,boolean after);

//just for test
	public void deleteEvent(Integer event);
}

package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.Event;
import com.ebupt.justholdon.server.database.entity.EventType;
import com.ebupt.justholdon.server.database.entity.MessageFlag;

public interface EventService extends GenericService<Event, Integer> {
	public Integer createGenericEvent(Long sponsorId, Long receiverId,
			Integer habitId, EventType type, Integer relationId,
			String content, MessageFlag flag);

	public Integer createFriendInfo(Long sponsorId, Long receiverId,
			EventType type, String content);
	public Integer createHabitInfo(Long sponsorId,Long receiverId,Integer habitId, EventType type,String content,Integer relatioId);
	
	public Integer createHabitEvent(Long sponsortId,Integer habitId,EventType type,String content);
}
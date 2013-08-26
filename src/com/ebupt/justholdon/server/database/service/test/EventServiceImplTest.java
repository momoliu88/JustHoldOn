package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.entity.Event;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.HabitType;
import com.ebupt.justholdon.server.database.entity.PersistUnit;
import com.ebupt.justholdon.server.database.entity.PrivilegeType;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;
import com.ebupt.justholdon.server.database.service.CheckInService;
import com.ebupt.justholdon.server.database.service.CommentService;
import com.ebupt.justholdon.server.database.service.EventService;
import com.ebupt.justholdon.server.database.service.EventType;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.MessageFlag;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class EventServiceImplTest {
	static HabitService habitService;
	static UserService userService;
	static UserHabitService userHabitService;
	static CheckInService checkInService;
	static CommentService commentService;
	static EventService eventService;
	static List<Integer> hids = new ArrayList<Integer>();
	static List<Long> uids = new ArrayList<Long>();
	static List<Integer> cids = new ArrayList<Integer>();
	static List<Integer> eventIds = new ArrayList<Integer>();
	static {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		habitService = (HabitService) ctx.getBean("habitService");
		userService = (UserService) ctx.getBean("userService");
		userHabitService = (UserHabitService) ctx.getBean("userHabitService");
		checkInService = (CheckInService) ctx.getBean("checkInService");
		commentService = (CommentService) ctx.getBean("commentService");
		eventService = (EventService) ctx.getBean("eventService");
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
 
	}
	@Test
	public void testcreateRemindInfo()
	{
 
		Long uid=1824596703L;
		List<Event> events;
		events = eventService.getUnreadInformation(uid, 0, 10, true);
		System.out.println("size:"+events.size());
		for(Event event:events) System.out.println(event);
		events=eventService.getRelevantEventFromId(uid, 4642, 10, true);
		System.out.println("size:"+events.size());
		for(Event event:events) System.out.println(event);
		Long bewatched= 1507035727L;
		events=eventService.getRelevantEventFromId(uid,bewatched, 0, 10, true);
		System.out.println("size:"+events.size());
		for(Event event:events) System.out.println(event);
		Integer hid = 44;
		events=eventService.getFriendsEventsInHabit(uid, hid, 0, 10, true);
		System.out.println("friends size:"+events.size());
		for(Event event:events) System.out.println(event);
		events=eventService.getMyAndFriendsEvent(uid, 5251, 2, true);
		System.out.println("myandfriend size:"+events.size());
		for(Event event:events) System.out.println(event);
	}
	@Test
	public void testGetAll(){
		System.out.println("get all");
		List<Event> events = eventService.getAllEvents(0, 10,true);
		
		for(Event event:events) System.out.println(event);

	}
}

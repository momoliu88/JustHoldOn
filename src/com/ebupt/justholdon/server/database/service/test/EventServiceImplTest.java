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
import com.ebupt.justholdon.server.database.entity.EventType;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.HabitType;
import com.ebupt.justholdon.server.database.entity.MessageFlag;
import com.ebupt.justholdon.server.database.entity.PersistUnit;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.service.CheckInService;
import com.ebupt.justholdon.server.database.service.CommentService;
import com.ebupt.justholdon.server.database.service.EventService;
import com.ebupt.justholdon.server.database.service.HabitService;
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
		
		Habit habit = new Habit().setHabitName("name1")
				.setUnit(PersistUnit.DAY).setGroupName("groupName1")
				.setType(HabitType.SYSTEM).setStages("{1,2,3}");
		Habit habit1 = new Habit().setHabitName("name2")
				.setUnit(PersistUnit.DAY).setGroupName("groupName1")
				.setType(HabitType.SYSTEM).setStages("{1,2,3}");
		User user1 = new User("user1", "password", "avatar", 787L, "device");
		User user2 = new User("user2", "password", "avatar", 789L, "device");

		hids.add(habitService.save(habit));
		hids.add(habitService.save(habit1));

		uids.add(userService.save(user1));
		uids.add(userService.save(user2));
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		for(Integer eventid:eventIds)
			eventService.deleteEvent(eventid);
		eventIds.clear();
	}

	@Test
	public void testCreateGenericEvent() {
		Long uid = uids.get(0);
		eventIds.add(
				eventService.createGenericEvent(uid, null, hids.get(0), 
						EventType.SOMEBODY_JOININ_APP, null, "join in this app", MessageFlag.JUST_EVENT));
		assertEquals(1,userService.get(uid).getSponsorEvent().size());
		eventIds.add(
				eventService.createGenericEvent(uid, uids.get(1), hids.get(0), 
						EventType.INVITE_JOININ_HABIT, null, "invite to join in this app", MessageFlag.UNREADED));
		assertEquals(2,userService.get(uid).getSponsorEvent().size());

	}

	@Test
	public void testCreateFriendInfo() {
		eventIds.add(eventService.createFriendInfo(uids.get(0), uids.get(1), EventType.WANT_BE_FRIEND, "how about to be friend"));
		assertEquals(1,userService.get(uids.get(0)).getSponsorEvent().size());

	}

	@Test
	public void testCreateHabitInfo() {
		eventIds.add(eventService
				.createHabitInfo(uids.get(0), uids.get(1), hids.get(0),EventType.INVITE_JOININ_HABIT, "how about to be friend",null));
		eventIds.add(eventService
				.createHabitInfo(uids.get(0), uids.get(1), hids.get(0),EventType.COMMENT_CHECKIN, "a comment on habit",12));
		assertEquals(2,userService.get(uids.get(0)).getSponsorEvent().size());
		assertEquals(2,userService.get(uids.get(1)).getReceiverEvent().size());
		assertEquals(2,habitService.get(hids.get(0)).getEvents().size());
	}

	@Test
	public void testCreateHabitEvent() {
		eventIds.add(eventService
				.createHabitEvent(uids.get(0), hids.get(0),EventType.SOMEBODY_JOININ_HABIT, "how about to be friend"));
		 
		assertEquals(1,userService.get(uids.get(0)).getSponsorEvent().size());
		assertEquals(1,habitService.get(hids.get(0)).getEvents().size());
 	}

	@Test
	public void testReadAInformation() {
		eventIds.add(eventService
				.createFriendInfo(uids.get(0),uids.get(1), EventType.WANT_BE_FRIEND, "wanted to be friend"));
		List<Event> events = eventService.getUnreadInformation(uids.get(1));
		assertEquals(1,events.size());
		
		eventService.readAInformation(eventIds.get(0));
		assertEquals(0,eventService.getUnreadInformation(uids.get(1)).size());
	}

	@Test
	public void testGetUnreadInformation() {
		eventIds.add(eventService
				.createHabitEvent(uids.get(0), hids.get(0),EventType.SOMEBODY_JOININ_HABIT, "how about to be friend"));
		eventIds.add(eventService
				.createFriendInfo(uids.get(0),uids.get(1), EventType.WANT_BE_FRIEND, "wanted to be friend"));
		eventIds.add(eventService
				.createHabitInfo(uids.get(0), uids.get(1),hids.get(0), EventType.INVITE_JOININ_HABIT, "invide to habit", null));
		List<Event> events = eventService.getUnreadInformation(uids.get(1));
		assertEquals(2,events.size());
	}

	@Test
	public void testGetRelevantEventLong() {
		eventIds.add(eventService
				.createHabitEvent(uids.get(0), hids.get(0),EventType.SOMEBODY_JOININ_HABIT, "how about to be friend"));
		eventIds.add(eventService
				.createFriendInfo(uids.get(0),uids.get(1), EventType.WANT_BE_FRIEND, "wanted to be friend"));
		eventIds.add(eventService
				.createHabitInfo(uids.get(0), uids.get(1),hids.get(0), EventType.INVITE_JOININ_HABIT, "invide to habit", null));
		List<Event> events = eventService.getRelevantEvent(uids.get(0));
		assertEquals(1,events.size());
	}
//
//	@Test
//	public void testGetRelevantEventLongIntegerInteger() {
//		fail("Not yet implemented");
//	}

}

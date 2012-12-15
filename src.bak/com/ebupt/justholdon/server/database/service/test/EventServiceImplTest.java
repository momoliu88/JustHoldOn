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
import com.ebupt.justholdon.server.database.entity.MessageFlag;
import com.ebupt.justholdon.server.database.entity.PersistUnit;
import com.ebupt.justholdon.server.database.entity.PrivilegeType;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;
import com.ebupt.justholdon.server.database.service.CheckInService;
import com.ebupt.justholdon.server.database.service.CommentService;
import com.ebupt.justholdon.server.database.service.EventService;
import com.ebupt.justholdon.server.database.service.EventType;
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
		
		for(int i  = 0 ; i < 5;i++)
		{
			String name = "name"+i;
			Habit habit = new Habit().setHabitName(name)
					.setUnit(PersistUnit.DAY).setGroupName("groupName1")
					.setType(HabitType.SYSTEM).setStages("{1,2,3}");
			hids.add(habitService.save(habit));
			
		}
		for(int i = 0 ;i < 5;i++)
		{
			Long counter = 700L+i;
			User user = new User("user1", "password", "avatar", counter, "device");
			uids.add(userService.save(user));
		}
		UserHabit uHid = new UserHabit().setPrivilege(PrivilegeType.ONLY_FRIENDS);
		UserHabit uHid1 = new UserHabit().setPrivilege(PrivilegeType.ALL);
		UserHabit uHid2 = new UserHabit().setPrivilege(PrivilegeType.MYSELF);
		UserHabit uHid3 = new UserHabit().setPrivilege(PrivilegeType.ONLY_FRIENDS);

		userHabitService.connectUserHabitAndCreateEvent(uids.get(0), hids.get(0), uHid, "content"); 
		userHabitService.connectUserHabitAndCreateEvent(uids.get(0), hids.get(1), uHid1, "content"); 
		userHabitService.connectUserHabitAndCreateEvent(uids.get(0), hids.get(2), uHid2, "content"); 
		userHabitService.connectUserHabitAndCreateEvent(uids.get(0), hids.get(3), uHid3, "content"); 
		UserHabit uHid4 = new UserHabit().setPrivilege(PrivilegeType.ONLY_FRIENDS);
		UserHabit uHid5 = new UserHabit().setPrivilege(PrivilegeType.ONLY_FRIENDS);

		userHabitService.connectUserHabitAndCreateEvent(uids.get(1), hids.get(0), uHid4, "content"); 
		userHabitService.connectUserHabitAndCreateEvent(uids.get(3), hids.get(0), uHid5, "content"); 
		
//		 
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
//		for(Integer eventid:eventIds)
//			eventService.deleteEvent(eventid);
//		eventIds.clear();
	}

//	@Test
//	public void testCreateGenericEvent() {
//		Long uid = uids.get(0);
//		eventIds.add(
//				eventService.createGenericEvent(uid, null, hids.get(0), 
//						EventType.SOMEBODY_JOININ_APP, null, "join in this app", MessageFlag.JUST_EVENT));
//		assertEquals(1,userService.get(uid).getSponsorEvent().size());
//		eventIds.add(
//				eventService.createGenericEvent(uid, uids.get(1), hids.get(0), 
//						EventType.INVITE_JOININ_HABIT, null, "invite to join in this app", MessageFlag.UNREADED));
//		assertEquals(2,userService.get(uid).getSponsorEvent().size());
//
//	}
//
//	@Test
//	public void testCreateFriendInfo() {
//		eventIds.add(eventService.createFriendInfo(uids.get(0), uids.get(1), EventType.WANT_BE_FRIEND, "how about to be friend"));
//		assertEquals(1,userService.get(uids.get(0)).getSponsorEvent().size());
//
//	}
//
//	@Test
//	public void testCreateHabitInfo() {
//		eventIds.add(eventService
//				.createHabitInfo(uids.get(0), uids.get(1), hids.get(0),EventType.INVITE_JOININ_HABIT, "how about to be friend",null));
//		eventIds.add(eventService
//				.createHabitInfo(uids.get(0), uids.get(1), hids.get(0),EventType.COMMENT_CHECKIN, "a comment on habit",12));
//		assertEquals(2,userService.get(uids.get(0)).getSponsorEvent().size());
//		assertEquals(2,userService.get(uids.get(1)).getReceiverEvent().size());
//		assertEquals(2,habitService.get(hids.get(0)).getEvents().size());
//	}
//
//	@Test
//	public void testCreateHabitEvent() {
//		eventIds.add(eventService
//				.createHabitEvent(uids.get(0), hids.get(0),EventType.SOMEBODY_JOININ_HABIT, "how about to be friend"));
//		 
//		assertEquals(1,userService.get(uids.get(0)).getSponsorEvent().size());
//		assertEquals(1,habitService.get(hids.get(0)).getEvents().size());
// 	}
//
//	@Test
//	public void testReadAInformation() {
//		eventIds.add(eventService
//				.createFriendInfo(uids.get(0),uids.get(1), EventType.WANT_BE_FRIEND, "wanted to be friend"));
//		List<Event> events = eventService.getUnreadInformation(uids.get(1));
//		assertEquals(1,events.size());
//		
//		eventService.readAInformation(eventIds.get(0));
//		assertEquals(0,eventService.getUnreadInformation(uids.get(1)).size());
//	}
//
//	@Test
//	public void testGetUnreadInformation() {
//		eventIds.add(eventService
//				.createHabitEvent(uids.get(0), hids.get(0),EventType.SOMEBODY_JOININ_HABIT, "how about to be friend"));
//		eventIds.add(eventService
//				.createFriendInfo(uids.get(0),uids.get(1), EventType.WANT_BE_FRIEND, "wanted to be friend"));
//		eventIds.add(eventService
//				.createHabitInfo(uids.get(0), uids.get(1),hids.get(0), EventType.INVITE_JOININ_HABIT, "invide to habit", null));
//		List<Event> events = eventService.getUnreadInformation(uids.get(1));
//		assertEquals(2,events.size());
//	}
//
//	@Test
//	public void testGetRelevantEventLong() {
//		eventIds.add(eventService
//				.createHabitEvent(uids.get(0), hids.get(0),EventType.SOMEBODY_JOININ_HABIT, "how about to be friend"));
//		eventIds.add(eventService
//				.createFriendInfo(uids.get(0),uids.get(1), EventType.WANT_BE_FRIEND, "wanted to be friend"));
//		eventIds.add(eventService
//				.createHabitInfo(uids.get(0), uids.get(1),hids.get(0), EventType.INVITE_JOININ_HABIT, "invide to habit", null));
//		List<Event> events = eventService.getRelevantEvent(uids.get(0));
//		assertEquals(1,events.size());
//	}

//	@Test
//	public void testGetRelevantEventLongIntegerInteger() {
//		List<Event> events = eventService.getRelevantEventFromId(787l, null, 5, true);
//		for(Event event:events)
//		{
//			System.out.println("#id:"+event.getId()+" type:"+event.getType().toString()+" flag"+event.getFlag());
//		}
//		events = eventService.getRelevantEventFromId(787l,89,10,false);
//		System.out.println("========================");
//		for(Event event:events)
//		{
//			System.out.println("#id:"+event.getId()+" type:"+event.getType().toString()+" flag"+event.getFlag());
//		}
//	}
	@Test
	public void testgetRelevantEventFromIdLongBeWatched()
	{
		List<Event> events =eventService.getRelevantEventFromId(uids.get(1),uids.get(0), null, 10, false);
		System.out.println("===========================");
		for(Event event:events)
		{
			System.out.println("#id:"+event.getId()+" type:"+event.getType().toString()+" flag"+event.getFlag());
		}
		userService.beFriend(uids.get(0), uids.get(2));
		userService.beFriend(uids.get(1), uids.get(2));
		userService.beFriend(uids.get(0), uids.get(1));

		events =eventService.getRelevantEventFromId(uids.get(1),uids.get(0), null, 10, false);
		System.out.println("===========================");
		for(Event event:events)
		{
			System.out.println("#id:"+event.getId()+" type:"+event.getType().toString()+" flag"+event.getFlag());
		}
		events = eventService.getAllFriendsRelevantEventFromId(uids.get(2), null, 10, true);
		System.out.println("===========================");
		for(Event event:events)
		{
			System.out.println("#id:"+event.getId()+" type:"+event.getType().toString()+" flag"+event.getFlag());
		}
		events = eventService.getAHabitEvents(uids.get(0),hids.get(0), null, 10, true);
		System.out.println("===========================");
		for(Event event:events)
		{
			System.out.println("#id:"+event.getId()+" type:"+event.getType().toString()+" flag"+event.getFlag());
		}
		
		events = eventService.getAUserHabitEvent(uids.get(0), uids.get(1),hids.get(0), null, 10, true);
		System.out.println("===========================");
		for(Event event:events)
		{
			System.out.println("#id:"+event.getId()+" type:"+event.getType().toString()+" flag"+event.getFlag());
		}
	}

}

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
import com.ebupt.justholdon.server.database.service.EventService;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.HabitState;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class UserHabitServiceImplTest {
	static HabitService habitService;
	static UserService userService;
	static UserHabitService userHabitService;
	static CheckInService checkInService;
	static EventService eventService;

	static List<Integer> hids = new ArrayList<Integer>();
	static List<Long> uids = new ArrayList<Long>();
	static List<Integer> cids = new ArrayList<Integer>();
	static	{
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		habitService = (HabitService) ctx.getBean("habitService");
		userService = (UserService) ctx.getBean("userService");
		eventService = (EventService) ctx.getBean("eventService");
		userHabitService = (UserHabitService) ctx.getBean("userHabitService");
		checkInService = (CheckInService) ctx.getBean("checkInService");
		Habit habit = new Habit().setHabitName("name1").setUnit(PersistUnit.DAY)
				.setGroupName("groupName1").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		Habit habit1 = new Habit().setHabitName("name2").setUnit(PersistUnit.DAY)
				.setGroupName("groupName1").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		Habit habit2 = new Habit().setHabitName("name3").setUnit(PersistUnit.DAY)
				.setGroupName("groupName1").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		User user1 = new User("user1", "password", "avatar", 787L, "device");
		User user2 = new User("user2", "password", "avatar", 789L, "device");

		hids.add(habitService.save(habit));
		hids.add(habitService.save(habit1));
		hids.add(habitService.save(habit2));

		uids.add(userService.save(user1));
		uids.add(userService.save(user2));
		
		UserHabit uHid = new UserHabit().setPrivilege(PrivilegeType.MYSELF);
		UserHabit uHid2 = new UserHabit().setPrivilege(PrivilegeType.ALL);
		UserHabit uHid3 = new UserHabit().setPrivilege(PrivilegeType.ONLY_FRIENDS);

		userHabitService.connectUserHabit(uids.get(0), hids.get(1), uHid2);
		userHabitService.connectUserHabit(uids.get(0), hids.get(0), uHid);
		userHabitService.connectUserHabit(uids.get(0), hids.get(2), uHid3);
	}
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testGetUserHabit() {
		UserHabit userHabit = userHabitService.getUserHabit(uids.get(0), hids.get(1));
		assertEquals("user1",userHabit.getUser().getUserName());
		assertEquals(PrivilegeType.ALL,userHabit.getPrivilege());
	}

	@Test
	public void testGetUserHabitsLong() {
		List<UserHabit> userHabits = userHabitService.getUserHabits(uids.get(0));
		assertEquals(3,userHabits.size());
	}

	@Test
	public void testGetUserHabitsLongLong() {
		List<UserHabit> userHabits = userHabitService.getUserHabits(uids.get(1), uids.get(0));
		assertEquals(1,userHabits.size());
		userService.beFriend(uids.get(0), uids.get(1));
		userHabits = userHabitService.getUserHabits(uids.get(1), uids.get(0));
		assertEquals(2,userHabits.size());
		userService.removeFriend(uids.get(0), uids.get(1));
	}
//	@Test
//	public void testGetUserHabitsHabitState()
//	{
//		userHabitService.exitHabit(uids.get(0), hids.get(0));
//		List<UserHabit>userHabitsAll = userHabitService.getUserHabits(uids.get(0));
//		List<UserHabit>userHabits = userHabitService.getUserHabits(uids.get(0), HabitState.DELETED);
//		assertEquals(1,userHabits.size());
//		assertEquals(3,userHabitsAll.size());
//	}
	@Test
	public void testGetUserWatchedHabitsHabitState()
	{
		List<UserHabit> userHabits = userHabitService.getUserHabits(uids.get(1), uids.get(0),HabitState.DELETED);
		assertEquals(0,userHabits.size());
	
		userHabitService.updateState(uids.get(0), hids.get(1),HabitState.DELETED);
		userHabitService.updateState(uids.get(0), hids.get(2),HabitState.DELETED);
		userHabits = userHabitService.getUserHabits(uids.get(1), uids.get(0),HabitState.DELETED);
		assertEquals(1,userHabits.size());

		userService.beFriend(uids.get(0), uids.get(1));
		userHabits = userHabitService.getUserHabits(uids.get(1), uids.get(0),HabitState.DELETED);
		assertEquals(2,userHabits.size());
	}
	@Test
	public void testhasParticipateHabit()
	{
		boolean hasAttended = userHabitService.hasParticipateHabit(uids.get(0), hids.get(0));
		assertEquals(true,hasAttended);
		hasAttended = userHabitService.hasParticipateHabit(uids.get(1), hids.get(0));
		assertEquals(false,hasAttended);	
	}
	@Test
	public void testconnectUserHabitAndCreateEvent()
	{
		UserHabit uHid = new UserHabit().setPrivilege(PrivilegeType.MYSELF);
		userHabitService.connectUserHabitAndCreateEvent(uids.get(1), hids.get(0), uHid, "attend habits");
		List<Event> events= eventService.findAll();
		assertEquals(1,events.size());
		for(Event event:events)
		{
			System.out.println("#"+event.getId()+" "+event.getType().toString());
			eventService.deleteEvent(event.getId());
		}
	}
	@Test
	public void testupdateStateAndCteateEvent()
	{
		UserHabit uHid = new UserHabit().setPrivilege(PrivilegeType.MYSELF);
		userHabitService.connectUserHabitAndCreateEvent(uids.get(1), hids.get(0), uHid, "attend habits");
		userHabitService.updateStateAndCteateEvent(uids.get(1), hids.get(0),HabitState.CONSOLIDATING,"hold on");
		List<Event> events= eventService.findAll();
		assertEquals(2,events.size());
		for(Event event:events)
		{
			System.out.println("#"+event.getId()+" "+event.getType().toString());
			eventService.deleteEvent(event.getId());
		}
	}
}

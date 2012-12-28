package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.HabitType;
import com.ebupt.justholdon.server.database.entity.PersistUnit;
import com.ebupt.justholdon.server.database.entity.PrivilegeType;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;
import com.ebupt.justholdon.server.database.service.ApproveService;
import com.ebupt.justholdon.server.database.service.CheckInService;
import com.ebupt.justholdon.server.database.service.EventService;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class ApproveServiceImplTest {
	static HabitService habitService;
	static UserService userService;
	static ApproveService approveService;
	static UserHabitService userHabitService;
	static CheckInService checkInService;
	static EventService eventService;
	static List<Integer> hids = new ArrayList<Integer>();
	static List<Long> uids = new ArrayList<Long>();
	static List<Integer> cids = new ArrayList<Integer>();
	static List<Integer> approvesIds = new ArrayList<Integer>();

	static	{
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		habitService = (HabitService) ctx.getBean("habitService");
		userService = (UserService) ctx.getBean("userService");
		eventService = (EventService) ctx.getBean("eventService");
		approveService = (ApproveService) ctx.getBean("approveService");
		userHabitService = (UserHabitService) ctx.getBean("userHabitService");
		checkInService = (CheckInService) ctx.getBean("checkInService");
		Habit habit = new Habit().setHabitName("name1").setUnit(PersistUnit.DAY)
				.setGroupName("groupName1").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		Habit habit1 = new Habit().setHabitName("name2").setUnit(PersistUnit.DAY)
				.setGroupName("groupName1").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		User user1 = new User("user1", "password", "avatar", 787L, "device");
		User user2 = new User("user2", "password", "avatar", 788L, "device");

		hids.add(habitService.save(habit));
		hids.add(habitService.save(habit1));

		uids.add(userService.save(user1));
		uids.add(userService.save(user2));

		UserHabit uHid = new UserHabit().setPrivilege(PrivilegeType.ALL);
		UserHabit uHid2 = new UserHabit().setPrivilege(PrivilegeType.ALL);
		userHabitService.connectUserHabit(uids.get(0), hids.get(1), uHid2);
		userHabitService.connectUserHabit(uids.get(0), hids.get(0), uHid);
		
		cids.add(checkInService.checkIn(uids.get(0), hids.get(1)));
		cids.add(checkInService.checkIn(uids.get(0), hids.get(0)));

		
	}
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testApproveCheckIn() {
		approvesIds.add(approveService.approveCheckIn(uids.get(1), cids.get(0)));
		approvesIds.add(approveService.approveCheckIn(uids.get(0), cids.get(0)));

		assertEquals(true,approveService.isApproved(uids.get(1),cids.get(0)));
		assertEquals(true,approveService.isApproved(uids.get(0),cids.get(0)));
		
		assertEquals(false,approveService.isApproved(uids.get(0),cids.get(1)));
		assertEquals(false,approveService.isApproved(uids.get(1),cids.get(1)));
		for(Integer id : approvesIds)
			approveService.delete(id);
	}

//	@Test
//	public void testIsApproved() {
//		fail("Not yet implemented");
//	}

}

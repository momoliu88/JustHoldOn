package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.Approve;
import com.ebupt.justholdon.server.database.entity.CheckIn;
import com.ebupt.justholdon.server.database.entity.Flag;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.HabitType;
import com.ebupt.justholdon.server.database.entity.PersistUnit;
import com.ebupt.justholdon.server.database.entity.PrivilegeType;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;
import com.ebupt.justholdon.server.database.service.ApproveService;
import com.ebupt.justholdon.server.database.service.CheckInService;
import com.ebupt.justholdon.server.database.service.CommentService;
import com.ebupt.justholdon.server.database.service.EventService;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class EffectiveTestCheckin {
	static HabitService habitService;
	static UserService userService;
	static UserHabitService userHabitService;
	static CheckInService checkInService;
	static EventService eventService;
	static CommentService commentService;
	static ApproveService approveService;
	static List<Integer> hids = new ArrayList<Integer>();
	static List<Long> uids = new ArrayList<Long>();
	static List<Integer> cids = new ArrayList<Integer>();
	static {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		habitService = (HabitService) ctx.getBean("habitService");
		userService = (UserService) ctx.getBean("userService");
		eventService = (EventService) ctx.getBean("eventService");
		userHabitService = (UserHabitService) ctx.getBean("userHabitService");
		checkInService = (CheckInService) ctx.getBean("checkInService");
		commentService = (CommentService) ctx.getBean("commentService");
		approveService = (ApproveService) ctx.getBean("approveService");
		// Habit habit = new
		// Habit().setHabitName("name1").setUnit(PersistUnit.DAY)
		// .setGroupName("groupName1").setType(HabitType.SYSTEM)
		// .setStages("{1,2,3}");
		// Habit habit1 = new
		// Habit().setHabitName("name2").setUnit(PersistUnit.DAY)
		// .setGroupName("groupName1").setType(HabitType.SYSTEM)
		// .setStages("{1,2,3}");
		// User user1 = new User("user1", "password", "avatar", 787L, "device");
		// User user2 = new User("user1", "password", "avatar", 788L, "device");
		//
		// hids.add(habitService.save(habit));
		// hids.add(habitService.save(habit1));
		//
		// uids.add(userService.save(user1));
		// uids.add(userService.save(user2));
		//
		// UserHabit uHid = new UserHabit().setPrivilege(PrivilegeType.ALL);
		// UserHabit uHid2 = new UserHabit().setPrivilege(PrivilegeType.ALL);
		// userHabitService.connectUserHabit(uids.get(0), hids.get(1), uHid2);
		// userHabitService.connectUserHabit(uids.get(0), hids.get(0), uHid);
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testcount() {
		Long uid = 1762687133L;
		System.out.println("===============================================");
		System.out.println("directly get friends size "
				+ userService.countFriends(uid));
		System.out.println("===============================================");
		System.out.println("directly get userhabit size "
				+ userService.countUserHabits(uid));
	}

	@Test
	public void test() {
		Long uid = 1762687133L;
		Set<User> users = userService.getFriends(uid);

		for (User user : users)
			System.out.println(user.getUserName());
		getUser();
	}

	public static void getUser() {

		Long start = System.currentTimeMillis();
		CheckIn ck = checkInService.get(2);
		System.out.println("interval: " + (System.currentTimeMillis() - start));
		List<Approve> approves = checkInService.getApproves(2);
		System.out.println("size:approve " + approves.size());

		for (Approve app : approves) {
			User user = app.getUser();
			System.out.println("user: " + user.getUserName() + " id:"
					+ user.getId());
			Set<Flag> flags = userService.getFlags(user.getId());
			System.out.println("flags: " + flags.size());
			System.out.println("habits: "
					+ userService.getUserHabits(user.getId()));
		}
		start = System.currentTimeMillis();
		checkInService.get(2);
		System.out.println("interval: " + (System.currentTimeMillis() - start));
		System.out.println("isapproved "
				+ approveService.isApproved(1762687133L, 13));
	}
}

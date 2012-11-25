package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.HabitType;
import com.ebupt.justholdon.server.database.entity.PersistUnit;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class HabitServiceImplTest {
	HabitService habitService;
	Habit habit;
	String password = "pass";
	String avatar = "avatar";
	String device = "device";
	Long uid = 123L;
	List<Integer> hids = new ArrayList<Integer>();
	UserHabitService userHabitService;
	UserDao userDao;
	User user;
	User user1;
	User user2;
	List<Long> uids = new ArrayList<Long>();
	UserService userService;
	UserHabit userHabit;
	UserHabit userHabit1;
	UserHabit userHabit2;
	UserHabit userHabit3;
	UserHabit userHabit4;
	Habit habit1;
	Habit habit2;

	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		habitService = (HabitService) ctx.getBean("habitService");
		userService = (UserService) ctx.getBean("userService");
		userHabitService = (UserHabitService) ctx.getBean("userHabitService");

		habit = new Habit().setHabitName("name1").setUnit(PersistUnit.DAY)
				.setGroupName("groupName1").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		habit1 = new Habit().setHabitName("name2").setUnit(PersistUnit.DAY)
				.setGroupName("groupName1").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		habit2 = new Habit().setHabitName("name3").setUnit(PersistUnit.DAY)
				.setGroupName("groupName2").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");

		hids.add(habitService.save(habit));
		hids.add(habitService.save(habit1));
		hids.add(habitService.save(habit2));

		user = new User("user0", password, avatar, uid, device);
		user1 = new User("user1", password, avatar, 787L, device);
		user2 = new User("user2", password, avatar, 188L, device);

		uids.add(userService.save(user));
		uids.add(userService.save(user1));
		uids.add(userService.save(user2));

		userService.beFriend(uids.get(1), uids.get(0));
		userService.beFriend(uids.get(1), uids.get(2));
		userHabit = new UserHabit();
		userHabit1 = new UserHabit();
		userHabit2 = new UserHabit();// .setHabit(habit2).setUser(user1);
		userHabit3 = new UserHabit();// .setHabit(habit).setUser(user);
		userHabit4 = new UserHabit();// .setHabit(habit).setUser(user);

		userHabitService.connectUserHabit(uids.get(0), hids.get(0), userHabit);
		userHabitService.connectUserHabit(uids.get(0), hids.get(1), userHabit1);
		userHabitService.connectUserHabit(uids.get(0), hids.get(2), userHabit2);

		User _user = userService.get(uids.get(0));

		for (UserHabit _uH : _user.getUserHabits()) {
			_uH.setModifyTime(new Date());
			userHabitService.update(_uH);
		}
		userHabitService.connectUserHabit(uids.get(1), hids.get(1), userHabit3);
		userHabitService.connectUserHabit(uids.get(2), hids.get(1), userHabit4);

	}

	@After
	public void tearDown() throws Exception {

		userService.removeFriend(uids.get(1), uids.get(0));
		userService.removeFriend(uids.get(1), uids.get(2));

		userHabitService.cancelUserHabit(uids.get(0), hids.get(0));
		userHabitService.cancelUserHabit(uids.get(0), hids.get(1));
		userHabitService.cancelUserHabit(uids.get(0), hids.get(2));
		userHabitService.cancelUserHabit(uids.get(1), hids.get(1));
		userHabitService.cancelUserHabit(uids.get(2), hids.get(1));
//
//		for (Long id : uids)
//			userService.delete(id);
//		for (Integer id : hids)
//			habitService.delete(id);

	}

	/*
	 * @Test public void test() { Map<String, Integer> map =
	 * habitService.groupNames(); for (Entry<String, Integer> entry :
	 * map.entrySet()) System.out.println(entry.getKey() + " => " +
	 * entry.getValue()); }
	 * 
	 * @Test public void testfindAGroup() { List<Habit> results =
	 * habitService.findAGroup("groupName1"); for(Habit result:results)
	 * System.out.println(result); }
	 */
	/*
	 * @Test public void testfindParticipateNum() { int num =
	 * habitService.findParticipateNum(hids.get(0)); assertEquals(1,num); }
	 * 
	 * @Test public void testfindParticipateNum_2() { int num =
	 * habitService.findParticipateNum(hids.get(1)); assertEquals(2, num); }
	 */
	
	@Test
	public void testfindParticipateAndFriends() {
		List<User> users = habitService.findParticipateAndFriends(hids.get(1),
				uids.get(1));
		assertEquals(2, users.size());
		for (User user : users)
			System.out.println("USERNAME " + user.getUserName());
	}

	
}

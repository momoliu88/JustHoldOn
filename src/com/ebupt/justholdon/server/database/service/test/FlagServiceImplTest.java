package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.Flag;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.HabitType;
import com.ebupt.justholdon.server.database.entity.PersistUnit;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.service.FlagService;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.UserFieldService;
import com.ebupt.justholdon.server.database.service.UserService;

public class FlagServiceImplTest {
	UserFieldService userFieldService;
	FlagService flagService;
	UserService userService;
	HabitService habitService;
	User user1;
	List<Integer> flagIds = new ArrayList<Integer>();
	User user2;
	Flag flag1;
	Flag flag2;
	Flag flag3;
	Habit habit1;
	Habit habit2;
	Habit habit3;
	UserDao userDao;
	List<Flag> flags = new ArrayList<Flag>();

	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		userFieldService = (UserFieldService) ctx.getBean("userFieldService");
		habitService = (HabitService) ctx.getBean("habitService");
		userDao = (UserDao) ctx.getBean("userDao");
		userService = (UserService) ctx.getBean("userService");
		flagService = (FlagService) ctx.getBean("flagService");

		user1 = new User().setId(11L).setUserName("name5").setAvatar("ava")
				.setPassword("pass").setToken("token").setDeviceToken("device");
		user2 = new User().setId(12L).setUserName("name7").setAvatar("ava")
				.setPassword("pass").setToken("token").setDeviceToken("device");
		habit1 = new Habit().setStages("{1,2,3}").setHabitName("habitName1")
				.setGroupName("group1").setType(HabitType.SYSTEM)
				.setUnit(PersistUnit.DAY);
		habit2 = new Habit().setStages("{1,2,3}").setHabitName("habitName2")
				.setGroupName("group1").setType(HabitType.SYSTEM)
				.setUnit(PersistUnit.DAY);
		habit3 = new Habit().setStages("{1,2,3}").setHabitName("habitName3")
				.setGroupName("group1").setType(HabitType.SYSTEM)
				.setUnit(PersistUnit.DAY);

		habitService.save(habit1);
		habitService.save(habit2);
		habitService.save(habit3);
		userService.save(user2);
		userService.save(user1);

		flag1 = new Flag().setContent("flag2").setTarget("type1");
		flag2 = new Flag().setContent("flag1").setTarget("type1");
		flag3 = new Flag().setContent("flag3").setTarget("type3");

		flagIds.add(flagService.save(flag1));
		flagIds.add(flagService.save(flag2));
		flagIds.add(flagService.save(flag3));

		flagService.addUser(user2, flag1);
		flagService.addUser(user1, flag1);
		
		flagService.addHabit(habit1, flag1);
		flagService.addHabit(habit1, flag2);
		flagService.addHabit(habit1, flag3);

		flagService.addHabit(habit2, flag1);
		flagService.addHabit(habit3, flag1);
	}

	@After
	public void tearDown() throws Exception {
		flagIds.clear();
		
		flagService.removeUser(user1, flag1);
		flagService.removeUser(user2, flag1);
		
		flagService.removeHabit(habit1, flag1);
		flagService.removeHabit(habit1, flag2);
		flagService.removeHabit(habit1, flag3);

		flagService.removeHabit(habit2, flag1);
		flagService.removeHabit(habit3, flag1);	
		
		flagService.delete(flag1);
		flagService.delete(flag2);
		flagService.delete(flag3);
		
		habitService.delete(habit1);
		habitService.delete(habit2);
		habitService.delete(habit3);
		
		userService.delete(user1);
		userService.delete(user2);

	}

	@Test
	public void testFindAllBoolean() {
		List<Flag> flags = flagService.findAll(true);
		assertEquals("flag2", flags.get(0).getContent());
	}

	@Test
	public void testFindAllSTARTEND() {
		List<Flag> results = flagService.findAll(true, 0, 2);
		assertEquals(2, results.size());

		assertEquals("flag2", results.get(0).getContent());
	}

	@Test
	public void testFindAll() {
		List<Flag> flags = flagService.findAll(false);
		// assertEquals(0,flags.size());
		assertEquals("flag2", flags.get(0).getContent());
	}

	@Test
	public void testfindAType() {
		List<Flag> flags = flagService.findAType("type1", 0, 10);
		assertEquals(2, flags.size());
		assertEquals("flag2", flags.get(0).getContent());
	}

	@Test
	public void testfindHabitCounts_1() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(flagIds.get(0));
		assertEquals(3,flagService.findHabitCounts(ids));
	}
	@Test
	public void testfindHabitCounts_2() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(flagIds.get(1));
		assertEquals(1,flagService.findHabitCounts(ids));
	}
	@Test
	public void testfindHabitCounts_3() {
		List<Integer> ids = flagIds;
		assertEquals(3,flagService.findHabitCounts(ids));
	}
}

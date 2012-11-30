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
	List<Integer> hids = new ArrayList<Integer>();
	List<Long> uids = new ArrayList<Long>();

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

		hids.add(habitService.save(habit1));
		hids.add(habitService.save(habit2));
		hids.add(habitService.save(habit3));
		
		uids.add( userService.save(user2));
		uids.add(userService.save(user1));


		flag1 = new Flag().setContent("flag2").setTarget("type1");
		flag2 = new Flag().setContent("flag1").setTarget("type1");
		flag3 = new Flag().setContent("flag3").setTarget("type3");

		flagIds.add(flagService.save(flag1));
		flagIds.add(flagService.save(flag2));
		flagIds.add(flagService.save(flag3));

		User _user = userService.get(uids.get(0));
		_user.setUserName("*********");
		userService.update(_user);
		User _user2 = userService.get(uids.get(1));
		userService.update(_user2);
		
//		_user2.getFlags().add(flag1);
//		flag1.getUsers().add(_user2);
//		flagService.update(flag1);
//		userService.update(_user2);
		
		flagService.addUser(uids.get(0), flagIds.get(0));
		flagService.addUser(uids.get(1), flagIds.get(0));
		
		flagService.addUser(uids.get(1), flagIds.get(0));

		flagService.addHabit(hids.get(0), flagIds.get(0));
		flagService.addHabit(hids.get(1), flagIds.get(1));
		flagService.addHabit(hids.get(2), flagIds.get(2));

		flagService.addHabit(hids.get(1), flagIds.get(0));
		flagService.addHabit(hids.get(2), flagIds.get(0));
		
	}

	@After
	public void tearDown() throws Exception {
		
		flagService.removeUser(uids.get(0), flagIds.get(0));
		flagService.removeUser(uids.get(1), flagIds.get(0));
		
		
		flagService.removeHabit(hids.get(0), flagIds.get(0));
		flagService.removeHabit(hids.get(1), flagIds.get(1));
		flagService.removeHabit(hids.get(2), flagIds.get(2));

		flagService.removeHabit(hids.get(1), flagIds.get(0));
		flagService.removeHabit(hids.get(2), flagIds.get(0));	
		
		flagService.delete(flagIds.get(0));
		flagService.delete(flagIds.get(1));
		flagService.delete(flagIds.get(2));
		
		System.out.println(" delete uid");

		for(Long id:uids)
			userService.delete(id);
		for(Integer id:hids)
		{
			System.out.println("id "+id);
			habitService.delete(id);
		}
		
	}

	@Test
	public void testFindAllBoolean() {
		List<Flag> flags = flagService.findAll(true);
		assertEquals("flag2", flags.get(0).getContent());
		
//		User _user = userService.get(12L);
//		Set<Flag> flags = _user.getFlags();
//		for(Flag flag:flags)
//		{
//			System.out.println(flag);
//		}
	}
/*
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
	*/
}

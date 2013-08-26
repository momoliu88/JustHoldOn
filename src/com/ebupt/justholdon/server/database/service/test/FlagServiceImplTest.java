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
		Integer id1 = flagService.createFlag("减肥");
		Integer id2= flagService.createFlag("聪明");
//		flagService.addUser(1716305793L, 4);
		flagService.addHabit(67, 4);
	}

	@After
	public void tearDown() throws Exception {
		
//		flagService.removeUser(uids.get(0), flagIds.get(0));
//		flagService.removeUser(uids.get(1), flagIds.get(0));
//		
//		
//		flagService.removeHabit(hids.get(0), flagIds.get(0));
//		flagService.removeHabit(hids.get(1), flagIds.get(1));
//		flagService.removeHabit(hids.get(2), flagIds.get(2));
//
//		flagService.removeHabit(hids.get(1), flagIds.get(0));
//		flagService.removeHabit(hids.get(2), flagIds.get(0));	
		
//		flagService.delete(flagIds.get(0));
//		flagService.delete(flagIds.get(1));
//		flagService.delete(flagIds.get(2));
//		
//		System.out.println(" delete uid");
//
//		for(Long id:uids)
//			userService.delete(id);
//		for(Integer id:hids)
//		{
//			System.out.println("id "+id);
//			habitService.delete(id);
//		}
		
	}

	@Test
	public void testFindAllBoolean() {
		List<Flag> flags = flagService.findAll(false, 0, 20, true);
		for(Flag flag:flags)
			System.out.println(flag);
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(4);
		ids.add(5);
		List<Habit> habits = flagService.findHabits(ids);
		for(Habit habit:habits)
			System.out.println(habit);
		habits = flagService.findHabits(1716305793L);
		for(Habit habit:habits)
			System.out.println(habit);
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

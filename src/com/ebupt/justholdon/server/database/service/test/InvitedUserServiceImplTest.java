package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.service.InvitedUserService;
import com.ebupt.justholdon.server.database.service.UserService;

public class InvitedUserServiceImplTest {
	static InvitedUserService service;
	static UserService userService;
	static {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		 service = (InvitedUserService) ctx.getBean("invitedUserService");
			userService = (UserService) ctx.getBean("userService");

	}
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCountInvitedUserNums() {
//		fail("Not yet implemented");
	}
//	@Test
//	public void testInvitedUsernames(){
//		List<String> us = service.hasInvitedUser();
//		service.inviteUser(1L,"李墨Eli");
//		for(String name:us)
//			System.out.println(name);
//	}
	@Test
	public void testCount(){
		Calendar cal = Calendar.getInstance();
		cal.set(2013, 4,9,0,0,0);
		Date start = cal.getTime();
		cal.set(2013, 4,10,0,0,0);
		Date end = cal.getTime();
		System.out.println(userService.getUsers(start, end));
	}
//	@Test
//	public void testInviteUser() {
//		service.inviteUser(1L, "你妹");
//		service.inviteUser(1893424284L, "你妹");
//
//	}
//	@Test
//	public void testCount(){
//		Calendar cal = Calendar.getInstance();
//		cal.set(2013, 04, 17,0,0,0);
//		Date startDate= cal.getTime();
//		cal.set(2013,04,18,0,0,0);
//		Date endDate = cal.getTime();
//		Integer nums = service.countInvitedUserNums(startDate, endDate, 1893424284L);
//		System.out.println(startDate +" "+endDate+" : "+nums);
//	}
}

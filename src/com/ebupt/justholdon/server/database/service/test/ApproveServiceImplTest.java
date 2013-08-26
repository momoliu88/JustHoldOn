package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

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
		
	}
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testApproveCheckIn()
	{
		Long uid = 1791131285L;
		Integer checkInId = 228;
		//approveService.approveCheckIn(1791131285L, 228);
		boolean isapproved = approveService.isApproved(uid, checkInId);
		assertEquals(true,isapproved);
		approveService.delete(754);
	}
 
}

package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.entity.SystemInfo;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.service.SystemInfoService;
import com.ebupt.justholdon.server.database.service.UserService;
import com.ebupt.justholdon.server.database.service.WeeklySummaryService;

public class SystemInfoServiceImplTest {
	List<Integer> infoIds = new ArrayList<Integer>();
	SystemInfoService systemInfoService;
	UserService userService;
	Long uid = 123L;
	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		systemInfoService= (SystemInfoService) ctx.getBean("systemInfoService");
		infoIds.add(systemInfoService.createSystemInformation("test1"));
		infoIds.add(systemInfoService.createSystemInformation("test2"));
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 9, 10);
		infoIds.add(systemInfoService.createSystemInformation("test3", "extra", cal.getTime()));
		
		userService = (UserService) ctx.getBean("userService");
		User user = new User("user0", "1233", "4563", uid, "DEVICE");
		userService.save(user);
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testGetUnreadedSystemInfoLong() {
		List<SystemInfo> infos = systemInfoService.getUnreadedSystemInfo(uid);
		assertEquals(3,infos.size());
		systemInfoService.readASystemInfo(uid, infoIds.get(0));
		infos = systemInfoService.getUnreadedSystemInfo(uid);
		assertEquals(2,infos.size());
		for(SystemInfo info:infos)
			System.out.println("#"+info.getContent());

	}
//
//	@Test
//	public void testGetUnreadedSystemInfoLongIntegerInteger() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetAllSystemInfo() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testReadASystemInfo() {
//		fail("Not yet implemented");
//	}

}

package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.entity.DeviceType;
import com.ebupt.justholdon.server.database.entity.NewestVersion;
import com.ebupt.justholdon.server.database.service.CheckInService;
import com.ebupt.justholdon.server.database.service.CommentService;
import com.ebupt.justholdon.server.database.service.EventService;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.NewestVersionService;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class NewestVersionServiceTest {
	static HabitService habitService;
	static UserService userService;
	static UserHabitService userHabitService;
	static CheckInService checkInService;
	static CommentService commentService;
	static EventService eventService;
	static NewestVersionService newestVersionService;
	static List<Integer> hids = new ArrayList<Integer>();
	static List<Long> uids = new ArrayList<Long>();
	static List<Integer> cids = new ArrayList<Integer>();
	static List<Integer> eventIds = new ArrayList<Integer>();
	static {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		habitService = (HabitService) ctx.getBean("habitService");
		userService = (UserService) ctx.getBean("userService");
		userHabitService = (UserHabitService) ctx.getBean("userHabitService");
		checkInService = (CheckInService) ctx.getBean("checkInService");
		commentService = (CommentService) ctx.getBean("commentService");
		eventService = (EventService) ctx.getBean("eventService");
		newestVersionService = (NewestVersionService) ctx.getBean("newestVersionService");
	}
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetNewestVersion() {
		versionService.
//		NewestVersion nw = newestVersionService.getNewestVersion(DeviceType.ANDRROID);
//		System.out.println("newest version "+nw.getType().toString()+" "+nw.getVersion().getVersion());
	}

}

package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.service.CheckInService;
import com.ebupt.justholdon.server.database.service.CommentService;
import com.ebupt.justholdon.server.database.service.EventService;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.NewestVersionService;
import com.ebupt.justholdon.server.database.service.RelationShipService;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class RelationShipServiceTest {
	static HabitService habitService;
	static UserService userService;
	static UserHabitService userHabitService;
	static CheckInService checkInService;
	static CommentService commentService;
	static EventService eventService;
	static NewestVersionService newestVersionService;
	static RelationShipService relationShipService;
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
		relationShipService = (RelationShipService) ctx.getBean("relationShipService");
	}
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBeFriend() {
		relationShipService.beFriend(1742757124L,1043421004L);
	}

	@Test
	public void testRemoveFriend() {
		relationShipService.removeFriend(1742757124L,1043421004L);
	}

	@Test
	public void testCountFriends() {
		System.out.println(relationShipService.countFriends(1742757124L));
	}

	@Test
	public void testIsFriend() {
		System.out.println(relationShipService.isFriend(3214532155L, 1742757124L));
		System.out.println(relationShipService.isFriend(1762687133L, 1742757124L));

	}

	@Test
	public void testFindFriendsLongIntegerIntegerBoolean() {
		List<User> users= relationShipService.findFriends(1742757124L);
		System.out.println("find friends");
		for(User user:users)
			System.out.println(user);
	}

	@Test
	public void testSearchFriendLongString() {
		System.out.println("search friends");

		List<User> users = relationShipService.searchFriend(1507035727L, "小");
		for(User user:users)
			System.out.println(user);
	}

}

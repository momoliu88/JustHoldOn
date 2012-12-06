package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserField;
import com.ebupt.justholdon.server.database.service.ComparatorType;
import com.ebupt.justholdon.server.database.service.UserFieldService;
import com.ebupt.justholdon.server.database.service.UserService;

public class UserServiceTest {
	UserFieldService service;
	UserService userService;
	List<Long> uids = new ArrayList<Long>();
	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		service = (UserFieldService) ctx.getBean("userFieldService");
		userService = (UserService) ctx.getBean("userService");

		UserField user = new UserField().setId(101L).setUserName("name")
				.setDeviceToken("device").setWeiboKey("123").setPassword("123")
				.setAvatar("ava");
		
		UserField user1 = new UserField().setId(102L).setUserName("name1")
				.setDeviceToken("device").setWeiboKey("123").setPassword("123")
				.setAvatar("ava");
		UserField user2 = new UserField().setId(103L).setUserName("name2")
				.setDeviceToken("device").setWeiboKey("123").setPassword("123")
				.setAvatar("ava");
		UserField user3 = new UserField().setId(104L).setUserName("name3")
				.setDeviceToken("device").setWeiboKey("123").setPassword("123")
				.setAvatar("ava");
		UserField user4 = new UserField().setId(105L).setUserName("name4")
				.setDeviceToken("device").setWeiboKey("123").setPassword("123")
				.setAvatar("ava");
		/*
		uids.add(service.createAndSave("username", "pass", 123L, "avatar", "device",
				"token"));
		uids.add(service.save(user));
		uids.add(service.save(user1));
		uids.add(service.save(user2));
		uids.add(service.save(user3));
		uids.add(service.save(user4));*/

	}

	@After
	public void tearDown() throws Exception {
//		for(Long id:uids)
//			service.delete(id);
	}

	@Test
	public void testCreateAndSave() {	
		 
	}
/*
	@Test
	public void testSave() {
		
	}
	
	@Test
	public void testFindFriends()
	{
		userService.beFriend(uids.get(0), uids.get(1));
		userService.beFriend(uids.get(0), uids.get(2));
		List<User> friends = userService.findFriends(uids.get(0), 0, 10);
		assertEquals(2,friends.size());
		assertEquals(1,friends.get(0).getFriends().size());
		userService.removeFriend(uids.get(0), uids.get(1));
		userService.removeFriend(uids.get(0), uids.get(2));

	}
	@Test
	public void testFindFriends_1()
	{
		userService.beFriend(uids.get(0), uids.get(1));
		userService.beFriend(uids.get(0), uids.get(2));
		List<User> friends = userService.findFriends(uids.get(0), 0, 1);
		assertEquals(1,friends.size());
		userService.removeFriend(uids.get(0), uids.get(1));
		userService.removeFriend(uids.get(0), uids.get(2));

	}
	@Test
	public void testUpdate()
	{
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("userName","new");
		 map.put("birthday", new Date());
		 service.update(uids.get(0),map);
		 User _user = userService.get(uids.get(0));
		 assertEquals("new",_user.getUserName());		 
	}
	private void beFriends()
	{
		userService.beFriend(uids.get(1), uids.get(0));
		userService.beFriend(uids.get(1), uids.get(2));
		userService.beFriend(uids.get(1), uids.get(3));
		userService.beFriend(uids.get(1), uids.get(4));
		userService.beFriend(uids.get(1), uids.get(5));
	//2 have 4 friends
		userService.beFriend(uids.get(2), uids.get(4));
		userService.beFriend(uids.get(2), uids.get(5));
		userService.beFriend(uids.get(2), uids.get(0));
		userService.beFriend(uids.get(3), uids.get(4));
	}
	private void removeFriends()
	{
		//1 have 5 friends
				userService.removeFriend(uids.get(1), uids.get(0));
				userService.removeFriend(uids.get(1), uids.get(2));
				userService.removeFriend(uids.get(1), uids.get(3));
				userService.removeFriend(uids.get(1), uids.get(4));
				userService.removeFriend(uids.get(1), uids.get(5));
			//2 have 4 friends
				userService.removeFriend(uids.get(2), uids.get(4));
				userService.removeFriend(uids.get(2), uids.get(5));
				userService.removeFriend(uids.get(2), uids.get(0));
				//3 have 2 friends,4 have 3friends,5 have 2friends,0 have 2 friends
				userService.removeFriend(uids.get(3), uids.get(4));
	}
	@Test
	public void testfindUsersByFriendsMost()
	{//1 have 5 friends
		beFriends();
		//3 have 2 friends,4 have 3friends,5 have 2friends,0 have 2 friends
		List<User> users = userService.findUsers(ComparatorType.MOSTFRIENDS);
		assertEquals(6,users.size());
		assertEquals(uids.get(1),users.get(0).getId());
		assertEquals(uids.get(2),users.get(1).getId());
		assertEquals(uids.get(4),users.get(2).getId());
		removeFriends();
	}
	@Test
	public void testfindUsersStartEnd()
	{
		beFriends();
		List<User> users = userService.findUsers(ComparatorType.MOSTFRIENDS,0,1);
		assertEquals(1,users.size());
		removeFriends();

	}
	@Test
	public void testsearchFriend()
	{
		beFriends();
		List<User> users = userService.searchFriend(uids.get(2), "user");
		assertEquals(1,users.size());
		removeFriends();
	}
	@Test
	public void testsearchFriendStartEnd()
	{
		beFriends();
		List<User> users = userService.searchFriend(uids.get(2), "4",0,2);
		assertEquals(1,users.size());
		for(User user:users)
			System.out.println(user.getId());
		removeFriends();
	}
	*/
}

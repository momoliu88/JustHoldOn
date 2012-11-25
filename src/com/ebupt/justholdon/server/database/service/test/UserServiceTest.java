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
		uids.add(service.createAndSave("username", "pass", 123L, "avatar", "device",
				"token"));
		uids.add(service.save(user));
		uids.add(service.save(user1));
	}

	@After
	public void tearDown() throws Exception {
		for(Long id:uids)
			service.delete(id);
	}

	@Test
	public void testCreateAndSave() {	
	}

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
}

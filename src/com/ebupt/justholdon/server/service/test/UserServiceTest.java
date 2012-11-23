package com.ebupt.justholdon.server.service.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.entity.UserField;
import com.ebupt.justholdon.server.service.UserFieldService;

public class UserServiceTest {
	UserFieldService service;

	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		service = (UserFieldService) ctx.getBean("userService");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateAndSave() {
		service.createAndSave("username", "pass", 123, "avatar", "device",
				"token");
		
	}

	@Test
	public void testSave() {
		UserField user = new UserField().setId(10).setUserName("name")
				.setDeviceToken("device").setWeiboKey("123").setPassword("123")
				.setAvatar("ava");
		service.save(user);
	}
	@Test
	public void testUpdate()
	{
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("userName","name");
		 map.put("birthday", new Date());
		 service.update(10,map);
	}
}

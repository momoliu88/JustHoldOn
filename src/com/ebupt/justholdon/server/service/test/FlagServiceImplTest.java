package com.ebupt.justholdon.server.service.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

 
import com.ebupt.justholdon.server.entity.Flag;
import com.ebupt.justholdon.server.entity.User;
import com.ebupt.justholdon.server.service.FlagService;
import com.ebupt.justholdon.server.service.UserFieldService;
import com.ebupt.justholdon.server.service.UserService;

public class FlagServiceImplTest {
	UserFieldService userFieldService;
	FlagService flagService;
	UserService userService;
	

	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		userFieldService = (UserFieldService) ctx.getBean("userFieldService");
		userService = (UserService) ctx.getBean("userService");
		flagService = (FlagService) ctx.getBean("flagService");
	User user1 = new User().setId(11).setUserName("name5").setAvatar("ava")
				.setPassword("pass").setToken("token").setDeviceToken("device");
		User user2 = new User().setId(12).setUserName("name7").setAvatar("ava")
				.setPassword("pass").setToken("token").setDeviceToken("device");
	
		Flag flag = new Flag().setContent("flag2");
		//User user1 = userService.get(123);
		//User user2 = userService.get(1263);
	//	flag.getUsers().add(user1);
	//	flag.getUsers().add(user2);
		userService.save(user2);
		userService.save(user1);
		
		Flag[] flags = { new Flag().setContent("flag1"),flag,new Flag().setContent("flag3") };
		for(Flag _flag:flags)
			flagService.save(_flag);
	//	user1.getFlags().add(flag);
	//	user2.getFlags().add(flag);
		//user1.setUserName("new name");
	//	userService.add(21,flag);
	//	userService.update(user1);
	//	userService.update(user2);
		flagService.addUser(user2, flag);
		flagService.addUser(user1, flag);
	
	 	flagService.removeUser(user1, flag);
		flagService.removeUser(user2, flag);
		flagService.delete(flag);
		userService.delete(user1);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindAllBoolean() {
		List<Flag> flags = flagService.findAll(true);
		System.out.println("len "+flags.size());
		for(Flag flag:flags)
		{
			System.out.println(flag);
		}
	}

}

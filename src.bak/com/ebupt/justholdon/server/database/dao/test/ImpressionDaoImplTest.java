package com.ebupt.justholdon.server.database.dao.test;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.dao.ImpressionDao;
import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.Impression;
import com.ebupt.justholdon.server.database.entity.User;

public class ImpressionDaoImplTest {
	ImpressionDao impressionDao;
	UserDao userDao;
	User userA;
	User userB;
	Impression impression;

	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		impressionDao = (ImpressionDao) ctx.getBean("impressionDao");
		userDao = (UserDao) ctx.getBean("userDao");
		String userName = "mytest";
		String password = "pass";
		String avatar = "avatar";
		String device = "device";
		long uid = 123;
		userA = new User(userName, password, avatar,uid,device);
		userB = new User("userB", "123", "avatar",uid,device);
		userDao.save(userA);
		userDao.save(userB);
		impression = new Impression().setContent("你是个好人")
				.setCreateTime(new Date()).setReceiver(userA).setSponsor(userB);
		userA.getSponseImpressiones().add(impression);
		userB.getReceivedImpressiones().add(impression);
		userDao.update(userA);
		userDao.update(userB);
	}

	@After
	public void tearDown() throws Exception {
		impression.setSponsor(null).setReceiver(null);
		userA.getSponseImpressiones().remove(impression);
		userB.getReceivedImpressiones().remove(impression);
		impressionDao.delete(impression);
		userDao.delete(userA);
		userDao.delete(userB);
	}

	@Test
	public void testRead() {
	}

}

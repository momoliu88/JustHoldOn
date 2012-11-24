package com.ebupt.justholdon.server.database.dao.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.ApproveDao;
import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.Approve;

public class ApproveDaoImplTest {
	UserDao userDao;
	ApproveDao dao ;
	@Before
	public void setUp() throws Exception {
		 ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
	     dao = (ApproveDao) ctx.getBean("approveDao");
	     userDao = (UserDao) ctx.getBean("userDao");

	}

	@After
	public void tearDown() throws Exception {
	}
	@Transactional
	@Test
	public void testFindByCriteria() {
		
		Approve approve = dao.get(1);
		System.out.println(approve.getUser().getUserName());
	}

}

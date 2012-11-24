package com.ebupt.justholdon.server.database.dao.test;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.dao.UserFieldDao;
import com.ebupt.justholdon.server.database.entity.BindType;
import com.ebupt.justholdon.server.database.entity.UserField;

public class UserFieldDaoImplTest {

	UserFieldDao userFieldDao;
	UserField userField;
	String userName = "username";

	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		userFieldDao = (UserFieldDao) ctx.getBean("userFieldDao");
		userField = new UserField();
		userField.setUserName(userName).setAvatar("avatar")
				.setBirthday(new Date()).setSocialBind(BindType.WEIBO)
				.setPassword("password").setDeviceToken("device").setWeiboKey("weibo").setId(123L);
	}

	@After
	public void tearDown() throws Exception {
	}
//insert into userField (area, avatar, birthday, cellphone, createTime, description, deviceToken, email, extends, modifyTime, money, password, renrenKey, sex, sinaKey, socialBind, uid, userLevel, userName, weiboKey)
	@Test
	public void testCreate() {
		Long id = userFieldDao.create(userField);
		assertTrue(id != -1);
		userFieldDao.delete(id);
	}

	@Test
	public void testRead() {
		Long id = userFieldDao.save(userField);
		UserField _userField = userFieldDao.read(id);
		assertEquals(userName, _userField.getUserName());
		userFieldDao.delete(id);
	}

	@Test
	public void testUpdate() {
		Long id = userFieldDao.save(userField);
		UserField _userField = userFieldDao.read(id);
		_userField.setUserLevel(3);
		userFieldDao.update(_userField);
		_userField = userFieldDao.read(id);
		assertEquals(3, _userField.getUserLevel());
		userFieldDao.delete(id);
	}

	@Test
	public void testDeleteT() {
		Long id = userFieldDao.save(userField);
		userFieldDao.delete(id);
	}

	@Test
	public void testDeletePK() {
		userFieldDao.save(userField);
		userFieldDao.delete(userField);
	}

	@Test
	public void testFindAll() {
		Long id2 = userFieldDao.save(userField);
		String newName = "another";
		userField.setUserLevel(4).setUserName(newName);
		Long id1 = userFieldDao.save(userField);
		List<UserField> userFields = userFieldDao.findAll();
		String[] names = { userName, newName };
		int i = 0;
		for (UserField field : userFields) {
			assertEquals(names[i], field.getUserName());
			i++;
		}
		userFieldDao.delete(id1);
		userFieldDao.delete(id2);
	}

}

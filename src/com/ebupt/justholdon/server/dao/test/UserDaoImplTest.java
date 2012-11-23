package com.ebupt.justholdon.server.dao.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.dao.UserDao;
import com.ebupt.justholdon.server.entity.User;

public class UserDaoImplTest {

	UserDao userDao ;
	String userName = "mytest";
	String password = "pass";
	String avatar = "avatar";
	String device = "device";
	int uid =123;
	User user ;
	@Before
	public void setUp() throws Exception {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
        userDao = (UserDao) ctx.getBean("userDao");
        user  = new User(userName,password,avatar,uid,device);
	}
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetFriends()
	{
        User usera = new User("new name",password,avatar,129,device);
		int id = userDao.save(user);
        
		usera.getFriends().add(user);
		int ida = userDao.save(usera);
        user.getFriends().add(usera);
        userDao.update(user);
        
        assertEquals(1,userDao.getFriends(ida));
        assertEquals(1,userDao.getFriends(id));

        user.getFriends().remove(usera);
        usera.getFriends().remove(user);
        userDao.update(user);
        userDao.update(usera);
        
        userDao.delete(user);
        userDao.delete(usera);
	}
	
	@Test
	public void testGet() {
		int id = userDao.save(user);
		User _user = userDao.get(id);
		
		assertTrue(user.getUserName().equals(_user.getUserName()));
		userDao.delete(id);
	}

	@Test
	public void testSave() {
		int id = userDao.save(user);
		System.out.println("save id "+id );
		userDao.delete(id);
	}

	@Test
	public void testUpdate() {
		Integer id = userDao.save(user);
		String newName = "new name";
		user.setUserName(newName);
		userDao.update(user);
		User _user = userDao.get(id);
		
		assertEquals(user.getUserName(),_user.getUserName());
		userDao.delete(id);
	}

	@Test
	public void testDeleteInt() {
		int id = userDao.save(user);
		userDao.delete(id);
	}

	@Test
	public void testDeleteUser() {
		userDao.save(user);
		userDao.delete(user);
	}

	@Test
	public void testFindByUserName() {
		userDao.save(user);
		List<User> users = userDao.findByUserName(userName);
		assertEquals(1,users.size());
		 
		assertTrue(user.equals(users.get(0)));
		

		userDao.delete(user);
		
	}

	@Test
	public void testFindAllUser() {
		userDao.save(user);
		String device = "device";
		User user2 = new User("my user2","123","avatar",1263,device);
		userDao.save(user2);
		List<User> users = userDao.findAll();
		assertTrue(Arrays.equals(new User[]{user,user2}, users.toArray()));
	//	userDao.delete(user);
	//	userDao.delete(user2);
	}

}

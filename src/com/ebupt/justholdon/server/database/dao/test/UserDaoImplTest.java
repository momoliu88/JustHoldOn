package com.ebupt.justholdon.server.database.dao.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.User;

public class UserDaoImplTest {

	UserDao userDao ;
	String userName = "李四";
	String password = "pass";
	String avatar = "avatar";
	String device = "device";
	long uid =124;
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
	/*
	@Test
	public void testGetFriends()
	{
        User usera = new User("张三",password,avatar,(long) 129,device);
		long id = userDao.save(user);
        
		usera.getFriends().add(user);
		long ida = userDao.save(usera);
        user.getFriends().add(usera);
        userDao.update(user);
        
        assertEquals(1,userDao.getFriends(ida));
        assertEquals(1,userDao.getFriends(id));

        user.getFriends().remove(usera);
        usera.getFriends().remove(user);
        userDao.update(user);
        userDao.update(usera);
        
     //   userDao.delete(user);
     //   userDao.delete(usera);
	}
*/
	@Test
	public void testGet() {
		long id = userDao.save(user);
		User _user = userDao.get(id);
		System.out.println(_user.getUserName());
		assertTrue(user.getUserName().equals(_user.getUserName()));
		userDao.delete(id);
	}
/*
	@Test
	public void testSave() {
		long id = userDao.save(user);
		System.out.println("save id "+id );
		userDao.delete(id);
	}

	@Test
	public void testUpdate() {
		Long id = userDao.save(user);
		String newName = "new name";
		user.setUserName(newName);
		userDao.update(user);
		User _user = userDao.get(id);
		
		assertEquals(user.getUserName(),_user.getUserName());
		userDao.delete(id);
	}

	@Test
	public void testDeleteInt() {
		long id = userDao.save(user);
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
		User user2 = new User("my user2","123","avatar",1263L,device);
		userDao.save(user2);
		List<User> users = userDao.findAll();
		assertTrue(Arrays.equals(new User[]{user,user2}, users.toArray()));
		userDao.delete(user);
		userDao.delete(user2);
	}
*/
}

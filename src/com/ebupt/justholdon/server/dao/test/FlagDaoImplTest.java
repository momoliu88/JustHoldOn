package com.ebupt.justholdon.server.dao.test;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.dao.FlagDao;
import com.ebupt.justholdon.server.dao.HabitDao;
import com.ebupt.justholdon.server.dao.UserDao;
import com.ebupt.justholdon.server.entity.Flag;
import com.ebupt.justholdon.server.entity.Habit;
import com.ebupt.justholdon.server.entity.User;

public class FlagDaoImplTest {
	UserDao userDao ;
	String userName = "mytest";
	String password = "pass";
	String avatar = "avatar";
	String device = "device";
 	User user ;
	HabitDao habitDao;
	FlagDao flagDao;
	Flag flag;
	Habit habit;
	Integer id;
	String habitname = "habitname";
	@Before
	public void setUp() throws Exception {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
        userDao = (UserDao) ctx.getBean("userDao");
        user  = new User(userName,password,avatar,123,device);
        flagDao = (FlagDao) ctx.getBean("flagDao");
        flag = new Flag().setContent("hi flag");
        Flag  flag2 = new Flag().setContent("hi flag");

        user.getFlags().add(flag);
        user.getFlags().add(flag2);
        flag2.getUsers().add(user);
        flag.getUsers().add(user);
		
       id = flagDao.save(flag);
       flagDao.save(flag2);

        userDao.save(user);
        user.setUserName("new");
        
        userDao.save(user);
        /*
		habitDao = (HabitDao) ctx.getBean("habitDao");
		habit = new Habit().
				setHabitName(habitname)
				.setUnit(PersistUnit.DAY)
				.setGroupName("groupName")
				.setType(HabitType.SYSTEM).setStages("{1,2,3}");
		habit.getFlags().add(flag);
		habitDao.save(habit);
		habit.setHabitName("new");
		habitDao.save(habit);*/
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() {
//		assertEquals(1,flagDao.getUserSize(id));
	}

}

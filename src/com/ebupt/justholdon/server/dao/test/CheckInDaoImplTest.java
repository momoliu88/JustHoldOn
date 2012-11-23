package com.ebupt.justholdon.server.dao.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.dao.CheckInDao;
import com.ebupt.justholdon.server.dao.HabitDao;
import com.ebupt.justholdon.server.dao.UserDao;
import com.ebupt.justholdon.server.entity.CheckIn;
import com.ebupt.justholdon.server.entity.Habit;
import com.ebupt.justholdon.server.entity.HabitType;
import com.ebupt.justholdon.server.entity.PersistUnit;
import com.ebupt.justholdon.server.entity.User;

public class CheckInDaoImplTest {
	CheckInDao checkInDao;
	UserDao userDao;
	HabitDao habitDao;
	Habit habit;
	User user;
	CheckIn checkIn;
	int id,habitId;

	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		checkInDao = (CheckInDao) ctx.getBean("checkinDao");
		userDao = (UserDao) ctx.getBean("userDao");
		habitDao = (HabitDao) ctx.getBean("habitDao");
		String userName = "mytest";
		String password = "pass";
		String avatar = "avatar";
		habit = new Habit().setHabitName("habitname").setUnit(PersistUnit.DAY)
				.setGroupName("groupName").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		user = new User(userName, password, avatar,123,"device");
		
		id =userDao.save(user);
		habitId =habitDao.save(habit);
		checkIn = new CheckIn().setCheckInTime(new Date()).setUser(user).setHabit(habit);
		user.getCheckIns().add(checkIn);
		habit.getCheckIns().add(checkIn);
		userDao.update(user);
		habitDao.update(habit);
		//checkInDao.save(checkIn);
	}

	@After
	public void tearDown() throws Exception {
		checkIn.setHabit(null).setUser(null);
		habit.getCheckIns().remove(checkIn);
		user.getCheckIns().remove(checkIn);
		checkInDao.delete(checkIn);
		userDao.delete(user);
		habitDao.delete(habit);
	}

	@Test
	public void testRead() {
		Criterion criterion = Restrictions.eq("user.id", id);
		List<CheckIn> checkIns = checkInDao.findByCriteria(criterion);
		assertEquals(checkIns.get(0).getHabit().getId(),habitId);
  	}

	@Test
	public void testUpdate() {
		Criterion criterion = Restrictions.eq("user.id", id);
		List<CheckIn> checkIns = checkInDao.findByCriteria(criterion);
		CheckIn _checkIn = checkIns.get(0);
		int _id = _checkIn.getId();
		_checkIn.setLocation("123");
		checkInDao.update(_checkIn);
		assertEquals("123",checkInDao.get(_id).getLocation());
		
		
 	}
/*
	@Test
	public void testFindByCriteria() {
 	}
*/
}

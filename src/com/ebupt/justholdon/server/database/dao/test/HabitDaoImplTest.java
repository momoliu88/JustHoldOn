package com.ebupt.justholdon.server.database.dao.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.dao.HabitDao;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.HabitType;
import com.ebupt.justholdon.server.database.entity.PersistUnit;

public class HabitDaoImplTest {
	HabitDao habitDao;
	Habit habit;
	Integer id;
	String habitname = "habitname";
	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		habitDao = (HabitDao) ctx.getBean("habitDao");
		habit = new Habit().
				setHabitName(habitname)
				.setUnit(PersistUnit.DAY)
				.setGroupName("groupName1")
				.setType(HabitType.SYSTEM).setStages("{1,2,3}");
		id = habitDao.save(habit);
	}

	@After
	public void tearDown() throws Exception {
		//habitDao.delete(id);
	}

	@Test
	public void testRead() {
		Habit _habit = habitDao.read(id);
		assertEquals(habitname,_habit.getHabitName());
	}

	@Test
	public void testUpdate() {
		Habit _habit = habitDao.read(id);
		_habit.setHabitName("new");
		habitDao.update(_habit);
		_habit = habitDao.read(id);
		assertEquals("new",_habit.getHabitName());
	}

}

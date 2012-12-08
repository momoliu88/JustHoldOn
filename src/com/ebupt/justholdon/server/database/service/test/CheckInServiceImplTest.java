package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.entity.CheckIn;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.HabitType;
import com.ebupt.justholdon.server.database.entity.PersistUnit;
import com.ebupt.justholdon.server.database.entity.PrivilegeType;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;
import com.ebupt.justholdon.server.database.service.CheckInService;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class CheckInServiceImplTest {
	static HabitService habitService;
	static UserService userService;
	static UserHabitService userHabitService;
	static CheckInService checkInService;
	static List<Integer> hids = new ArrayList<Integer>();
	static List<Long> uids = new ArrayList<Long>();
	static List<Integer> cids = new ArrayList<Integer>();
	static	{
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		habitService = (HabitService) ctx.getBean("habitService");
		userService = (UserService) ctx.getBean("userService");
		userHabitService = (UserHabitService) ctx.getBean("userHabitService");
		checkInService = (CheckInService) ctx.getBean("checkInService");
		Habit habit = new Habit().setHabitName("name1").setUnit(PersistUnit.DAY)
				.setGroupName("groupName1").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		Habit habit1 = new Habit().setHabitName("name2").setUnit(PersistUnit.DAY)
				.setGroupName("groupName1").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		User user1 = new User("user1", "password", "avatar", 787L, "device");
		hids.add(habitService.save(habit));
		hids.add(habitService.save(habit1));

		uids.add(userService.save(user1));
		
		UserHabit uHid = new UserHabit().setPrivilege(PrivilegeType.ALL);
		UserHabit uHid2 = new UserHabit().setPrivilege(PrivilegeType.ALL);
		userHabitService.connectUserHabit(uids.get(0), hids.get(1), uHid2);
		userHabitService.connectUserHabit(uids.get(0), hids.get(0), uHid);
		
	}
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		for(Integer cid:cids)
			checkInService.deleteCheckIn(cid);
		cids.clear();
	}

	@Test
	public void testCheckInLongInteger() {
		cids.add(checkInService.checkIn(uids.get(0), hids.get(0)));
		cids.add(checkInService.checkIn(uids.get(0), hids.get(0)));
		User user = userService.get(uids.get(0));
		assertEquals(2,user.getCheckIns().size());
	}

	@Test
	public void testCheckInCheckIn() {
		User user = userService.get(uids.get(0));
		CheckIn ck = new CheckIn().setUser(user)
				.setHabit(habitService.get(hids.get(0)));
		CheckIn ck1 = new CheckIn().setUser(user)
				.setHabit(habitService.get(hids.get(0)));
		CheckIn ck2 = new CheckIn().setUser(user)
				.setHabit(habitService.get(hids.get(0)));
//		cids.add(checkInService.checkIn(ck));
//		cids.add(checkInService.checkIn(ck1));
//		cids.add(checkInService.checkIn(ck2));
		assertEquals(3,user.getCheckIns().size());
		

	}

	@Test
	public void testGetCheckInsLongInteger() {
		User user = userService.get(uids.get(0));
		CheckIn ck1 = new CheckIn().setUser(user)
				.setHabit(habitService.get(hids.get(0)));
		CheckIn ck2 = new CheckIn().setUser(user)
				.setHabit(habitService.get(hids.get(0)));
		CheckIn ck3 = new CheckIn().setUser(user)
				.setHabit(habitService.get(hids.get(0)));
		CheckIn ck4 = new CheckIn().setUser(user)
				.setHabit(habitService.get(hids.get(1)));
//		cids.add(checkInService.checkIn(ck1));
//		cids.add(checkInService.checkIn(ck2));
//		cids.add(checkInService.checkIn(ck3));
//		cids.add(checkInService.checkIn(ck4));

		List<CheckIn> cksForHabit0 =checkInService.getCheckIns(uids.get(0), hids.get(0));
		List<CheckIn> cksForHabit1 =checkInService.getCheckIns(uids.get(0), hids.get(1));

		assertEquals(3,cksForHabit0.size());
		assertEquals(1,cksForHabit1.size());

	}
//
//	@Test
//	public void testGetCheckInNum() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetCheckInsLongIntegerIntegerInteger() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testGetCheckInInTimeRangeNum() {
		User user = userService.get(uids.get(0));
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 5, 10,0,0,0);
		CheckIn ck1 = new CheckIn().setUser(user)
				.setHabit(habitService.get(hids.get(0))).setCheckInTime(cal.getTime());
		cal.set(2012, 5,11,0,0,0);
		CheckIn ck2 = new CheckIn().setUser(user)
				.setHabit(habitService.get(hids.get(0))).setCheckInTime(cal.getTime());
		cal.set(2012, 5,11,0,0,0);
		CheckIn ck3 = new CheckIn().setUser(user)
				.setHabit(habitService.get(hids.get(0))).setCheckInTime(cal.getTime());
		cal.set(2012, 5,12,0,0,0);
		CheckIn ck4 = new CheckIn().setUser(user)
				.setHabit(habitService.get(hids.get(1))).setCheckInTime(cal.getTime());
//		cids.add(checkInService.checkIn(ck1));
//		cids.add(checkInService.checkIn(ck2));
//		cids.add(checkInService.checkIn(ck3));
//		cids.add(checkInService.checkIn(ck4));
		 cal.set(2012, 5, 10,5,0,0);
		Date start =cal.getTime();
		 cal.set(2012, 5, 11,0,0,0);

		Date end =cal.getTime();
		cal.set(2012,5,11,0,0,0);
		Date start1 = cal.getTime();
		cal.set(2012,5,12,0,0,0);
		Date end1 = cal.getTime();

		System.out.println("start "+start.toString()+" end "+end.toString());
		System.out.println("start1 "+start1.toString()+" end1 "+end1.toString());

		assertEquals(2,checkInService.getCheckInInTimeRangeNum(uids.get(0), hids.get(0), start, end));
		assertEquals(1,checkInService.getCheckInInTimeRangeNum(uids.get(0),hids.get(1), start1, end1));
	}

	@Test
	public void testGetCheckInInTimeRangeLongIntegerDateDate() {
		
	}
//
//	@Test
//	public void testGetCheckInInTimeRangeLongIntegerDateDateIntegerInteger() {
//		fail("Not yet implemented");
//	}

}

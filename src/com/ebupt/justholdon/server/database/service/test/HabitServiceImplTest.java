package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.Flag;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.HabitType;
import com.ebupt.justholdon.server.database.entity.PersistUnit;
import com.ebupt.justholdon.server.database.entity.Suggestion;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;
import com.ebupt.justholdon.server.database.service.HabitGroupName;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.HabitState;
import com.ebupt.justholdon.server.database.service.SuggestionService;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class HabitServiceImplTest {
	HabitService habitService;
	Habit habit;
	String password = "pass";
	String avatar = "avatar";
	String device = "device";
	Long uid = 123L;
	List<Integer> hids = new ArrayList<Integer>();
	UserHabitService userHabitService;
	SuggestionService suggestionService;
	UserDao userDao;
	User user;
	User user1;
	User user2;
	List<Long> uids = new ArrayList<Long>();
	UserService userService;
	UserHabit userHabit;
	UserHabit userHabit1;
	UserHabit userHabit2;
	UserHabit userHabit3;
	UserHabit userHabit4;
	Habit habit1;
	Habit habit2;
	IntegerationService service;
	private Logger logger = Logger.getLogger(this.getClass());
	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		habitService = (HabitService) ctx.getBean("habitService");
		userService = (UserService) ctx.getBean("userService");
		suggestionService = (SuggestionService) ctx.getBean("suggestionService");
		userHabitService = (UserHabitService) ctx.getBean("userHabitService");
		service = (IntegerationService) ctx.getBean("integerationService");
		habit = new Habit().setHabitName("name1").setUnit(PersistUnit.DAY)
				.setGroupName("groupName1").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		habit1 = new Habit().setHabitName("name2").setUnit(PersistUnit.DAY)
				.setGroupName("groupName1").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		habit2 = new Habit().setHabitName("name3").setUnit(PersistUnit.DAY)
				.setGroupName("groupName2").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
//		for(int i = 0 ;i<20;i++)
//		{
//			suggestionService.addSuggestion(1507035727L, "我是"+i+"条评论");
//			Thread.sleep(1000);
//		}
//
//		hids.add(habitService.save(habit));
//		hids.add(habitService.save(habit1));
//		hids.add(habitService.save(habit2));
//
//		user = new User("user0", password, avatar, uid, device);
//		user1 = new User("user1", password, avatar, 787L, device);
//		user2 = new User("user2", password, avatar, 188L, device);
//
//		uids.add(userService.save(user));
//		uids.add(userService.save(user1));
//		uids.add(userService.save(user2));
//
//		userService.beFriend(uids.get(1), uids.get(0));
//		userService.beFriend(uids.get(1), uids.get(2));
//		userHabit = new UserHabit();
//		userHabit1 = new UserHabit();
//		userHabit2 = new UserHabit();// .setHabit(habit2).setUser(user1);
//		userHabit3 = new UserHabit();// .setHabit(habit).setUser(user);
//		userHabit4 = new UserHabit();// .setHabit(habit).setUser(user);
//
//		userHabitService.connectUserHabit(uids.get(0), hids.get(0), userHabit);
//		userHabitService.connectUserHabit(uids.get(0), hids.get(1), userHabit1);
//		userHabitService.connectUserHabit(uids.get(0), hids.get(2), userHabit2);
//
//		User _user = userService.get(uids.get(0));
//
//		for (UserHabit _uH : _user.getUserHabits()) {
//			_uH.setModifyTime(new Date());
//			userHabitService.update(_uH);
//		}
//		userHabitService.connectUserHabit(uids.get(1), hids.get(1), userHabit3);
//		userHabitService.connectUserHabit(uids.get(2), hids.get(1), userHabit4);
//
	}

	@After
	public void tearDown() throws Exception {
//
//		userService.removeFriend(uids.get(1), uids.get(0));
//		userService.removeFriend(uids.get(1), uids.get(2));
//
//		userHabitService.updateState(uids.get(0), hids.get(0),HabitState.DELETED);
//		userHabitService.updateState(uids.get(0), hids.get(1),HabitState.DELETED);
//		userHabitService.updateState(uids.get(0), hids.get(2),HabitState.DELETED);
//		userHabitService.updateState(uids.get(1), hids.get(1),HabitState.DELETED);
//		userHabitService.updateState(uids.get(2), hids.get(1),HabitState.DELETED);
////
//		for (Long id : uids)
//			userService.delete(id);
//		for (Integer id : hids)
//			habitService.delete(id);

	}

	/*
	 * @Test public void test() { Map<String, Integer> map =
	 * habitService.groupNames(); for (Entry<String, Integer> entry :
	 * map.entrySet()) System.out.println(entry.getKey() + " => " +
	 * entry.getValue()); }
	 * 
	 * @Test public void testfindAGroup() { List<Habit> results =
	 * habitService.findAGroup("groupName1"); for(Habit result:results)
	 * System.out.println(result); }
	 */
	/*
	 * @Test public void testfindParticipateNum() { int num =
	 * habitService.findParticipateNum(hids.get(0)); assertEquals(1,num); }
	 * 
	 * @Test public void testfindParticipateNum_2() { int num =
	 * habitService.findParticipateNum(hids.get(1)); assertEquals(2, num); }
	 */
	
//	@Test
//	public void testfindParticipateAndFriends() {
//		List<User> users = habitService.findParticipateAndFriends(hids.get(1),
//				uids.get(1));
//		assertEquals(2, users.size());
//		for (User user : users)
//			System.out.println("USERNAME " + user.getUserName());
//	}
	Integer print(List<Habit> habits,Integer lastId){
		System.out.println("==================");
		System.out.println("lastid "+lastId);
		for(Habit habit:habits) {
			System.out.println(habit);
			lastId= habit.getId();
		}
		return lastId;
	}
	Long getTime(){
		return System.currentTimeMillis();
	}
	@Test
	public void testFindAll(){
		service.getAll();
	//	TestImpl.getAll();
//		Integer lastid = 0;
//		Long start = System.currentTimeMillis();
//		logger.debug("find all start");
//		List<Habit> habits = habitService.findAll(true,lastid,20,true);
//		Long current = System.currentTimeMillis();
//		logger.debug("find all end: "+(System.currentTimeMillis()-start));
//		System.out.println("gethabits "+(current-start));
//		Long uid =1671130932L;
//		for(Habit e:habits){
//			Integer habitId = e.getId();
//			Long s1 = System.currentTimeMillis();
//			UserHabit uh = userHabitService.getUserHabit(uid, habitId);
//			Long s2 = System.currentTimeMillis();
//			System.out.println("\tuserHabitService.getUserHabit(uid, habitId) "+(s2-s2));
//			int friendJoinNum=0;
//			if(uid !=null)
//			{
//				logger.debug("uid "+uid+" hid "+habitId);
//				Long s3= getTime();
//				 friendJoinNum = userHabitService.countNotDeletedUserHabits(uid, habitId);
//				 System.out.println("\tuserHabitService.countNotDeletedUserHabits "+(getTime()-s3));
//				 
//				 }
//			Long s4 = getTime();
////			habitService.findParticipateNum(habitId);
//			e.getActiveUserNum();
//			 System.out.println("\thabitService.findParticipateNum "+(getTime()-s4));
//
//			Set<String> strSet = new HashSet<String>();
//			for(Flag flag :habitService.getFlags(habitId)){
//				strSet.add(flag.getContent());
//			}
//		
//		}
//		System.out.println("in for "+ (System.currentTimeMillis()-current));
// 		habits = habitService.findAll(true,lastid,20,true);
//		System.out.println("gethabitlist time(ms) "+(System.currentTimeMillis()-start));
////

	}
//	@Test
//	public void testFindAGroup(){
//		String groupName = "学习";
//		List<Habit>habits = habitService.findAGroup(groupName, 0, 100, true);
//		for(Habit habit:habits) System.out.println(habit);
//	}
//	@Test
//	public void testGroupNames(){
//		Map<String,Integer> groups = habitService.groupNames();
//		System.out.println("group names");
//		for(Entry<String,Integer> entry:groups.entrySet())
//			System.out.println(entry.getKey()+ "\t"+entry.getValue());
//	}
//	@Test
//	public void testSearch(){
//		System.out.println("search");
//		List<Habit> habits = habitService.search("每天", 0, 10, true, true);
//		for(Habit habit:habits) System.out.println(habit);
//	}
//	@Test
//	public void testFindParticipateNum(){
//		System.out.println("find participate");
//		System.out.println(habitService.findParticipateNum(5));
//	}
//	@Test
//	public void testCountNotDelUserHabits(){
//		System.out.println("count not del");
//		System.out.println(habitService.countNotDeletedUserHabits(5));
//	}
//	@Test
//	public void testFlag(){
//		System.out.println("Flag");
//		Set<Flag> flags = habitService.getFlags(5);
//		for(Flag flag:flags)
//			System.out.println(flag);
//	}
//	@Test
//	public void testCount(){
//		
//		System.out.println("total:" +habitService.countHabits());
//	}
	/*

		//used
		public Set<Flag> getFlags(Integer hid);
		*/
		
	
	
	
}

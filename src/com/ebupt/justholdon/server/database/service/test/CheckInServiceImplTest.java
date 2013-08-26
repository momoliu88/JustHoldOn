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

import com.ebupt.justholdon.server.database.entity.Approve;
import com.ebupt.justholdon.server.database.entity.CheckIn;
import com.ebupt.justholdon.server.database.entity.Comment;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.HabitType;
import com.ebupt.justholdon.server.database.entity.PersistUnit;
import com.ebupt.justholdon.server.database.entity.PrivilegeType;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;
import com.ebupt.justholdon.server.database.service.ApproveService;
import com.ebupt.justholdon.server.database.service.CheckInService;
import com.ebupt.justholdon.server.database.service.CommentService;
import com.ebupt.justholdon.server.database.service.EventService;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class CheckInServiceImplTest {
	static HabitService habitService;
	static UserService userService;
	static UserHabitService userHabitService;
	static CheckInService checkInService;
	static EventService eventService;
	static CommentService commentService;
	static ApproveService approveService;
	static List<Integer> hids = new ArrayList<Integer>();
	static List<Long> uids = new ArrayList<Long>();
	static List<Integer> cids = new ArrayList<Integer>();
	static {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		habitService = (HabitService) ctx.getBean("habitService");
		userService = (UserService) ctx.getBean("userService");
		eventService = (EventService) ctx.getBean("eventService");
		userHabitService = (UserHabitService) ctx.getBean("userHabitService");
		checkInService = (CheckInService) ctx.getBean("checkInService");
		commentService = (CommentService) ctx.getBean("commentService");
		approveService = (ApproveService) ctx.getBean("approveService");
 

	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
		// for(Integer cid:cids)
		// checkInService.deleteCheckIn(cid);
		// cids.clear();
	}

	 
// 	@Test
//	public void test() {
//		Calendar cal = Calendar.getInstance();
//		cal.set(2013, 3,2, 0, 0, 0);
//		Date start = cal.getTime();
//		cal.set(2013, 3, 21, 0, 0, 0);
//		Date end = cal.getTime();
//		int num = checkInService.getCheckInInTimeRangeNum(1746516774L, 37,
//				start, end);
//		System.out.println("num " + num);
//		
//	}
//	private void print(List<CheckIn>rets){
//		for (CheckIn ret : rets) {
//			System.out.println("ck: " + ret.getId() + " "
//					+ ret.getCheckInTime());
//		}
//	}
//	@Test
//	public void testCheckInUidHid() {
//		Long uid = 1671130932L;
//		Integer hid = 78;
//		List<CheckIn> rets = checkInService.getCheckIns(uid, hid);
//		System.out.println(checkInService.getCheckInNum(uid, hid)+" following:");
//		print(rets);
//		
//		rets = checkInService.getCheckIns(uid, hid, 1021, 10, false);
//		System.out.println("checkInService.getCheckIns(uid, hid, 1021, 10, false);");
//		print(rets);
//		Calendar cal = Calendar.getInstance();
//		cal.set(2013, 3, 21, 8, 0, 0);
//		Date start = cal.getTime();
//		cal.set(2013, 3, 25, 10, 0, 0);
//
//		Date end = cal.getTime();
//		rets = checkInService.getCheckInInTimeRange(uid, hid, start, end);
//		System.out.println("checkInService.getCheckInInTimeRange(uid, hid, start, end);");
//
//		print(rets);
//	}
//	@Test
//	public void testGetCheckIns(){
//		Long uid = 1671130932L;
//		Long bewatched = 1762687133L;
//		Integer hid = 76;
//		List<CheckIn> rets = checkInService.getCheckIns(uid, bewatched, hid,973,5,true);
//		System.out.println("in testGetCheckIns");
//		print(rets);
//		List<Approve> apps = checkInService.getApproves(1108);
//		for(Approve app:apps)
//			System.out.println(app.getCreateTime());
//		List<Comment> comments = checkInService.getComments(1108);
//		for(Comment comment:comments)
//			System.out.println(comment.getCreateTime());
//
//	}
	@Test
	public void testCheckin(){
//		checkInService.checkInAndCreateEvent(1644982463L, 22, "content");
		Integer count = checkInService.getCheckInNum(1644982463L);
		System.out.println(count);
		Calendar cal = Calendar.getInstance();
		cal.set(2013,3,17,0,0,0);
		Date start = cal.getTime();
		cal.set(2013, 3,18,0,0,0);
		Date end = cal.getTime();
		List<CheckIn> cks = checkInService.getHotCheckins(1644982463L, start, end, 10);
		for(CheckIn ck:cks)
			System.out.println(ck.getId()+" "+(checkInService.countApproves(ck.getId())+checkInService.countComments(ck.getId())));
//		Map<String,>
	}
}

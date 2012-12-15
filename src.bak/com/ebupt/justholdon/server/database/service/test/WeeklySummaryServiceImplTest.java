package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.WeeklySummary;
import com.ebupt.justholdon.server.database.service.UserService;
import com.ebupt.justholdon.server.database.service.WeeklySummaryService;

public class WeeklySummaryServiceImplTest {
	WeeklySummaryService weeklySummaryService;
	UserService userService;
	List<Long> uids = new ArrayList<Long>();
	List<Integer> weeklySummaryIds =  new ArrayList<Integer>();
	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		weeklySummaryService = (WeeklySummaryService) ctx.getBean("weeklySummaryService");
		userService = (UserService) ctx.getBean("userService");
//		Long uid = 123L;
//		User user = new User("user0", "1233", "4563", uid, "DEVICE");
//		uids.add(userService.save(user));
//		weeklySummaryIds.add(weeklySummaryService.createWeeklySummary(uid, 100, 5, "SO BAD"));
//		weeklySummaryIds.add(weeklySummaryService.createWeeklySummary(uid, 100, 50, "NOT SO BAD"));
//		Calendar cal = Calendar.getInstance();
//		cal.set(2012, 6, 5);	
//		weeklySummaryIds.add(weeklySummaryService.createWeeklySummary(uid, 120, 100, cal.getTime(),"GOOD"));
		
	}

	@After
	public void tearDown() throws Exception {
	/*	
		for(Integer weeklySummaryId:weeklySummaryIds)
		{
			Long uid = uids.get(0);
			weeklySummaryService.deleteWeeklySummary(uid, weeklySummaryId);
		};
		for(Long uid:uids)
			userService.delete(uid);
	*/
	}

	@Test
	public void testGetFromDB() {
	//	fail("Not yet implemented");
	}

	@Test
	public void testCreateWeeklySummaryLongIntIntDateString() {
	//	fail("Not yet implemented");
	}

	@Test
	public void testGetUserWeeklySummaryLong() {
//		for(Long uid:uids)
//		{
			List<WeeklySummary> summaries = weeklySummaryService.getUserWeeklySummary(123L);
			assertEquals(3,summaries.size());
			for(WeeklySummary summary:summaries)
				System.out.println("#"+summary.getCreateTime()+" "+summary.getComment());
//		}
	}

	@Test
	public void testGetUserWeeklySummaryLongIntegerInteger() {
//		for(Long uid:uids)
//		{
//			List<WeeklySummary> summaries = weeklySummaryService.getUserWeeklySummary(uid,0,1);
//			assertEquals(1,summaries.size());
//		}
	}

}

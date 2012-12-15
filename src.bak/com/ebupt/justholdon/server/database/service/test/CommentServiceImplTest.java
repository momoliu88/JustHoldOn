package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.entity.Comment;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.HabitType;
import com.ebupt.justholdon.server.database.entity.PersistUnit;
import com.ebupt.justholdon.server.database.entity.PrivilegeType;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;
import com.ebupt.justholdon.server.database.service.CheckInService;
import com.ebupt.justholdon.server.database.service.CommentService;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class CommentServiceImplTest {
	static HabitService habitService;
	static UserService userService;
	static UserHabitService userHabitService;
	static CheckInService checkInService;
	static CommentService commentService;
	static List<Integer> hids = new ArrayList<Integer>();
	static List<Long> uids = new ArrayList<Long>();
	static List<Integer> cids = new ArrayList<Integer>();
	static List<Integer> commentIds = new ArrayList<Integer>();
	static	{
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		habitService = (HabitService) ctx.getBean("habitService");
		userService = (UserService) ctx.getBean("userService");
		userHabitService = (UserHabitService) ctx.getBean("userHabitService");
		checkInService = (CheckInService) ctx.getBean("checkInService");
		commentService = (CommentService) ctx.getBean("commentService");
		Habit habit = new Habit().setHabitName("name1").setUnit(PersistUnit.DAY)
				.setGroupName("groupName1").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		Habit habit1 = new Habit().setHabitName("name2").setUnit(PersistUnit.DAY)
				.setGroupName("groupName1").setType(HabitType.SYSTEM)
				.setStages("{1,2,3}");
		User user1 = new User("user1", "password", "avatar", 787L, "device");
		User user2 = new User("user2", "password", "avatar", 789L, "device");

		hids.add(habitService.save(habit));
		hids.add(habitService.save(habit1));

		uids.add(userService.save(user1));
		uids.add(userService.save(user2));

		UserHabit uHid = new UserHabit().setPrivilege(PrivilegeType.ALL);
		UserHabit uHid2 = new UserHabit().setPrivilege(PrivilegeType.ALL);
		userHabitService.connectUserHabit(uids.get(0), hids.get(1), uHid2);
		userHabitService.connectUserHabit(uids.get(0), hids.get(0), uHid);
		
		cids.add(checkInService.checkIn(uids.get(0), hids.get(0)));
		cids.add(checkInService.checkIn(uids.get(0), hids.get(1)));
		
	}
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		for(Integer commentId: commentIds)
			commentService.deleteComment(commentId);
		commentIds.clear();
	}

	@Test
	public void testCreateCommentLongLongIntegerString() {
		//a comment for a checkin
		commentIds.add(	commentService.createComment(uids.get(1), null, cids.get(0), "hello"));
		commentIds.add(commentService.createComment(uids.get(0), uids.get(1), cids.get(0), "ok reply"));

		User user1 = userService.get(uids.get(0));
		User user2 = userService.get(uids.get(1));
		assertEquals(0,user1.getReceiverComments().size());
		assertEquals(1,user2.getSponsorComments().size());
		assertEquals(1,user2.getReceiverComments().size());
		assertEquals(2,checkInService.get(cids.get(0)).getComments().size());
	}

//	@Test
//	public void testCreateCommentLongLongIntegerDateString() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testGetCommentsForCheckinInteger() {
		commentIds.add(	commentService.createComment(uids.get(1), null, cids.get(0), "hello"));
		commentIds.add(commentService.createComment(uids.get(0), uids.get(1), cids.get(0), "ok reply"));
		commentIds.add(commentService.createComment(uids.get(1), uids.get(0), cids.get(0), "ok reply reply"));
		List<Comment> comments = commentService.getCommentsForCheckin(cids.get(0));
		assertEquals(3,comments.size());
		for(Comment comment:comments)
			System.out.println("#"+comment.getSponsor().getId());
	}
//
//	@Test
//	public void testGetCommentsForCheckinIntegerIntegerInteger() {
//		fail("Not yet implemented");
//	}

}

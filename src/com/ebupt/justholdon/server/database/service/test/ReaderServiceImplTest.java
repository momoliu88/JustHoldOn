package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

 
import com.ebupt.justholdon.server.database.entity.Book;
import com.ebupt.justholdon.server.database.entity.ReadStat;
import com.ebupt.justholdon.server.database.entity.Reader;
import com.ebupt.justholdon.server.database.service.ApproveService;
import com.ebupt.justholdon.server.database.service.BookService;
import com.ebupt.justholdon.server.database.service.CheckInService;
import com.ebupt.justholdon.server.database.service.CommentService;
import com.ebupt.justholdon.server.database.service.EventService;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.ReaderService;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class ReaderServiceImplTest {
	static HabitService habitService;
	static UserService userService;
	static UserHabitService userHabitService;
	static CheckInService checkInService;
	static EventService eventService;
	static CommentService commentService;
	static ApproveService approveService;
	static BookService bookService;
	static ReaderService readerService;
	static {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		habitService = (HabitService) ctx.getBean("habitService");
		userService = (UserService) ctx.getBean("userService");
		eventService = (EventService) ctx.getBean("eventService");
		userHabitService = (UserHabitService) ctx.getBean("userHabitService");
		checkInService = (CheckInService) ctx.getBean("checkInService");
		commentService = (CommentService) ctx.getBean("commentService");
		approveService = (ApproveService) ctx.getBean("approveService");
		bookService = (BookService) ctx.getBean("bookService");
		readerService = (ReaderService) ctx.getBean("readerService");
	}
	static Integer id = 0;
	static Long uid = 3297527954L;
	static List<Integer> ids = new ArrayList<Integer>();
	@Before
	public void setUp() throws Exception {
		id = readerService.read(uid,1);
		ids.add(readerService.read(1507035727L, 1));
		Thread.sleep(2000);
		ids.add(readerService.read(uid, 2));
		Thread.sleep(2000);
		ids.add(readerService.read(uid, 3));

		ids.add(readerService.read(1707695242L, 1));
		ids.add(readerService.read(1893424284L,1));
		ids.add(id);
		
	}

	@After
	public void tearDown() throws Exception {
//		for(Integer id:ids)
//		readerService.delete(id);
//		ids.clear();
	}
//	@Test
//	public void testRead() {
//		//readerService.read(uid,1);
//		System.out.println("testRead...");
//		Reader reader = readerService.get(id);
//		System.out.println(reader);
//	}
//	private void print(){
//		Reader reader = readerService.get(id);
//		System.out.println(reader);
//	}
//	@Test
//	public void testUpdateState() {
//		System.out.println("testUpdateState...");
//		System.out.println("before...");
//		print();
//		readerService.updateState(uid, 1, ReadStat.READED);
//		System.out.println("after...");
//		print();
//	}
//
//	@Test
//	public void testUpdatePage() {
//		System.out.println("testUpdatePage...");
//		System.out.println("before...");
//		print();
//		readerService.updatePage(uid, 1, 10);
//		System.out.println("after...");
//		print();
//	}
//
//	@Test
//	public void testGetFriendNum() {
//		System.out.println("testGetFriendNum...");
//		Integer nums = readerService.getFriendNum(uid, 1);
//		System.out.println("friends read book: "+nums);
//	}
//
//	@Test
//	public void testGetReaders() {
//		System.out.println("testGetReaders...");
//		Integer nums =readerService.getReaders(1);
//		System.out.println("people read book: "+nums);
//
//	}

	@Test
	public void testGetBooks() {
		System.out.println("testGetBooks...");

		List<Book> books = readerService.getBooks(uid,3,10,true);
		System.out.println(books.size());
	}

//	@Test
//	public void testGetReadStat() {
//		System.out.println("testGetReadStat...");
//		Reader reader = readerService.getReader(3297527954L, 1);
//		System.out.println(reader);
//	}

}

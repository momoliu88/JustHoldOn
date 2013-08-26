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
import com.ebupt.justholdon.server.database.service.ApproveService;
import com.ebupt.justholdon.server.database.service.BookService;
import com.ebupt.justholdon.server.database.service.CheckInService;
import com.ebupt.justholdon.server.database.service.CommentService;
import com.ebupt.justholdon.server.database.service.EventService;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class BookServiceImplTest {
	static HabitService habitService;
	static UserService userService;
	static UserHabitService userHabitService;
	static CheckInService checkInService;
	static EventService eventService;
	static CommentService commentService;
	static ApproveService approveService;
	static BookService bookService;

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

	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateBook() {
		Book book = new Book();
		book.setAuthor("刘晓琴").setCoverAddr("123").setIntroduction("8889").setIsbn("1-2-3-1").setName("test");
		bookService.createBook(book);
	}

	@Test
	public void testIsAdded() {
		boolean isAdded = bookService.isAdded("1-2-3", null);
		System.out.println("isadded? "+isAdded);
		isAdded = bookService.isAdded(null,"test");
		System.out.println("isadded? "+isAdded);
		isAdded = bookService.isAdded(null,"test1");
		System.out.println("isadded? "+isAdded);

	}

}

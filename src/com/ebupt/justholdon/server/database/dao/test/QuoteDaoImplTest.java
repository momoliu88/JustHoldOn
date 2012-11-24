package com.ebupt.justholdon.server.database.dao.test;

import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.dao.QuoteDao;
import com.ebupt.justholdon.server.database.entity.Quote;

public class QuoteDaoImplTest {

	Quote quote;
	QuoteDao quoteDao;
	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		quoteDao = (QuoteDao) ctx.getBean("quoteDao");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRead() throws UnsupportedEncodingException {
		quote = quoteDao.read(774);
	//	System.out.print(new String (quote.getContent().getBytes("UTF8"),"UTF8") + " "); 
		System.out.println(quote.getContent());
 	}
}

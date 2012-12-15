package com.ebupt.justholdon.server.database.data.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ebupt.justholdon.server.database.entity.User;

public class UserTest {

	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEqualsObject() {
		User user = new User("name","pass","ava",123l,"device");
		User user2 = new User("name","pass","ava",124l,"device");
		assertTrue(user.equals(user2));
	}

}

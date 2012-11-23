package com.ebupt.justholdon.server.dao;

import java.util.List;
 
import com.ebupt.justholdon.server.entity.User;

public interface UserDao extends GenericHibernateDao<User, Integer>{
	
	List<User> findByUserName(String name);
	int getFriends(Integer id );
}

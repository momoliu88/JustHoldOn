package com.ebupt.justholdon.server.database.dao;

import java.util.List;
 
import com.ebupt.justholdon.server.database.entity.User;

public interface UserDao extends GenericHibernateDao<User, Long>{
	
	List<User> findByUserName(String name);
	int getFriends(Long id );
}

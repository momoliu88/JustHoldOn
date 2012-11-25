package com.ebupt.justholdon.server.database.service;

import java.util.List;

import com.ebupt.justholdon.server.database.entity.User;

public interface UserService extends GenericService<User,Long>{
	public boolean beFriend(Long id1,Long id2);

	public boolean removeFriend(Long user1,Long user2);
	
	public boolean isValideUser(Long uid,String password);
	
	public List<User> findFriends(Long uid,Integer start,Integer end);

}

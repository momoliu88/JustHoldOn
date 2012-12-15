package com.ebupt.justholdon.server.database.service;

import java.util.List;

import com.ebupt.justholdon.server.database.entity.User;

public interface UserService extends GenericService<User,Long>{
	public boolean beFriend(Long id1,Long id2);

	public boolean removeFriend(Long user1,Long user2);
	
	public boolean isValideUser(Long uid,String password);
	public boolean isFriend(Long uidA,Long uidB);
	//use uid to find this user's friends
	public List<User> findFriends(Long uid,Integer startId,Integer length,boolean after);
	public List<User> findFriends(Long uid);

	//find all users in this app
	public List<User> findUsers(ComparatorType type);

	public List<User> findUsers(ComparatorType type,Integer startId,Integer length,boolean after);

	/* search friends*/
	public List<User> searchFriend(Long uid,String key);
	/* search friends*/
	public List<User> searchFriend(Long uid,String key,Integer startId,Integer length,boolean after);
}

package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.User;

public interface UserService extends GenericService<User,Long>{
	public void beFriend(User user1,User user2);
	public void beFriend(Long user1,Long user2);

	public void removeFriend(User user1,User user2);
	public void removeFriend(Long user1,Long user2);
	
	public boolean isValideUser(Long uid,String password);

}

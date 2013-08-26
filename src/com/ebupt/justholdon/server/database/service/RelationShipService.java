package com.ebupt.justholdon.server.database.service;

import java.util.List;
//import java.util.Set;

import com.ebupt.justholdon.server.database.entity.RelationShip;
import com.ebupt.justholdon.server.database.entity.User;

public interface RelationShipService extends
		GenericService<RelationShip, Integer> {
	// used
	public boolean beFriend(Long id1, Long id2);

	public boolean removeFriend(Long user1, Long user2);

//	public Set<User> getFriends(Long userId);

	// used
	public Integer countFriends(Long uid);

	// used
	public boolean isFriend(Long uidA, Long uidB);

	// used
	public List<User> findFriends(Long uid, Integer startId, Integer length,
			boolean after);

	public List<User> findFriends(Long uid);

	/* search friends */
	public List<User> searchFriend(Long uid, String key);

	/* search friends */
	public List<User> searchFriend(Long uid, String key, Integer startId,
			Integer length, boolean after);
}

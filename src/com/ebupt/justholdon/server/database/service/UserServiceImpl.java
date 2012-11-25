package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public User get(Long id) {
		return userDao.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	@Transactional(readOnly = false)
	public Long save(User user) {
		return userDao.save(user);
	}

	//
	// @Override
	// public User load(Long id) {
	// return userDao.load(id);
	// }

	@Override
	public int update(Long id, Map<String, Object> infos) {
		// TODO Auto-generated method stub
		return -1;
	}

	// @Override
	// public void delete(User persistentObject) {
	// userDao.delete(persistentObject);
	// }

	@Override
	public void delete(Long id) {
		userDao.delete(id);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	public boolean beFriend(User user1, User user2) {
		if (null == user1 || null == user2)
			return false;
		if (user1.getFriends().contains(user2)
				&& user2.getFriends().contains(user1))
			return true;
		user1.getFriends().add(user2);
		user2.getFriends().add(user1);
		userDao.update(user1);
		userDao.update(user2);
		return true;
	}

	public boolean removeFriend(User user1, User user2) {
		if (null == user1 || null == user2)
			return false;
		if (!user1.getFriends().contains(user2)
				&& !user2.getFriends().contains(user1))
			return true;
		user1.getFriends().remove(user2);
		user2.getFriends().remove(user1);
		userDao.update(user2);
		userDao.update(user1);
		return true;
	}

	@Override
	public boolean beFriend(Long user1, Long user2) {
		return beFriend(userDao.get(user1), userDao.get(user2));
	}

	@Override
	public boolean removeFriend(Long user1, Long user2) {
		return removeFriend(userDao.get(user2), userDao.get(user1));
	}

	@Override
	public boolean isValideUser(Long uid, String password) {
		User user = userDao.get(uid);
		if (null == user)
			return false;
		return user.getPassword().equals(password);
	}

	@Override
	public List<User> findFriends(Long uid, Integer start, Integer end) {
		User user = userDao.get(uid);
		List<User> friends = new ArrayList<User>(user.getFriends());
		return Utils.subList(start, end, friends);
	}

}

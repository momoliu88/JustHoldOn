package com.ebupt.justholdon.server.database.service;

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

	@Override
	public User load(Long id) {
		return userDao.load(id);
	}

	@Override
	public int update(Long id, Map<String, Object> infos) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void delete(User persistentObject) {
		userDao.delete(persistentObject);
	}

	@Override
	public void delete(Long id) {
		userDao.delete(id);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public void beFriend(User user1, User user2) {
		user1.getFriends().add(user2);
		user2.getFriends().add(user1);
		userDao.update(user1);
		userDao.update(user2);
	}

	@Override
	public void removeFriend(User user1, User user2) {
		user1.getFriends().remove(user2);
		user2.getFriends().remove(user1);
		userDao.update(user2);
		userDao.update(user1);
	}

	@Override
	public void beFriend(Long user1, Long user2) {
		beFriend(userDao.get(user1), userDao.get(user2));
	}

	@Override
	public void removeFriend(Long user1, Long user2) {
		removeFriend(userDao.get(user2), userDao.get(user1));
	}

	@Override
	public boolean isValideUser(Long uid, String password) {
		User user = userDao.get(uid);
		if (null == user)
			return false;
		return user.getPassword().equals(password);
	}

}

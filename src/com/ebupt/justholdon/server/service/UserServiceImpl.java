package com.ebupt.justholdon.server.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.dao.UserDao;
import com.ebupt.justholdon.server.entity.User;
@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	@Override
	public User get(Integer id) {
		return userDao.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(User user) {
		userDao.update(user);
	}

	
	@Override
	@Transactional(readOnly = false)
	public Integer save(User user) {
		return userDao.save(user);
	}

	@Override
	public User load(Integer id) {
		return userDao.load(id);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void delete(User persistentObject)  {
		userDao.delete(persistentObject);
	}

	@Override
	public void delete(Integer id) {
		userDao.delete(id);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}


}

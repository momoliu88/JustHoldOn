package com.ebupt.justholdon.server.database.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.UserFieldDao;
import com.ebupt.justholdon.server.database.entity.UserField;

@Service("userFieldService")
@Transactional
public class UserFieldServiceImpl implements UserFieldService {
	public UserFieldServiceImpl() {
	};

	@Autowired
	private UserFieldDao userFieldDao;
	

	public UserFieldDao getUserFieldDao() {
		return userFieldDao;
	}

	public void setUserFieldDao(UserFieldDao userFieldDao) {
		this.userFieldDao = userFieldDao;
	}

	@Override
	@Transactional(readOnly = false)
	public Long createAndSave(String userName, String pass, Long uid,
			String avatar, String deviceToken, String token) {
		UserField userField = new UserField().setId(uid).setUserName(userName)
				.setAvatar(avatar).setDeviceToken(deviceToken)
				.setPassword(pass).setWeiboKey(token);
		return userFieldDao.save(userField);
	}

	@Override
	@Transactional(readOnly = false)
	public Long save(UserField userField) {
		return userFieldDao.save(userField);
	}

	@Override
	@Transactional(readOnly = false)
	public int update(Long uid, Map<String, Object> info) {
		return userFieldDao.update(uid, info);
	}

	@Override
	public UserField get(Long id) {
		return userFieldDao.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(UserField transientObject) {
		userFieldDao.update(transientObject);
	}

//	
//	@Override
//	public void delete(UserField persistentObject) {
//		userFieldDao.delete(persistentObject);
//	}

	@Override
	public void delete(Long id) {
		userFieldDao.delete(id);
	}

	@Override
	public List<UserField> findAll() {
		return userFieldDao.findAll();
	}
//
//	@Override
//	public UserField load(Long id) {
//		return userFieldDao.load(id);
//	}

}

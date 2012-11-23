package com.ebupt.justholdon.server.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.dao.FlagDao;
import com.ebupt.justholdon.server.dao.UserDao;
import com.ebupt.justholdon.server.entity.Flag;
import com.ebupt.justholdon.server.entity.User;

@Service("flagService")
@Transactional(readOnly = true)
public class FlagServiceImpl implements FlagService {
	public FlagServiceImpl() {
	};

	@Autowired
	private FlagDao flagDao;
	@Autowired
	private UserDao userDao;
	private Comparator<Flag> comparator = new Comparator<Flag>() {
		@Override
		public int compare(Flag flag1, Flag flag2) {
			return flag2.getUsers().size() - flag1.getUsers().size();
		}
	};
	@Override
	public List<Flag> findAll(boolean byHot) {
		List<Flag> list = findAll();
		if (byHot)
			Collections.sort(list, comparator);
		return list;
	}

	public List<Flag> findAll() {
		return flagDao.findAll();
	}

	public FlagDao getFlagDao() {
		return flagDao;
	}

	public void setFlagDao(FlagDao flagDao) {
		this.flagDao = flagDao;
	}

	@Override
	@Transactional(readOnly = false)
	public Integer save(Flag newInstance) {
		return flagDao.save(newInstance);
	}

	@Override
	public Flag get(Integer id) {
		return flagDao.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(Flag transientObject) {
		flagDao.update(transientObject);
	}

	@Override
	@Transactional(readOnly = false)
	public int update(Integer id, Map<String, Object> infos) {
		return flagDao.update(id, infos);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Flag persistentObject) {
		flagDao.delete(persistentObject);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Integer id) {
		flagDao.delete(id);
	}

	@Override
	public Flag load(Integer id) {
		return flagDao.load(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void addUser(User user,Flag flag) {
		flag.getUsers().add(user);
		user.getFlags().add(flag);
		flagDao.update(flag);
		userDao.update(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void removeUser(User user, Flag flag) {
		flag.getUsers().remove(user);
		user.getFlags().remove(flag);
		flagDao.update(flag);
		userDao.update(user);
		
	}

}

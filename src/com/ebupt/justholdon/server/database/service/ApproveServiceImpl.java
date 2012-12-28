package com.ebupt.justholdon.server.database.service;

import java.util.List;
import java.util.Set;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.ApproveDao;
import com.ebupt.justholdon.server.database.dao.CheckInDao;
import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.Approve;
import com.ebupt.justholdon.server.database.entity.CheckIn;
import com.ebupt.justholdon.server.database.entity.User;

@Service("approveService")
@Transactional
public class ApproveServiceImpl implements ApproveService {
	
	@Autowired
	private ApproveDao approveDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CheckInDao checkInDao;
	
	@Override
	public Integer save(Approve newInstance) {
		return approveDao.save(newInstance);
	}

	@Override
	public Approve get(Integer id) {
		return approveDao.get(id);
	}

	@Override
	public void update(Approve transientObject) {
		approveDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return approveDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
		Approve approve = get(id);
		approve.getCheckin().getApproves().remove(approve);
		approve.getUser().getApproves().remove(approve);
		approve.setCheckin(null);
		approve.setUser(null);
		approveDao.delete(approve);
	}

	@Override
	public List<Approve> findAll() {
		return approveDao.findAll();
	}

	@Override
	public Integer approveCheckIn(Long uid, Integer checkInId) {
		User user = userDao.get(uid);
		CheckIn ck = checkInDao.get(checkInId);
		Approve approve = new Approve().setCheckin(ck).setUser(user); 
		return approveDao.save(approve);
	}

	@Override
	public boolean isApproved(Long uid, Integer checkInId) {
		CheckIn ck = checkInDao.get(checkInId);
		Set<Approve> approves =  ck.getApproves();
		for(Approve approve:approves)
			if(approve.getUser().getId().equals(uid))
				return true;
		return false;
	}
}

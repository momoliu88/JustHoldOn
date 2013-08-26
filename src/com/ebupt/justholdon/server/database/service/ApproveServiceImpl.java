package com.ebupt.justholdon.server.database.service;

import java.util.List;
//import java.util.Set;

import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.ApproveDao;
import com.ebupt.justholdon.server.database.entity.Approve;
import com.ebupt.justholdon.server.database.entity.CheckIn;
import com.ebupt.justholdon.server.database.entity.User;

@Service("approveService")
@Transactional
public class ApproveServiceImpl implements ApproveService {

	@Autowired
	private ApproveDao approveDao;
	@Autowired
	private UserService userService;
	@Autowired
	private CheckInService checkInService;

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
		//approveDao.update(approve);
	}

	@Override
	public List<Approve> findAll() {
		return approveDao.findAll();
	}

	@Override
	public Integer approveCheckIn(Long uid, Integer checkInId) {
		User user = userService.get(uid);
		CheckIn ck = checkInService.get(checkInId);
		Approve approve = new Approve().setCheckin(ck).setUser(user);
		return approveDao.save(approve);
	}

	@Override
	public boolean isApproved(Long uid, Integer checkInId) {
		Criterion criterion[] = { Restrictions.eq("user", userService.get(uid)),
				Restrictions.eq("checkin", checkInService.get(checkInId)) };
		List<Approve> approves = approveDao.findByCriteria(criterion);
		return (null != approves) && (approves.size() > 0);
	}

	@Override
	public void saveOrUpdate(Approve transientObject) {
		approveDao.saveOrUpdate(transientObject);		
	}
}

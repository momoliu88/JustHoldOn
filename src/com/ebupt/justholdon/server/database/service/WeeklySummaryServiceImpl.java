package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.dao.WeeklySummaryDao;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.WeeklySummary;

@Service("weeklySummaryService")
@Transactional
public class WeeklySummaryServiceImpl implements WeeklySummaryService {
	@Autowired
	private WeeklySummaryDao weeklySummaryDao;
	@Autowired
	private UserDao userDao;

	@Override
	public Integer save(WeeklySummary newInstance) {
		return weeklySummaryDao.save(newInstance);
	}

	@Override
	public WeeklySummary get(Integer id) {
		return weeklySummaryDao.get(id);
	}

	@Override
	public void update(WeeklySummary transientObject) {
		weeklySummaryDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return weeklySummaryDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
		weeklySummaryDao.delete(id);
	}

	@Override
	public List<WeeklySummary> findAll() {
		return weeklySummaryDao.findAll();
	}

	@Override
	public Integer createWeeklySummary(Long uid, int goalCheckInTimes,
			int actualCheckInTimes, String comment) {
		return createWeeklySummary(uid,goalCheckInTimes,actualCheckInTimes,null,comment);
	}

	@Override
	public Integer createWeeklySummary(Long uid, int goalCheckInTimes,
			int actualCheckInTimes, Date createTime, String comment) {
		User user = userDao.get(uid);
//		if (null == user)
//			throw new java.lang.NullPointerException("can't get user from "
//					+ uid);
		WeeklySummary weeklySummary = new WeeklySummary()
				.setActualCheckInTimes(actualCheckInTimes).setComment(comment)
				.setGoalCheckInTimes(goalCheckInTimes).setUser(user);
		if(null != createTime)
			weeklySummary.setCreateTime(createTime);
		return weeklySummaryDao.save(weeklySummary);
	}

	@Override
	public List<WeeklySummary> getUserWeeklySummary(Long uid) {
		User user = userDao.get(uid);
//		if(null == user)
//			throw new java.lang.NullPointerException("can't get user from "+uid);
 		Set<WeeklySummary> summaries = user.getWeeklySummaries();
 		List<WeeklySummary> result = new ArrayList<WeeklySummary>(summaries);
 		Collections.sort(result,WeeklySummary.getDateComparator());
		return result;
	}

	@Override
	public List<WeeklySummary> getUserWeeklySummary(Long uid, Integer start,
			Integer end) {
		return Utils.subList(start, end, getUserWeeklySummary(uid));
	}

}

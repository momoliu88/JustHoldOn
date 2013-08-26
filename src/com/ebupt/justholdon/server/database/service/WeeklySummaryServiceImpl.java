package com.ebupt.justholdon.server.database.service;

//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
//import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.dao.WeeklySummaryDao;
//import com.ebupt.justholdon.server.database.entity.GenericComparator;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.WeeklySummary;

@Service("weeklySummaryService")
@Transactional
public class WeeklySummaryServiceImpl implements WeeklySummaryService {
	@Autowired
	private WeeklySummaryDao weeklySummaryDao;
	@Autowired
	private UserService userService;
//	@SuppressWarnings("unchecked")
//	private Comparator comparator = GenericComparator.getInstance().getDateComparator();
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
		WeeklySummary weeklySummary = weeklySummaryDao.get(id);
		weeklySummary.getUser().getWeeklySummaries().remove(weeklySummary);
		weeklySummary.setUser(null);
		weeklySummaryDao.delete(weeklySummary);
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
		User user = userService.get(uid);
		WeeklySummary weeklySummary = new WeeklySummary()
				.setActualCheckInTimes(actualCheckInTimes).setComment(comment)
				.setGoalCheckInTimes(goalCheckInTimes).setUser(user);
		if(null != createTime)
			weeklySummary.setCreateTime(createTime);
		return weeklySummaryDao.save(weeklySummary);
	}
	private Criterion[] userWeeklySummaryCriterion(Long uid){
		Criterion [] crits = {
				Restrictions.eq("user", userService.get(uid))
		};
		return crits;
	}

	@Override
	public List<WeeklySummary> getUserWeeklySummary(Long uid) {
		return weeklySummaryDao.findByCriteria(userWeeklySummaryCriterion(uid));
	}
	private List<WeeklySummary> warpCriterions(Integer length,Integer startId,boolean after,Criterion...crits){
		if(Utils.checkIdIsZero(startId)) 
			return weeklySummaryDao.findByCriteria(length,crits);
		WeeklySummary weeklySummary = get(startId);
		return weeklySummaryDao.findByCriteria(length,
				Utils.warpIdRangeLimit(weeklySummary.getCreateTime(),"createTime",after, crits));
	}
	@Override
	public List<WeeklySummary> getUserWeeklySummary(Long uid, Integer startId,
			Integer length,boolean after) {
		return warpCriterions(length,startId,after,userWeeklySummaryCriterion(uid));
	//weeklySummaryDao.findByCriteria(length, Utils.warpIdRangeLimit(after, startId, userWeeklySummaryCriterion(uid)));
	}

	@Override
	public void deleteWeeklySummary(Long uid, Integer wid) {
		WeeklySummary summary = weeklySummaryDao.get(wid);
		User user = userService.get(uid);
		user.getWeeklySummaries().remove(summary);
		summary.setUser(null);
		weeklySummaryDao.delete(wid);
	}

	@Override
	public void saveOrUpdate(WeeklySummary transientObject) {
		weeklySummaryDao.saveOrUpdate(transientObject);
	}

}

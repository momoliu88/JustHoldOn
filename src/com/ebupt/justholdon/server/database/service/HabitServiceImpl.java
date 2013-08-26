package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.HabitDao;
import com.ebupt.justholdon.server.database.entity.Flag;
import com.ebupt.justholdon.server.database.entity.Habit;

@Service("habitService")
@Transactional
public class HabitServiceImpl implements HabitService {
	@Autowired
	private HabitDao habitDao;

	@Override
	public Integer save(Habit newInstance) {
		return habitDao.save(newInstance);
	}

	@Override
	public Habit get(Integer id) {
		return habitDao.get(id);
	}

	@Override
	public void update(Habit transientObject) {
		habitDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return habitDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
		// Habit habit = habitDao.get(id);
		// Set<UserHabit> userHabits = habit.getUserHabits();
		// Set<Flag> flags = habit.getFlags();
		// Set<CheckIn> checkIns = habit.getCheckIns();
		// Set<Event> events = habit.getEvents();
		// for(UserHabit userHabit:userHabits)
		// userHabit.setHabit(null);
		// for(Flag flag:flags)
		// flag.getHabits().remove(habit);
		// for(CheckIn checkIn:checkIns)
		// checkIn.setHabit(null);
		// for(Event event:events)
		// event.setHabit(null);
		// habit.getFlags().clear();
		// habit.getCheckIns().clear();
		// habit.getEvents().clear();
		// habit.getUserHabits().clear();
		// habitDao.delete(habit);
	}

	@Override
	public List<Habit> findAll() {
		return habitDao.findAll();
	}

	@Override
	public List<Habit> findAll(boolean byHot) {
		if (byHot)
			return habitDao.findAll(Order.desc("activeUserNum"));
		else
			return habitDao.findAll();
	}

	@Override
	public Map<String, Integer> groupNames() {
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("groupName"))
					  .add(Projections.count("groupName"));
		List<?> results = habitDao.findByCriteria(projectionList);
		Map<String, Integer> map = new HashMap<String, Integer>();
		Iterator<?> itr = results.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			map.put(obj[0].toString(), Integer.valueOf(obj[1].toString()));
		}
		return map;
	}

	private Criterion[] groupCriterion(String groupName) {
		Criterion[] criterions = { Restrictions.eq("groupName", groupName) };
		return criterions;
	}

	@Override
	public int findParticipateNum(Integer hid) {
		return get(hid).getActiveUserNum();
	}

	@Override
	public List<HabitGroupName> groupNamesList() {
		Map<String, Integer> groupNames = groupNames();
		List<HabitGroupName> ret = new ArrayList<HabitGroupName>();
		if (null == groupNames)
			return null;
		for (Entry<String, Integer> entry : groupNames.entrySet()) {
			ret.add(new HabitGroupName().setHabitnum(entry.getValue()).setName(
					entry.getKey()));
		}
		return ret;
	}

	private List<Habit> warpCriterions(Integer length,Integer startId,boolean after,Criterion...crits){
		if(Utils.checkIdIsZero(startId)) 
			return habitDao.findByCriteria(length,crits);
		Habit habit = get(startId);
		return habitDao.findByCriteria(length,
				Utils.warpIdRangeLimit(habit.getActiveUserNum(),"activeUserNum",after, crits));
	}
	@Override
	public List<Habit> findAll(boolean byHot, Integer startId, Integer length,
			boolean after) {
//		Criterion crits[] = {};
		return warpCriterions(length,startId,after,new Criterion[]{});
//		return habitDao.findByCriteria(length,
//				Utils.warpIdRangeLimit(after, startId, crits));
	}

	@Override
	public List<Habit> findAGroup(String groupName, Integer startId,
			Integer length, boolean after) {
	//	return habitDao.findByCriteria(length, Utils.warpIdRangeLimit(after,
//				startId, groupCriterion(groupName)));
		return warpCriterions(length,startId,after, groupCriterion(groupName));
	}

	// @Override
	// public Set<CheckIn> getCheckIns(Integer hid) {
	// return new HashSet<CheckIn>(get(hid).getCheckIns());
	// }

	// @Override
	// public Integer countCheckIns(Integer hid) {
	// return get(hid).getCheckIns().size();
	// }
	//
	// @Override
	// public Set<Event> getEvents(Integer hid) {
	// return new HashSet<Event>(get(hid).getEvents());
	// }
	//
	// @Override
	// public Integer countEvents(Integer hid) {
	// return get(hid).getEvents().size();
	// }
	//
	// @Override
	// public Set<UserHabit> getUserHabits(Integer hid) {
	// return new HashSet<UserHabit>(get(hid).getUserHabits());
	// }
	//
	// @Override
	// public Integer countUserHabits(Integer hid) {
	// return get(hid).getUserHabits().size();
	// }

	@Override
	public Set<Flag> getFlags(Integer hid) {
		return new HashSet<Flag>(get(hid).getFlags());
	}

	@Override
	public Integer countFlags(Integer hid) {
		return get(hid).getFlags().size();
	}

	private Criterion[] habitNameCriterion(String key) {
		Criterion[] crits = { Restrictions.ilike("habitName", "%"+key+"%") };
		return crits;
	}

	@Override
	public List<Habit> search(String key, Integer startId, Integer length,
			boolean after, boolean byHot) {
//		return habitDao
//				.findByCriteria(length, Utils.warpIdRangeLimit(after, startId,
//						habitNameCriterion(key)));
		return warpCriterions(length,startId,after,habitNameCriterion(key));
	}

	@Override
	public Integer countNotDeletedUserHabits(Integer hid) {
		return findParticipateNum(hid);
	}

	@Override
	public void saveOrUpdate(Habit transientObject) {
		habitDao.saveOrUpdate(transientObject);
	}

	@Override
	public Integer countHabits() {
		ProjectionList projectionList = Projections.projectionList().add(Projections.count("id"));
		List<?> results = habitDao.findByCriteria(projectionList);
		Object o = results.get(0);
		return Integer.valueOf(o.toString());
	}

}

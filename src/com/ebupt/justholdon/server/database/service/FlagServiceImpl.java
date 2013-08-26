package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.FlagDao;
import com.ebupt.justholdon.server.database.entity.Flag;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.User;

@Service("flagService")
@Transactional
public class FlagServiceImpl implements FlagService {
	public FlagServiceImpl() {
	};
	@Autowired
	private FlagDao flagDao;
	@Autowired
	private UserService userService;
	@Autowired
	private HabitService habitService;

	private String comparedCol = "userNums";
	private Order getOrder(boolean byHot){
		Order order = null;
		if(byHot)
			order = Order.desc(comparedCol);
		return order;
	}
	@Override
	public List<Flag> findAll(boolean byHot) {
		return flagDao.findAll(getOrder(byHot));
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
	public void delete(Integer id) {
		Flag flag = flagDao.get(id);
		Set<User> users = flag.getUsers();
		Set<Habit> habits = flag.getHabits();
		for(User user:users)
			user.getFlags().remove(flag);
		for(Habit habit:habits)
			habit.getFlags().remove(flag);
		flag.getUsers().clear();
		flag.getHabits().clear();
		flagDao.delete(flag);
	}

	/*
	 * @Override public Flag load(Integer id) { return flagDao.load(id); }
	 */
	@Override
	@Transactional(readOnly = false)
	public void addUser(Long uid, Integer flagid) {
		User user = userService.get(uid);
		Flag flag = flagDao.get(flagid);
		if (null == flag || null == user)
			return;
		if (user.getFlags().contains(flag) && flag.getUsers().contains(user))
			return;
		flag.getUsers().add(user);
		flag.setUserNums(flag.getUserNums()+1);
		user.getFlags().add(flag);
		flagDao.update(flag);
		userService.update(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void removeUser(Long uid, Integer fid) {
		User user = userService.get(uid);
		Flag flag = flagDao.get(fid);
		if (null == flag || null == user)
			return;
		if (!user.getFlags().contains(flag) && !flag.getUsers().contains(user))
			return;
		System.out.println("begin removes");
		flag.getUsers().remove(user);
		user.getFlags().remove(flag);
		flag.setUserNums(flag.getUserNums()-1);
		flagDao.update(flag);
		userService.update(user);

	}

	@Override
	@Transactional(readOnly = false)
	public void addHabit(Integer hid, Integer fid) {
		Habit habit = habitService.get(hid);
		Flag flag = flagDao.get(fid);
		if (null == flag || null == habit)
			return;
		if (habit.getFlags().contains(flag) && flag.getHabits().contains(habit))
			return;
		flag.getHabits().add(habit);
		habit.getFlags().add(flag);
		flag.setHabitNums(flag.getHabitNums()+1);
		flagDao.update(flag);
		habitService.update(habit);
	}

	@Override
	@Transactional(readOnly = false)
	public void removeHabit(Integer hid, Integer fid) {
		Habit habit = habitService.get(hid);
		Flag flag = flagDao.get(fid);
		if (null == flag || null == habit)
			return;
		if (!habit.getFlags().contains(flag)
				&& !flag.getHabits().contains(habit))
			return;
		flag.getHabits().remove(habit);
		habit.getFlags().remove(flag);
		flag.setHabitNums(flag.getHabitNums()-1);
		flagDao.update(flag);
		habitService.update(habit);
	}
	private List<Flag> warpCriterions(Integer length,Integer startId,boolean byHot,boolean after,Criterion...crits){
		boolean isZero = Utils.checkIdIsZero(startId);
		System.out.println("iszero "+isZero+" length "+length);
		if(isZero) 
			return flagDao.findByCriteria(length,crits);
		Flag flag = get(startId);
		if(byHot)
			return flagDao.findByCriteria(length,
				Utils.warpIdRangeLimit(flag.getUserNums(),comparedCol,after, crits));
		else
			return flagDao.findByCriteria(length,
					Utils.warpIdRangeLimit(flag.getId(),"id",after, crits));
	}
	@Override
	public List<Flag> findAll(boolean byHot, Integer startId,Integer length,boolean after) {
		Criterion [] crits ={};
		flagDao.setOrder(Arrays.asList(getOrder(byHot)));
		List<Flag> flags = warpCriterions(length,startId,byHot,after,crits);
		flagDao.setOrder(null);
		return flags;
	}
	private Criterion [] targetTypeCriterion(String targetType){
		Criterion[] crits ={
				Restrictions.eq("target", targetType)
		};
		return crits;
	}
	@Override
	public List<Flag> findAType(String targetType,Integer startId,Integer length,boolean after) {
//		StringBuilder columnName = new StringBuilder().append("F.").append(
//				"target");
//		String hql = new StringBuilder().append("from ").append(flagTbName)
//				.append(" F ").append(" where ").append(columnName)
//				.append(" =?").toString();
//		@SuppressWarnings("unchecked")
//		List<Flag> flags = (List<Flag>) flagDao.find(hql,null, targetType);
//		if (null == flags)
//			return null;
//		Collections.sort(flags, Flag.getHotComparator());
//		return Utils.cutEventList(flags, startId, length, after,true);
		flagDao.setOrder(Arrays.asList(getOrder(true)));
		List<Flag> flags = warpCriterions(length,startId,true,after,targetTypeCriterion(targetType));
				
		//flagDao.findByCriteria(length,Utils.warpIdRangeLimit(after, startId, targetTypeCriterion(targetType)));
		flagDao.setOrder(null);
		return flags;
	}

	@Override
	public int findHabitCounts(List<Integer> flagIds) {
		int size = 0;
		List<Habit> habits = findHabits(flagIds);
		if (null != habits)
			size = habits.size();
		return size;
	}

	public List<Habit> findHabitsByFlags(List<Flag> flags){
		Set<Habit> result = new HashSet<Habit>();
		for (Flag flag : flags) {
			Set<Habit> habits = flag.getHabits();
			for (Habit habit : habits)
				result.add(habit);
		}
		List<Habit> habits = new ArrayList<Habit>(result);
		Collections.sort(habits, Habit.getHotComparator());
		return habits;
	}
	@Override
	public List<Habit> findHabits(List<Integer> flagIds) {
		Set<Habit> result = new HashSet<Habit>();
		for (Integer flagId : flagIds) {
			Flag flag = get(flagId);
			Set<Habit> habits = flag.getHabits();
			for (Habit habit : habits)
				result.add(habit);
		}
		List<Habit> habits = new ArrayList<Habit>(result);
		Collections.sort(habits, Habit.getHotComparator());
		return habits;
	}

	@Override
	public List<Habit> findHabits(List<Integer> flagIds, Integer startId,
			Integer length,boolean after) {
		List<Habit> habits = findHabits(flagIds);
		return Utils.cutEventList(habits, startId, length, after,true);
	}

	@Override
	public Boolean hasFlags(Long uid) {
		User user = userService.get(uid);
		return !user.getFlags().isEmpty();
	}

	@Override
	public Set<Habit> getHabits(Integer fid) {
		return new HashSet<Habit>(get(fid).getHabits());
	}

	@Override
	public Integer countHabits(Integer fid) {
		return get(fid).getHabits().size();
	}

	@Override
	public Set<User> getUsers(Integer fid) {
		return new HashSet<User>(get(fid).getUsers());
	}

	@Override
	public Integer countUsers(Integer fid) {
		return get(fid).getUsers().size();
	}

	@Override
	public void saveOrUpdate(Flag transientObject) {
		flagDao.saveOrUpdate(transientObject);
	}
	@Override
	public List<Habit> findHabits(Long uid) {
		User user = userService.get(uid);
		List<Flag> flags = new ArrayList<Flag>(user.getFlags());
		return findHabitsByFlags(flags);
	}
	@Override
	public Integer createFlag(String content) {
		Flag flag = new Flag().setContent(content);
		return save(flag);
	}
}

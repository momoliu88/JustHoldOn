package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.HabitDao;
import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.dao.UserHabitDao;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.PrivilegeType;
import com.ebupt.justholdon.server.database.entity.User;
import com.ebupt.justholdon.server.database.entity.UserHabit;

@Service("userHabitService")
@Transactional
public class UserHabitServiceImpl implements UserHabitService {
	@Autowired
	private UserHabitDao userHabitDao;
	@Autowired
	private UserDao userDao;
	@Autowired 
	private UserService userService;
	@Autowired
	private HabitDao habitDao;
	@Autowired
	private EventService eventService; 
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public Integer save(UserHabit newInstance) {
		return userHabitDao.save(newInstance);
	}

	@Override
	public UserHabit get(Integer id) {
		return userHabitDao.get(id);
	}

	//
	// @Override
	// public UserHabit load(Integer id) {
	// return userHabitDao.load(id);
	// }

	@Override
	public void update(UserHabit transientObject) {
		userHabitDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return userHabitDao.update(id, infos);
	}

	/*
	 * @Override public void delete(UserHabit persistentObject) {
	 * userHabitDao.delete(persistentObject); }
	 */
	@Override
	public void delete(Integer id) {
		UserHabit userHabit = userHabitDao.get(id);
		userHabit.setStat(HabitState.DELETED);
		userHabit.setEndTime(new Date());
		//userHabit.getUser().getUserHabits().remove(userHabit);
		//userHabit.getHabit().getUserHabits().remove(userHabit);
		//userHabit.setUser(null);
		//userHabit.setHabit(null);
		userHabitDao.update(userHabit);
	}

	@Override
	public List<UserHabit> findAll() {
		return userHabitDao.findAll();
	}

	public boolean connectUserHabit(User user, Habit habit, UserHabit userHabit) {
		if ((null == user) || (null == habit) || (null == userHabit)) {
			logger.debug("args can't be null");
			return false;
		}

		if (userHabit.getStat() == HabitState.DELETED)
			return false;
		userHabit.setUser(user).setHabit(habit);

		userHabitDao.saveOrUpdate(userHabit);
//		userDao.update(user);
//		habitDao.update(habit);
		return true;
	}

	@Override
	public boolean connectUserHabit(Long uid, Integer hid, UserHabit userHabit) {
		User user = userDao.get(uid);
		Habit habit = habitDao.get(hid);
		return connectUserHabit(user, habit, userHabit);
	}
	
//	@Override
	public boolean exitHabit(Long uid, Integer hid) {
		UserHabit userHabit = getUserHabit(uid,hid);
		if(null == userHabit) return false;
		userHabitDao.delete(userHabit.getId());
		return true;
	}
//
//	@Override
//	public void delete(UserHabit persistentObject) {
//		userHabitDao.delete(persistentObject);
//	}

	@Override
	public UserHabit getUserHabit(Long uid, Integer hid) {
		User user = userDao.get(uid);
		Set<UserHabit> uHabits = user.getUserHabits();
		UserHabit userHabit = null;
		for (UserHabit uHabit : uHabits) {
			if (uHabit.getHabit().getId() == hid) {
				userHabit = uHabit;
				break;
			}
		}
		return userHabit;
	}

	@Override
	public List<UserHabit> getUserHabits(Long uid) {
		User user = userDao.get(uid);
		Set<UserHabit> userHabits = user.getUserHabits();
		List<UserHabit> ret = new ArrayList<UserHabit>(userHabits);
		Collections.sort(ret,UserHabit.getDateComparator());
		return ret;
	}

	@Override
	public List<UserHabit> getUserHabits(Long uid, Integer startpos,
			Integer endpos) {
		return Utils.subList(startpos, endpos, getUserHabits(uid));
	}
	private boolean hasPrivilege(UserHabit habit,PrivilegeType type)
	{
		return (habit.getPrivilege().compareTo(type) <= 0);
	}
	private PrivilegeType getPrivilege(Long uid,Long beWatched){
		boolean isFriend = userService.isFriend(uid, beWatched);
		PrivilegeType privilege = PrivilegeType.ALL;
		if(isFriend)
			privilege = PrivilegeType.ONLY_FRIENDS;
		return privilege;
	}
	@Override
	public List<UserHabit> getUserHabits(Long uid, Long beWatched) {
		
		PrivilegeType privilege = getPrivilege(uid,beWatched);
		List<UserHabit> hostHabits = getUserHabits(beWatched);
		List<UserHabit> ret = new ArrayList<UserHabit>();
		for(UserHabit hostHabit:hostHabits)
		{
			 if(hasPrivilege(hostHabit,privilege))
				 ret.add(hostHabit);
		}
		return ret;
	}

	@Override
	public List<UserHabit> getUserHabits(Long uid, Long beWatched,
			Integer startpos, Integer endpos) {
		return Utils.subList(startpos, endpos, getUserHabits(uid,beWatched));
	}
	private List<UserHabit> getUserHabits(List<UserHabit> userHabits,HabitState habitState)
	{
		List<UserHabit> ret = new ArrayList<UserHabit>();
		for(UserHabit userHabit:userHabits)
			if(userHabit.getStat().equals(habitState))
				ret.add(userHabit);
		return ret;
	}
	
	@Override
	public List<UserHabit> getUserHabits(Long uid, HabitState habitState) {
		List<UserHabit> userHabits = getUserHabits(uid);
		return getUserHabits(userHabits,habitState);
	}

	@Override
	public List<UserHabit> getUserHabits(Long uid, HabitState habitState,
			Integer startpos, Integer endpos) {
		return Utils.subList(startpos, endpos, getUserHabits(uid,habitState));
	}

	@Override
	public List<UserHabit> getUserHabits(Long uid, Long beWatched,
			HabitState habitState) {
		List<UserHabit> userHabits =getUserHabits(uid,beWatched);
		return getUserHabits(userHabits,habitState);
	}

	@Override
	public List<UserHabit> getUserHabits(Long uid, Long beWatched,
			HabitState habitState, Integer startpos, Integer endpos) {
		return Utils.subList(startpos, endpos, getUserHabits(uid,beWatched,habitState));
	}

	@Override
	public boolean updateState(Long uid, Integer hid,HabitState state) {
		UserHabit userHabit = getUserHabit(uid,hid);
		if(null == userHabit) return false;
		userHabit.setStat(state);
		if(state.equals(HabitState.DELETED) || state.equals(HabitState.COMPLETED))
			userHabit.setEndTime(new Date());
		userHabitDao.update(userHabit);
		return true;
	}

	@Override
	public UserHabit getUserHabit(Long uid, Long beWatched, Integer hid) {
		List<UserHabit> userHabits = getUserHabits(uid,beWatched);
		for(UserHabit userHabit:userHabits)
			if(userHabit.getHabit().getId() == hid)
				return userHabit;
		return null;
	}

	@Override
	public void connectUserHabitAndCreateEvent(Long uid, Integer hid,
			UserHabit uHid,String content) {
		connectUserHabit(uid,hid,uHid);
		eventService.createHabitEvent(uid, hid, EventType.SOMEBODY_JOININ_HABIT, content);
	}

	@Override
	public void updateStateAndCteateEvent(Long uid, Integer hid,
			HabitState state, String content) {
		updateState(uid,hid,state);
		if(state.equals(HabitState.CONSOLIDATING)||state.equals(HabitState.COMPLETED))
			eventService.createHabitEvent(uid, hid, EventType.HOLDON_STAGE, content);
		else if(state.equals(HabitState.DELETED))
			eventService.createHabitEvent(uid, hid, EventType.QUIT_HABIT, content);

	}

	@Override
	public boolean hasParticipateHabit(Long uid, Integer hid) {
		String tbName = UserHabit.class.getName();
		String hql  = "from "+tbName+" as uh where uh.user = ? and uh.habit = ?";
		
		@SuppressWarnings("unchecked")
		List<UserHabit>userHabits = (List<UserHabit>) userHabitDao.find(hql, userDao.get(uid),habitDao.get(hid));
		if(userHabits==null || userHabits.isEmpty())
			return false;
		return true;
	}

	@Override
	public boolean hasPrivilegeToSee(Long uid, Long beWatched, Integer hid) {
		if(beWatched.equals(uid))
			return true;
		PrivilegeType privilege = getPrivilege(uid,beWatched);
		List<UserHabit> hostHabits = getUserHabits(beWatched);
		for(UserHabit hostHabit:hostHabits)
		{
			 if((hostHabit.getHabit().getId() == hid) 
					 && hasPrivilege(hostHabit,privilege))
				 return true;
		}
		return false;
	}

}

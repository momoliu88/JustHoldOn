package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.UserHabitDao;
import com.ebupt.justholdon.server.database.entity.CheckIn;
import com.ebupt.justholdon.server.database.entity.Event;
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
	private UserService userService;
	@Autowired
	private HabitService habitService;
	@Autowired
	private RelationShipService relationShipService;
	@Autowired
	private EventService eventService;
	@Autowired
	private CheckInService checkInService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	//private String tbName = TbName.userHabitTb;

	@Override
	public Integer save(UserHabit newInstance) {
		return userHabitDao.save(newInstance);
	}

	@Override
	public UserHabit get(Integer id) {
		return userHabitDao.get(id);
	}
	
	@Override
	public void update(UserHabit transientObject) {
		userHabitDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return userHabitDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
		UserHabit userHabit = userHabitDao.get(id);
		userHabit.setStat(HabitState.DELETED);
		userHabit.setEndTime(new Date());
		Long uid = userHabit.getUser().getId();
		Habit habit =  userHabit.getHabit();
		Integer hid =habit.getId();
		/***
		 * reduce habit's active usernum
		 * */
		habit.setActiveUserNum(habit.getActiveUserNum()-1);
		habitService.update(habit);
		/***
		 * set related checkin deleted
		 */
		List<CheckIn> cks = checkInService.getCheckIns(uid,hid);
		if(null != cks)
		for(CheckIn ck:cks){
			ck.setIsDeleted(true);
			User user = ck.getUser();
			user.setCheckInNums(user.getCheckInNums()-1);
			ck.setModifyTime(new Date());
			userService.update(user);
			checkInService.update(ck);
		}
		/**
		 * set related events deleted
		 */
		List<Event> events = eventService.getEvents(uid, hid);
		if(null != events)
			for(Event event:events){
				event.setIsDeleted(true);
				eventService.update(event);
			}
		userHabitDao.update(userHabit);
	}

	@Override
	public List<UserHabit> findAll() {
		return userHabitDao.findAll();
	}
	private Criterion[] habitStateCriterion(Integer hid,HabitState state){
		Criterion [] crits = {
				Restrictions.eq("habit", habitService.get(hid)),
				Restrictions.eq("stat",state)
		};
		return crits;
	}
	private Criterion[] userStateCriterion(Long uid,HabitState state){
		Criterion [] crits = {
				Restrictions.eq("user", userService.get(uid)),
				Restrictions.eq("stat",state)
		};
		return crits;
	}
	private Criterion[] userCriterion(Long uid){
		Criterion [] crits = {
				Restrictions.eq("user", userService.get(uid)),
		};
		return crits;
	}

	private Criterion[] userNotDelStateCriterion(Long uid){
		Criterion [] crits = {
				Restrictions.eq("user", userService.get(uid)),
				Restrictions.ne("stat",HabitState.DELETED)
		};
		return crits;
	}
	private Criterion[] habitNoDelCriterion(Integer hid){
		Criterion [] crits = {
				Restrictions.eq("habit", habitService.get(hid)),
				Restrictions.ne("stat",HabitState.DELETED)
		};
		return crits;
	}
	private Criterion[] userHabitCriterions(Long uid,Integer hid){
		Criterion [] crits = {
				Restrictions.eq("habit", habitService.get(hid)),
				Restrictions.eq("user",userService.get(uid))
		};
		return crits;
	}
	@Override
	public Integer countUserHabits(Integer hid, HabitState state) {
		ProjectionList projectionList = Projections.projectionList().add(Projections.count("id"));
		List<?> ret = userHabitDao.findByCriteria(projectionList,habitStateCriterion(hid,state));
		return Integer.valueOf(((Object[])(ret.get(0)))[0].toString());		 
	}

	
	@Override
	public List<User> findParticipate(Integer hid) {
		ProjectionList projectionList = Projections.projectionList().add(Projections.property("user"));
		return Utils.convertResults( userHabitDao.findByCriteria(projectionList,habitNoDelCriterion(hid)));
	}
	@Override
	public List<User> findParticipateAndFriends(Integer hid, Long uid) {
		List<User> users = findParticipate(hid);
		List<User> friends = relationShipService.findFriends(uid);
		List<User> friendsList = new ArrayList<User>(friends);
		users.retainAll(friendsList);
		return users;
	}
	public boolean connectUserHabit(User user, Habit habit, UserHabit userHabit) {
		if ((null == user) || (null == habit) || (null == userHabit)) {
			logger.debug("args can't be null");
			return false;
		}
		if (userHabit.getStat().equals(HabitState.DELETED))
			return false;
		UserHabit existedOne;
		if(null != (existedOne = getUserHabit(user.getId(),habit.getId())) 
				&& !( 
						existedOne.getStat().equals(HabitState.DELETED)
						||existedOne.getStat().equals(HabitState.COMPLETED)
					)
		)
		{
			return false;
		}
		userHabit.setUser(user).setHabit(habit);
		habit.setActiveUserNum(habit.getActiveUserNum()+1);
		userHabitDao.saveOrUpdate(userHabit);
		return true;
	}
	@Override
	public boolean connectUserHabit(Long uid, Integer hid, UserHabit userHabit) {
		User user = userService.get(uid);
		Habit habit = habitService.get(hid);
		return connectUserHabit(user, habit, userHabit);
	}

	boolean exitHabit(Long uid, Integer hid) {
		UserHabit userHabit = getUserHabit(uid, hid);
		if (null == userHabit)
			return false;
		delete(userHabit.getId());
		return true;
	}
	
	@Override
	public UserHabit getUserHabit(Long uid, Integer hid) {
		List<UserHabit> ret = userHabitDao.findByCriteria(1, userHabitCriterions(uid,hid));
		if(null == ret || ret.isEmpty()) return null;
		return ret.get(0);
	}

	@Override
	public List<UserHabit> getUserHabits(Long uid) {
		return userHabitDao.findByCriteria(userNotDelStateCriterion(uid));
	}
	private List<UserHabit> warpCriterions(Integer length,Integer startId,boolean after,Criterion...crits){
		if(Utils.checkIdIsZero(startId)) 
			return userHabitDao.findByCriteria(length,crits);
		UserHabit userHabit = get(startId);
		return userHabitDao.findByCriteria(length,
				Utils.warpIdRangeLimit(userHabit.getCreateTime(),"createTime",after, crits));
	}
	@Override
	public List<UserHabit> getUserHabits(Long uid, Integer startId,
			Integer length, boolean after) {
		//return userHabitDao.findByCriteria(Utils.warpIdRangeLimit(after, startId, userNotDelStateCriterion(uid)));
		return warpCriterions(length,startId,after,userNotDelStateCriterion(uid));
	}

	private boolean hasPrivilege(UserHabit habit, PrivilegeType type) {
		return (habit.getPrivilege().compareTo(type) <= 0);
	}

	private PrivilegeType getPrivilege(Long uid, Long beWatched) {
		if (uid.equals(beWatched))
			return PrivilegeType.MYSELF;
		boolean isFriend = relationShipService.isFriend(uid, beWatched);
		PrivilegeType privilege = PrivilegeType.ALL;
		if (isFriend)
			privilege = PrivilegeType.ONLY_FRIENDS;
		return privilege;
	}

	private boolean checkPrivilege(Long uid,Long beWatched,UserHabit hostHabit){
		PrivilegeType privilege = getPrivilege(uid, beWatched);
		return hasPrivilege(hostHabit, privilege);
	}
	@Override
	public List<UserHabit> getUserHabits(Long uid, Long beWatched) {
		List<UserHabit> hostHabits = getUserHabits(beWatched);
		List<UserHabit> ret = new ArrayList<UserHabit>();
		for (UserHabit hostHabit : hostHabits) {
			if (checkPrivilege(uid,beWatched,hostHabit))
				ret.add(hostHabit);
		}
		return ret;
	}
	private List<UserHabit> getPrivilegeHabits(Long uid,Long beWatched,Integer startId,Integer length,boolean after,Criterion... criterion){
		int nums = 0 ;
		int id = startId.intValue();
		Criterion crits [] = {};
		Integer fetchSize = Integer.valueOf(length);
		List<UserHabit> uHs;
		List<UserHabit> ret = new ArrayList<UserHabit>();
		while(true){
//			crits = Utils.warpIdRangeLimit(after, id, criterion);
			uHs = warpCriterions(Utils.getMulti(fetchSize),id,after,crits);
//					userHabitDao.findByCriteria(Utils.getMulti(fetchSize), crits);
			if(null == uHs|| uHs.isEmpty()) return ret;
			for(UserHabit uH:uHs)
			{
				if(checkPrivilege(uid,beWatched,uH))
				{
					ret.add(uH);
					nums++;
					if(nums >= length.intValue()) return ret;
				}
			}
			id = Utils.getNewestId(after, uHs);
		}
	}
	@Override
	public List<UserHabit> getUserHabits(Long uid, Long beWatched,
			Integer startId, Integer length, boolean after) {
		return getPrivilegeHabits(uid,beWatched,startId,length,after,userNotDelStateCriterion(beWatched));
	}
//
//	private List<UserHabit> getUserHabits(List<UserHabit> userHabits,
//			HabitState habitState) {
//		List<UserHabit> ret = new ArrayList<UserHabit>();
//		for (UserHabit userHabit : userHabits)
//			if (userHabit.getStat().equals(habitState))
//				ret.add(userHabit);
//		return ret;
//	}
	List<UserHabit> getUserHabitsInAllType(Long uid){
		return userHabitDao.findByCriteria(userCriterion(uid));
	}
	@Override
	public List<UserHabit> getUserHabits(Long uid, HabitState habitState) {
		return userHabitDao.findByCriteria(userStateCriterion(uid,habitState));
	}

	@Override
	public List<UserHabit> getUserHabits(Long uid, HabitState habitState,
			Integer startId, Integer length, boolean after) {
	//	return userHabitDao.findByCriteria(length,Utils.warpIdRangeLimit(after, startId,userStateCriterion(uid,habitState)));
		return warpCriterions(length,startId,after,userStateCriterion(uid,habitState));
	}

	@Override
	public List<UserHabit> getUserHabits(Long uid, Long beWatched,
			HabitState habitState) {
		List<UserHabit> hostHabits = getUserHabits(beWatched,habitState);
		List<UserHabit> ret = new ArrayList<UserHabit>();
		for (UserHabit hostHabit : hostHabits) {
			if (checkPrivilege(uid,beWatched,hostHabit))
				ret.add(hostHabit);
		}
		return ret;
	}

	@Override
	public List<UserHabit> getUserHabits(Long uid, Long beWatched,
			HabitState habitState, Integer startId, Integer length,
			boolean after) {
		return getPrivilegeHabits(uid,beWatched,startId,length,after,userStateCriterion(beWatched,habitState));
	}

	@Override
	public boolean updateState(Long uid, Integer hid, HabitState state) {
		UserHabit userHabit = getUserHabit(uid, hid);
		if (null == userHabit)
			return false;
		userHabit.setStat(state);
		if (state.equals(HabitState.DELETED)
				|| state.equals(HabitState.COMPLETED))
			userHabit.setEndTime(new Date());
		userHabitDao.update(userHabit);
		return true;
	}

	@Override
	public UserHabit getUserHabit(Long uid, Long beWatched, Integer hid) {
		List<UserHabit> uHs = userHabitDao.findByCriteria(userHabitCriterions(beWatched,hid));
		if(null == uHs || uHs.isEmpty()) return null;
		if(checkPrivilege(uid,beWatched,uHs.get(0)))
			return uHs.get(0);
		return null;
	}

	@Override
	public boolean AddUserHabitAndCreateEvent(Long uid, Integer hid,
			UserHabit uHid, String content) {
		boolean flag = connectUserHabit(uid, hid, uHid);
		eventService.createHabitEvent(uid, hid,
				EventType.SOMEBODY_JOININ_HABIT, content);
		return flag;
	}

	@Override
	public void updateStateAndCteateEvent(Long uid, Integer hid,
			HabitState state, String content) {
		updateState(uid, hid, state);
		if (state.equals(HabitState.CONSOLIDATING)
				|| state.equals(HabitState.COMPLETED))
			eventService.createHabitEvent(uid, hid, EventType.HOLDON_STAGE,
					content);
		else if (state.equals(HabitState.DELETED))
			eventService.createHabitEvent(uid, hid, EventType.QUIT_HABIT,
					content);

	}

	@Override
	public boolean hasParticipateHabit(Long uid, Integer hid) {
		List<UserHabit> uHs = userHabitDao.findByCriteria(userHabitCriterions(uid,hid));
		if(null == uHs || uHs.isEmpty()) return false;
		if(uHs.get(0).getStat().equals(HabitState.DELETED)) return false;
		return true;
	}

	@Override
	public boolean hasPrivilegeToSee(Long uid, Long beWatched, Integer hid) {
		if (beWatched.equals(uid))
			return true;
		PrivilegeType privilege = getPrivilege(uid, beWatched);
		UserHabit uH = getUserHabit(beWatched,hid);
		if(null == uH||uH.getStat().equals(HabitState.DELETED)) return false;
		return hasPrivilege(uH, privilege);
	}

	@Override
	public void connectUserHabitAndCreateEvent(Long uid, Integer hid,
			UserHabit uHid, String content) {
		if(connectUserHabit(uid, hid, uHid))
			eventService.createHabitEvent(uid, hid,
					EventType.SOMEBODY_JOININ_HABIT, content);
	//	connectUserHabit(uid, hid, uHid);
		
		
	}

	@Override
	public void deleteUserHabit(Long uid, Integer hid) {
		UserHabit uh = getUserHabit(uid,hid);
		if(uh == null||uh.getStat().equals(HabitState.DELETED)){
			logger.error("habit is alreay been deleted");
			return;
		}
		delete(uh.getId());
		
	}

	@Override
	public void saveOrUpdate(UserHabit transientObject) {
		userHabitDao.saveOrUpdate(transientObject);
	}

	private Integer countUserHabit(Criterion... crits){
		ProjectionList projectionList = Projections.projectionList().add(Projections.count("user"));
		List<?> ret = userHabitDao.findByCriteria(projectionList,crits);
		if(ret == null || ret.isEmpty()) return 0;
		Object  obj = ret.get(0);
		return Integer.valueOf(obj.toString());
	}
	@Override
	public Integer countUserHabits(Long uid) {
		return countUserHabit(userCriterion(uid));
	}

	@Override
	public Integer countNotDeletedUserHabits(Long uid) {
		return countUserHabit(userNotDelStateCriterion(uid));
	}
	private Criterion [] friendHabitNotDel(Long uid,Integer hid,List<User>friends){
		Criterion [] crits ={
				Restrictions.eq("habit", habitService.get(hid)),
				Restrictions.in("user",friends),
				Restrictions.ne("stat",HabitState.DELETED)
		};
		return crits;
	}
	
	@Override
	public Integer countNotDeletedUserHabits(Long uid, Integer hid) {
		List<User> friends = relationShipService.findFriends(uid);
		if(null == friends || friends.isEmpty()) return 0;
		return countUserHabit(friendHabitNotDel(uid,hid,friends));
	}

}

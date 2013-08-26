package com.ebupt.justholdon.server.database.service;

//import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
//import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.UserDao;
//import com.ebupt.justholdon.server.database.entity.Approve;
//import com.ebupt.justholdon.server.database.entity.CheckIn;
//import com.ebupt.justholdon.server.database.entity.Comment;
//import com.ebupt.justholdon.server.database.entity.Event;
//import com.ebupt.justholdon.server.database.entity.Flag;
//import com.ebupt.justholdon.server.database.entity.GenericComparator;
//import com.ebupt.justholdon.server.database.entity.Impression;
//import com.ebupt.justholdon.server.database.entity.SystemInfoSended;
import com.ebupt.justholdon.server.database.entity.User;
//import com.ebupt.justholdon.server.database.entity.UserHabit;
//import com.ebupt.justholdon.server.database.entity.WeeklySummary;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private InvitedUserService invitedUserService;
//	@Autowired
//	private RelationShipService relationShipService;
//	private String userTbName = User.class.getName();
	@Override
	public User get(Long id) {
		return userDao.get(id);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	@Transactional(readOnly = false)
	public Long save(User user) {
		return userDao.save(user);
	}

	@Override
	public int update(Long id, Map<String, Object> infos) {
		return userDao.update(id, infos);
	}

	@Override
	public void delete(Long id) {
		//userDao.delete(id);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}
	
	@Override
	public boolean isValideUser(Long uid, String password) {
		User user = get(uid);
		if (null == user)
			return false;
		return user.getPassword().equals(password);
	}
	private final String friendNums = "friendNums";
	private final String checkInNums = "checkInNums";
	private Order getOrder(ComparatorType type){
		if(null == type)return null;
		if(type.equals(ComparatorType.MOSTFRIENDS))
			return Order.desc(friendNums);
		else if(type.equals(ComparatorType.ACTIVEST))
			return Order.desc(checkInNums);
		return null;
	}
	private List<User> warpCriterions(Integer length,Long startId,ComparatorType type,boolean after,Criterion...crits){
		if(Utils.checkIdIsZero(startId)) 
			return userDao.findByCriteria(length,crits);
		User user = get(startId);
		Object col =user.getFriendNums();
		String colName = friendNums;
		if(type != null && type.equals(ComparatorType.ACTIVEST))
		{
			col = user.getCheckInNums();
			colName = checkInNums;
		}
		return userDao.findByCriteria(length,
				Utils.warpIdRangeLimit(col,colName,after, crits));
	}
	@Override
	public List<User> findUsers(ComparatorType type) {
		return userDao.findAll(getOrder(type));
	}

	@Override
	public List<User> findUsers(ComparatorType type, Long startId, Integer length,boolean after) {
//		Criterion[] crits ={};
		userDao.setOrder(Arrays.asList(getOrder(type)));
		List<User> ret = warpCriterions(length,startId,type,after, new Criterion[]{});
//				userDao.findByCriteria(length,Utils.warpIdRangeLimit(after, startId, crits));
		userDao.setOrder(null);
		return ret;
	}

 
//	@Override
//	public List<UserHabit> getUserHabits(Long uid) {
//		List<UserHabit> uH = new ArrayList<UserHabit>(get(uid).getUserHabits());
//		Collections.sort(uH,GenericComparator.getInstance().getDateComparator());
//		return uH;
//	}

//	@Override
//	public Set<Flag> getFlags(Long uid) {
//		return new HashSet<Flag>(get(uid).getFlags());
//	}

//	@Override
//	public Integer countFriends(Long uid) {
//		logger.info("count friends");
//		return get(uid).getFriends().size();
//	}
//
//	@Override
//	public Integer countUserHabits(Long uid) {
//		logger.info("count userhabits");
//		return get(uid).getUserHabits().size();
//	}

	@Override
	public Integer countFlags(Long uid) {
		logger.info("count flags");
		return get(uid).getFlags().size();
	}

//	@Override
//	public List<SystemInfoSended> getReceiveSystemInfos(Long uid) {
//		List<SystemInfoSended> ret = new ArrayList<SystemInfoSended>(get(uid).getReceiveSystemInfos());
//		Collections.sort(ret,GenericComparator.getInstance().getDateComparator());
//		return ret;
//	}
//
//	@Override
//	public Integer countReceiveSystemInfos(Long uid) {
//		return get(uid).getReceiveSystemInfos().size();
//	}
//
//	@Override
//	public List<Event> getSponsorEvent(Long uid) {
//		List<Event> ret  = new ArrayList<Event>(get(uid).getSponsorEvent());
//		Collections.sort(ret,GenericComparator.getInstance().getDateComparator());
//		return ret;
//	}
//
//	@Override
//	public Integer countSponsorEvent(Long uid) {
//		return get(uid).getSponsorEvent().size();
//	}
//
//	@Override
//	public List<Event> getReceiverEvent(Long uid) {
//		List<Event> ret =  new ArrayList<Event>(get(uid).getReceiverEvent());
//		Collections.sort(ret,GenericComparator.getInstance().getDateComparator());
//		return ret;
//	}
//
//	@Override
//	public Integer countReceiverEvent(Long uid) {
//		return get(uid).getReceiverEvent().size();
//	}
//
//	@Override
//	public List<Comment> getSponsorComments(Long uid) {
//		List<Comment> ret= new ArrayList<Comment>(get(uid).getSponsorComments());
//		Collections.sort(ret,GenericComparator.getInstance().getDateComparator());
//		return ret;
//	}
//
//	@Override
//	public Integer countSponsorComments(Long uid) {
//		return get(uid).getSponsorComments().size();
//	}
//
//	@Override
//	public List<Comment> getReceiverComments(Long uid) {
//		List<Comment> ret =  new ArrayList<Comment>(get(uid).getReceiverComments());
//		Collections.sort(ret,GenericComparator.getInstance().getDateComparator());
//		return ret;
//	}

//	@Override
//	public Integer countReceiverComments(Long uid) {
//		return get(uid).getReceiverComments().size();
//	}
//
//	@Override
//	public List<WeeklySummary> getWeeklySummaries(Long uid) {
//		List<WeeklySummary> ret =  new ArrayList<WeeklySummary>(get(uid).getWeeklySummaries());
//		Collections.sort(ret,GenericComparator.getInstance().getDateComparator());
//		return ret;
//	}
//
//	@Override
//	public Integer countWeeklySummaries(Long uid) {
//		return get(uid).getWeeklySummaries().size();
//	}
//
////	@SuppressWarnings("unchecked")
//	@Override
//	public List<Approve> getApproves(Long uid) {
//		List<Approve> approves = new ArrayList<Approve>(get(uid).getApproves());
//		Collections.sort(approves,GenericComparator.getInstance().getDateComparator());
//		return approves;
//	}
//
//	@Override
//	public Integer countApproves(Long uid) {
//		return get(uid).getApproves().size();
//	}
//
//	@Override
//	public List<CheckIn> getCheckIns(Long uid) {
//		List<CheckIn> checkIns = new ArrayList<CheckIn>(get(uid).getCheckIns());
//		//this date comparator is checkin time comparator.
//		Collections.sort(checkIns,CheckIn.getDateComparator());
//		return checkIns;
//	}
//
//	@Override
//	public Integer countCheckIns(Long uid) {
//		return get(uid).getCheckIns().size();
//	}
//
//	@Override
//	public List<Impression> getSponseImpressiones(Long uid) {
//		List<Impression> ret = new ArrayList<Impression>(get(uid).getSponseImpressiones());
//		Collections.sort(ret,GenericComparator.getInstance().getDateComparator());
//		return ret;
//	}
//
//	@Override
//	public Integer countSponseImpressiones(Long uid) {
//		return get(uid).getSponseImpressiones().size();
//	}

//	@Override
//	public List<Impression> getReceivedImpressiones(Long uid) {
//		List<Impression> ret =  new ArrayList<Impression>(get(uid).getReceivedImpressiones());
//		Collections.sort(ret,GenericComparator.getInstance().getDateComparator());
//		return ret;
//	}
//
//	@Override
//	public Integer countReceivedImpressiones(Long uid) {
//		return get(uid).getReceivedImpressiones().size();
//	}

	private Criterion [] userNameCriterion(String key){
		Criterion[] crits = {
				Restrictions.ilike("userName","%"+key+"%")
		};
		return crits;
	}
	@Override
	public List<User> search(String key, Long startId, Integer length,
			boolean after, boolean byHot) {
//		StringBuilder hql = new StringBuilder().append("from ").append(userTbName)
//				.append(" U ").append("where U.userName like ?");
//		//@SuppressWarnings("unchecked")
//		List<User> users = (List<User>) userDao.find(hql.toString(), null,"%"+key+"%");
//		if(byHot)
//			Collections.sort(users,User.getFriendsMostComparator());
//		return Utils.cutEventList(users, startId, length, after, true);
		ComparatorType type = null;
		if(byHot)
		{
			type = ComparatorType.MOSTFRIENDS;
			userDao.setOrder(Arrays.asList(getOrder(type)));
		}
		List<User> users = warpCriterions(length,startId,type,after,userNameCriterion(key)); 
//		userDao.findByCriteria(length, Utils.warpIdRangeLimit(after, startId, userNameCriterion(key)));
		userDao.setOrder(null);
		return users;
	}
//
//	@Override
//	public Integer countNotDeletedUserHabits(Long uid) {
//		Set<UserHabit> uHs = get(uid).getUserHabits();
//		Integer count =0;
//		for(UserHabit uH:uHs)
//			if(!uH.getStat().equals(HabitState.DELETED))
//				count ++;
//		return count;
//	}

//	@Override
//	public Integer countUserHabits(Long uid, HabitState state) {
//		Set<UserHabit> uHs = get(uid).getUserHabits();
//		Integer count =0;
//		for(UserHabit uH:uHs)
//			if(uH.getStat().equals(state))
//				count ++;
//		return count;	
//	}
	//I think this will case efficient problems
//	@Override
//	public Integer countNotDeletedUserHabits(Long uid, Integer hid) {
//		Integer count = 0;
//		List<User> friends = relationShipService.findFriends(uid);
//		for(User f:friends){
//			Set<UserHabit> userHabits = f.getUserHabits();
//			for(UserHabit uH:userHabits)
//				if(!uH.getStat().equals(HabitState.DELETED) && uH.getHabit().getId().equals(hid))
//				{
//					count++;
//					break;
//				}
//		}
//		return count;
//	}

	@Override
	public void saveOrUpdate(User transientObject) {
		userDao.saveOrUpdate(transientObject);
	}

	@Override
	public void update(Long uid, Integer limited) {
		User user = get(uid);
		user.setLimitedUser(limited);
		update(user);
	}

	@Override
	public Integer getUsers(Date start, Date end) {
		List<String> users = invitedUserService.hasInvitedUser();
		List<Criterion> crits = new ArrayList<Criterion>();
		crits.add(Restrictions.between("createTime",start,end));
		if(users != null && !users.isEmpty())
			crits.add(Restrictions.not(Restrictions.in("userName", users)));
		ProjectionList projList = Projections.projectionList().add(Projections.count("userName"));
		Criterion[] criterions = new Criterion[crits.size()];
		crits.toArray(criterions);
		List<?> results = userDao.findByCriteria(projList,criterions);
		return Integer.valueOf(results.get(0).toString());
	}

}

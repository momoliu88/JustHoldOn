package com.ebupt.justholdon.server.database.service;

import java.util.Date;
import java.util.List;

import com.ebupt.justholdon.server.database.entity.User;
//import com.ebupt.justholdon.server.database.entity.UserHabit;

public interface UserService extends GenericService<User, Long> {

//	public List<UserHabit> getUserHabits(Long uid);
	public void update(Long uid,Integer limited);
	public Integer getUsers(Date start,Date end);

//	public Integer countUserHabits(Long uid, HabitState state);

//	public Set<Flag> getFlags(Long uid);

	// used
	public Integer countFlags(Long uid);

//	public List<SystemInfoSended> getReceiveSystemInfos(Long uid);
//
//	public Integer countReceiveSystemInfos(Long uid);
//
//	public List<Event> getSponsorEvent(Long uid);
//
//	public Integer countSponsorEvent(Long uid);
//
//	public List<Event> getReceiverEvent(Long uid);
//
//	public Integer countReceiverEvent(Long uid);
//
//	public List<Comment> getSponsorComments(Long uid);
//
//	public Integer countSponsorComments(Long uid);
//
//	public List<Comment> getReceiverComments(Long uid);
//
//	public Integer countReceiverComments(Long uid);
//
//	public List<WeeklySummary> getWeeklySummaries(Long uid);
//
//	public Integer countWeeklySummaries(Long uid);
//
//	public List<Approve> getApproves(Long uid);
//
//	public Integer countApproves(Long uid);
//
//	public List<CheckIn> getCheckIns(Long uid);
//
//	public Integer countCheckIns(Long uid);
//
//	public List<Impression> getSponseImpressiones(Long uid);
//
//	public Integer countSponseImpressiones(Long uid);
//
//	public List<Impression> getReceivedImpressiones(Long uid);
//
//	public Integer countReceivedImpressiones(Long uid);

	// used
	public List<User> search(String key, Long startId, Integer length,
			boolean after, boolean byHot);

	// used
	public boolean isValideUser(Long uid, String password);

	// use uid to find this user's friends

	// find all users in this app
	public List<User> findUsers(ComparatorType type);

	// used
	public List<User> findUsers(ComparatorType type, Long startId,
			Integer length, boolean after);

}

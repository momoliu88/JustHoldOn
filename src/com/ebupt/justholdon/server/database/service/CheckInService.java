package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.Approve;
import com.ebupt.justholdon.server.database.entity.CheckIn;
import com.ebupt.justholdon.server.database.entity.Comment;
//import com.ebupt.justholdon.server.database.entity.UserHabit;

import java.util.List;
import java.util.Date;

public interface CheckInService extends GenericService<CheckIn,Integer> {
	public List<CheckIn> getHotCheckins(Long uid,Date startDay,Date end,int length);
	//check in: userid and habitid can't be null
	public Integer checkIn(Long uid,Integer hid);
	//used
	public Integer checkInAndCreateEvent(Long uid,Integer hid,String content);
	//check in:checkIn.user and checkIn.habit can't be null
	public Integer checkIn(Long uid,Integer hid,CheckIn checkIn);
	public Integer checkInAndCreateEvent(Long uid,Integer hid,CheckIn checkIn,String content);

//	public UserHabit getUserHabit(Integer cid);
	//delete checkin，just for test,no need in practise
	public void deleteCheckIn(Integer cid);
	//used
	public List<Approve> getApproves(Integer cid);
	public Integer countApproves(Integer cid);
	//used
	public List<Comment> getComments(Integer cid);
	public Integer countComments(Integer cid);
	//used
	public List<CheckIn> getCheckIns(Long uid,Integer hid);
	//used
	//this method shouldn't use
	public List<CheckIn> getCheckIns(Long uid,Long bewatched,Integer hid);
	public List<CheckIn> getCheckIns(Long uid,Long beWatched,Integer hid,Integer startId,Integer length,boolean after);
	//get checkins 
	public List<CheckIn> getCheckIns(Long uid,Integer hid,Integer startId,Integer length,boolean after);
	public int getCheckInNum(Long uid,Integer hid);
	public int getCheckInNum(Long uid);

	//used
	public int getCheckInInTimeRangeNum(Long uid,Integer hid,Date start,Date end);
	public List<CheckIn> getCheckInInTimeRange(Long uid,Integer hid,Date start,Date end);
	public List<CheckIn> getCheckInInTimeRange(Long uid,Integer hid,Date start,Date end,Integer startId,Integer length,boolean after);

}

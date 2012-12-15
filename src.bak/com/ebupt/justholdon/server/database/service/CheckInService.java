package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.CheckIn;
import java.util.List;
import java.util.Date;

public interface CheckInService extends GenericService<CheckIn,Integer> {
	//check in: userid and habitid can't be null
	public Integer checkIn(Long uid,Integer hid);
	public Integer checkInAndCreateEvent(Long uid,Integer hid,String content);
	//check in:checkIn.user and checkIn.habit can't be null
	public Integer checkIn(Long uid,Integer hid,CheckIn checkIn);
	public Integer checkInAndCreateEvent(Long uid,Integer hid,CheckIn checkIn,String content);

	
	//delete checkin，just for test,no need in practise
	public void deleteCheckIn(Integer cid);
	
	public List<CheckIn> getCheckIns(Long uid,Integer hid);
	public List<CheckIn> getCheckIns(Long uid,Integer hid,Integer start,Integer end);
	public int getCheckInNum(Long uid,Integer hid);
	public int getCheckInInTimeRangeNum(Long uid,Integer hid,Date start,Date end);
	public List<CheckIn> getCheckInInTimeRange(Long uid,Integer hid,Date start,Date end);
	public List<CheckIn> getCheckInInTimeRange(Long uid,Integer hid,Date start,Date end,Integer startpos,Integer endpos);

}

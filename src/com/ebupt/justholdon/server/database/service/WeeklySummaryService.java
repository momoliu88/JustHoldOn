package com.ebupt.justholdon.server.database.service;

import java.util.Date;
import java.util.List;

import com.ebupt.justholdon.server.database.entity.WeeklySummary;

public interface WeeklySummaryService extends GenericService<WeeklySummary, Integer> {
	/*create a weeklysummary for user uid*/
	public Integer createWeeklySummary(Long uid,int goalCheckInTimes,int actualCheckInTimes,String comment);
	public Integer createWeeklySummary(Long uid,int goalCheckInTimes,int actualCheckInTimes,Date createTime,String comment);
	
	/*get a user's weeklysummaries,sorted by date*/
	public List<WeeklySummary> getUserWeeklySummary(Long uid);
	public List<WeeklySummary> getUserWeeklySummary(Long uid,Integer start,Integer end);
	
	/*remove*/
	public void deleteWeeklySummary(Long uid,Integer wid);

}

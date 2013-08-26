package com.ebupt.justholdon.server.database.service.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.Flag;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.UserHabit;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.UserHabitService;

@Service("integerationService")
@Transactional
public class IntegerationServiceImpl implements IntegerationService {
	Long getTime(){
		return System.currentTimeMillis();
	}
	@Autowired 
	HabitService habitService;
	@Autowired
	UserHabitService userHabitService;
	private Logger logger = Logger.getLogger(IntegerationServiceImpl.class);
	@Override
	public void getAll() {
		Integer lastid = 0;
		Long start = System.currentTimeMillis();
		logger.debug("find all start");
		List<Habit> habits = habitService.findAll(true,lastid,20,true);
		Long current = System.currentTimeMillis();
		logger.debug("find all end: "+(System.currentTimeMillis()-start));
		System.out.println("gethabits "+(current-start));
		Long uid =1671130932L;
		for(Habit e:habits){
			Integer habitId = e.getId();
			Long s1 = System.currentTimeMillis();
			UserHabit uh = userHabitService.getUserHabit(uid, habitId);
			Long s2 = System.currentTimeMillis();
			System.out.println("\tuserHabitService.getUserHabit(uid, habitId) "+(s2-s2));
			int friendJoinNum=0;
			if(uid !=null)
			{
				logger.debug("uid "+uid+" hid "+habitId);
				Long s3= getTime();
				 friendJoinNum = userHabitService.countNotDeletedUserHabits(uid, habitId);
				 System.out.println("\tuserHabitService.countNotDeletedUserHabits "+(getTime()-s3));
				 
				 }
			Long s4 = getTime();
//			habitService.findParticipateNum(habitId);
			e.getActiveUserNum();
			 System.out.println("\thabitService.findParticipateNum "+(getTime()-s4));

			Set<String> strSet = new HashSet<String>();
			for(Flag flag :habitService.getFlags(habitId)){
				strSet.add(flag.getContent());
			}
		
		}
		System.out.println("in for "+ (System.currentTimeMillis()-current));
 		habits = habitService.findAll(true,lastid,20,true);
		System.out.println("gethabitlist time(ms) "+(System.currentTimeMillis()-start));
//
	}

	
}

package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.entity.Flag;
import com.ebupt.justholdon.server.database.entity.Habit;
import com.ebupt.justholdon.server.database.entity.HabitType;
import com.ebupt.justholdon.server.database.entity.PersistUnit;
import com.ebupt.justholdon.server.database.entity.UserField;
import com.ebupt.justholdon.server.database.service.FlagService;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.UserFieldService;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;

public class ServiceTest {

	UserFieldService userFieldService;
	HabitService habitService;
	FlagService flagService;
	UserService userService;
	UserHabitService userHabitService;
	
	@Before
	public void setUp() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		userFieldService = (UserFieldService) ctx.getBean("userFieldService");
		habitService = (HabitService) ctx.getBean("habitService");
		flagService = (FlagService) ctx.getBean("flagService");
		userService = (UserService) ctx.getBean("userService");
		userHabitService = (UserHabitService) ctx.getBean("userHabitService");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		int counts = 10;
		String avatar = "http://ww1.sinaimg.cn/mw690/7071999fjw1dudnh1bsk5j.jpg";
		String weibo = "weibokey";
		String deviceToken="deviceToken";
		List<Long> uids = new ArrayList<Long>();
		List<Integer> hids = new ArrayList<Integer>();
		List<Integer> fids = new ArrayList<Integer>();

		
		String [] groupNames = {"生活类","养生类","奋进类"};
		String [] flagName={"减肥","成功","李开复","方sir","健康","活泼泼","热辣辣","哟嘻嘻","唔哈哈"};
		String[] userNames = {"张三","李四","王五","一一","二二","三三","四四","五五","六六","七七","八八","九九","实时"};
		String[] habitNames = {"跑步","减肥","喝水","大笑","泪奔","我勒个擦","哟哟","好习惯","少喝咖啡","多吃白菜","少吃肉","读书","练字","骂娘"};
		Long id = 1L;
		for(int i = 0 ;i < counts;i++)
		{
			Long uid = userFieldService.save(new UserField()
									.setId(id).setUserName(userNames[i%userNames.length])
									.setAvatar(avatar).setWeiboKey(weibo).setDeviceToken(deviceToken).setPassword("passwd"));
			uids.add(uid);
			fids.add(
					flagService.save(new Flag().setContent(flagName[i%flagName.length]).setTarget("ALL"))
					);
			
			hids.add(habitService.save(new Habit()
								.setHabitName(habitNames[i%habitNames.length])
								.setGroupName(groupNames[i%groupNames.length])
								.setType(HabitType.SYSTEM)
								.setStages("{1,2,3}")
								.setUnit(PersistUnit.DAY))
								);
			id ++;
		}
		System.out.println(uids.get(0));
		System.out.println(userService.get(uids.get(0)).getUserName());
		
	}

}

package com.ebupt.justholdon.server.database.service.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ebupt.justholdon.server.database.entity.DeviceType;
import com.ebupt.justholdon.server.database.entity.NewestVersion;
import com.ebupt.justholdon.server.database.service.ApproveService;
import com.ebupt.justholdon.server.database.service.CheckInService;
import com.ebupt.justholdon.server.database.service.CommentService;
import com.ebupt.justholdon.server.database.service.EventService;
import com.ebupt.justholdon.server.database.service.HabitService;
import com.ebupt.justholdon.server.database.service.UserHabitService;
import com.ebupt.justholdon.server.database.service.UserService;
import com.ebupt.justholdon.server.database.service.VersionService;

public class VersionServiceImplTest {

	static VersionService vs ;
	static	{
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bean.xml");
		vs = (VersionService) ctx.getBean("versionService");
		
	}

	@Test
	public void testAddVersion() {
		 vs.addVersion(DeviceType.ANDRROID, "1.0.8", "各位好，习以为常v1.0.8版本发布~更新如下：\n1.增加自带闹钟，建议各位给每个习惯设上闹钟：）加强提醒~ 统一设置可以至侧边栏-设置-设置提醒中添加\n2.增加习惯阶段总结\n3.优化UI\n4.修复流刷新消失bug\n5.调整上传图片流程，为提高稳定性暂时去除获取本地图片的裁剪功能\n");
	}

}

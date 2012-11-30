package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.SystemInfo;
import java.util.Date;
import java.util.List;
public interface SystemInfoService extends GenericService<SystemInfo, Integer> {
	public Integer createSystemInformation(String content);
	public Integer createSystemInformation(String content,String extra,Date createDate);
	
	public List<SystemInfo> getUnreadedSystemInfo(Long uid);
	public List<SystemInfo> getUnreadedSystemInfo(Long uid,Integer start,Integer end);
	public List<SystemInfo> getAllSystemInfo(Long uid,Integer start,Integer end);
}

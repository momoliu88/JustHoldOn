package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.SystemInfo;
import com.ebupt.justholdon.server.database.entity.SystemInfoSended;

import java.util.Date;
import java.util.List;
import java.util.Set;
public interface SystemInfoService extends GenericService<SystemInfo, Integer> {
	//create systeminformation
	public Integer createSystemInformation(String content);
	public Integer createSystemInformation(String content,String extra,Date createDate);
	
	public Set<SystemInfoSended> getSendedSystemInfos(Integer id);
	public Integer countSendedSystemInfos(Integer id);
	
	public List<SystemInfo> getUnreadedSystemInfo(Long uid);
	public List<SystemInfo> getUnreadedSystemInfo(Long uid,Integer startId,Integer length,boolean after);
	//get all system information(read or unread)
	public List<SystemInfo> getAllSystemInfo(Integer startId,Integer length,boolean after);
	public void readASystemInfo(Long uid,Integer id);
}

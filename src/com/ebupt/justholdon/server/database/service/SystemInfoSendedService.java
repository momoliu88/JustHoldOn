package com.ebupt.justholdon.server.database.service;

import java.util.List;

import com.ebupt.justholdon.server.database.entity.SystemInfoSended;

public interface SystemInfoSendedService extends GenericService<SystemInfoSended, Integer> {
	//from uid,get messages'id which have sended to user
	List<Integer> getSendedInfos(Long uid);
}

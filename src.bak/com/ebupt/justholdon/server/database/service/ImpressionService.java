package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.Impression;

public interface ImpressionService extends GenericService<Impression,Integer>{
	public void addImpression(Long uidA,Long uidB,String content);
	public void addImpression(Long uidA,Long uidB,Impression impression);
}

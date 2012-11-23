package com.ebupt.justholdon.server.service;

import java.util.List;

import com.ebupt.justholdon.server.entity.Flag;
import com.ebupt.justholdon.server.entity.User;

public interface FlagService extends GenericService<Flag,Integer>{
	public List<Flag> findAll(boolean byHot);
	public void addUser(User user,Flag flag);
	public void removeUser(User user,Flag flag);
	
}

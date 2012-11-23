package com.ebupt.justholdon.server.service;

import com.ebupt.justholdon.server.entity.UserField;

public interface UserFieldService extends GenericService<UserField, Integer> {
	public Integer createAndSave(String userName, String pass, int uid,
			String avatar, String deviceToken, String token);

}

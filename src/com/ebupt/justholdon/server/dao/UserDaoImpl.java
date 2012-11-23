package com.ebupt.justholdon.server.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.entity.User;


@Repository("userDao")
@Transactional(readOnly=true)
public class UserDaoImpl extends GenericHibernateDaoImpl<User,Integer>  implements UserDao {
	
	public UserDaoImpl(Class<User> type) {
		super(type);
	}
	public UserDaoImpl()
	{
		this(User.class);
	}
 	private final String tableName = "com.ebupt.justholdon.server.entity.User";	 
	@Override
	public List<User> findByUserName(String name) {
		@SuppressWarnings("unchecked")
		List<User> users = currentSession()
				.createQuery(
						"from " + tableName + " user where user.userName = ? ")
				.setString(0, name).list();
		return users;
	}
	@Override
	public int getFriends(Integer id) {
		return get(id).getFriends().size();
	}
}

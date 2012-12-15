package com.ebupt.justholdon.server.database.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.User;


@Repository("userDao")
@Transactional
public class UserDaoImpl extends GenericHibernateDaoImpl<User,Long>  implements UserDao {
	
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
	public int getFriends(Long id) {
		return get(id).getFriends().size();
	}
	@Override
	public User get(Long id) {
		return (User) currentSession().get(User.class,id);
	}
}

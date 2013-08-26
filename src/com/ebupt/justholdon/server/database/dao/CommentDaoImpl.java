package com.ebupt.justholdon.server.database.dao;
 
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.Comment;

@Repository("commentDao")
@Transactional
public class CommentDaoImpl extends GenericHibernateDaoImpl<Comment, Integer> implements
		CommentDao {

	public CommentDaoImpl(Class<Comment> type) {
		super(type);
	}
	public CommentDaoImpl()
	{
		this(Comment.class);
	}

}

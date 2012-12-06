package com.ebupt.justholdon.server.database.service;

import java.util.Date;
import java.util.List;

import com.ebupt.justholdon.server.database.entity.Comment;

public interface CommentService extends GenericService<Comment, Integer> {
	/* create comment from sponsor to receiver*/
	public Integer createComment(Long sponsor,Long receiver,Integer checkInId,String comment);
	public Integer createComment(Long sponsor,Long receiver,Integer checkInId,Date date,String comment);
	/* delete comment*/
	public void deleteComment(Integer commentId);
	/* get comment for a checkin*/
	public List<Comment> getCommentsForCheckin(Integer checkInId);
	public List<Comment> getCommentsForCheckin(Integer checkInId,Integer start,Integer end);
	
}

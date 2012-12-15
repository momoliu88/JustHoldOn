package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.CheckInDao;
import com.ebupt.justholdon.server.database.dao.CommentDao;
import com.ebupt.justholdon.server.database.dao.UserDao;
import com.ebupt.justholdon.server.database.entity.CheckIn;
import com.ebupt.justholdon.server.database.entity.Comment;
import com.ebupt.justholdon.server.database.entity.GenericComparator;
import com.ebupt.justholdon.server.database.entity.User;

@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CheckInDao checkInDao;
	@Autowired
	private EventService eventService;
	@Override
	public Integer save(Comment newInstance) {
		return commentDao.save(newInstance);
	}

	@Override
	public Comment get(Integer id) {
		return commentDao.get(id);
	}

	@Override
	public void update(Comment transientObject) {
		commentDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return commentDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
		Comment comment = commentDao.get(id);
		comment.getSponsor().getSponsorComments().remove(comment);
		User receiver = comment.getReceiver();
		comment.getCheckIn().getComments().remove(comment);
		if(null != receiver)
			receiver.getReceiverComments().remove(comment);
		comment.setSponsor(null);
		comment.setReceiver(null);
		comment.setCheckIn(null);
		commentDao.delete(comment);	}

	@Override
	public List<Comment> findAll() {
		return commentDao.findAll();
	}

	@Override
	public Integer createComment(Long sponsor, Long receiver,
			Integer checkInId, String comment) {
		return createComment(sponsor,receiver,checkInId,null,comment);
	}

	@Override
	public Integer createComment(Long sponsor, Long receiver,
			Integer checkInId, Date date, String comment) {
		User sponsorUser = userDao.get(sponsor);
		User receiverUser = null;
		if(null != receiver)
			receiverUser = userDao.get(receiver);
		CheckIn checkIn = checkInDao.get(checkInId);
	
		Comment commentObj = new Comment().setCheckIn(checkIn)
				.setSponsor(sponsorUser).setReceiver(receiverUser)
				.setComment(comment);
		if(null != date)
			commentObj.setCreateTime(date);
		System.out.println("time "+commentObj.getCreateTime());
		return commentDao.save(commentObj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getCommentsForCheckin(Integer checkInId) {
		CheckIn checkIn = checkInDao.get(checkInId);
		List<Comment> comments = new ArrayList<Comment>(checkIn.getComments());
		Collections.sort(comments,GenericComparator.getInstance().getDateComparator());
		return comments;
	}

	@Override
	public List<Comment> getCommentsForCheckin(Integer checkInId,Integer startId,Integer length,boolean after){
	//	return Utils.subList(start, end, getCommentsForCheckin(checkInId));
		return Utils.cutEventList(getCommentsForCheckin(checkInId), startId, length, after);
	}

	@Override
	public void deleteComment(Integer commentId) {
		delete(commentId);
	}

	@Override
	public void createCommentAndCreateInformation(Long sponsor,
			Long receiver, Integer checkInId, String comment, String content) {
		Integer commentId = createComment(sponsor,receiver,checkInId,comment);
		Integer hid = checkInDao.get(checkInId).getHabit().getId();
		eventService.createHabitInfo(sponsor, receiver, hid, EventType.COMMENT_CHECKIN, content, commentId);
	}

	@Override
	public void createCommentAndCreateInformation(Long sponsor,
			Long receiver, Integer checkInId, Date date, String comment,
			String content) {
		Integer commentId = createComment(sponsor,receiver,checkInId,date,comment);
		Integer hid = checkInDao.get(checkInId).getHabit().getId();
		eventService.createHabitInfo(sponsor, receiver, hid, EventType.COMMENT_CHECKIN, content, commentId);
	}

}

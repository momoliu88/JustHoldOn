package com.ebupt.justholdon.server.database.service;

//import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.CommentDao;
import com.ebupt.justholdon.server.database.entity.CheckIn;
import com.ebupt.justholdon.server.database.entity.Comment;
import com.ebupt.justholdon.server.database.entity.User;

@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserService userService;
//	@Autowired
//	private CheckInDao checkInDao;
	@Autowired
	private EventService eventService;
	@Autowired
	private CheckInService checkInService;

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
		if (null != receiver)
			receiver.getReceiverComments().remove(comment);
		comment.setSponsor(null);
		comment.setReceiver(null);
		comment.setCheckIn(null);
		//commentDao.update(comment);
		commentDao.delete(comment);
	}

	@Override
	public List<Comment> findAll() {
		return commentDao.findAll();
	}

	@Override
	public Integer createComment(Long sponsor, Long receiver,
			Integer checkInId, String comment) {
		return createComment(sponsor, receiver, checkInId, null, comment);
	}

	@Override
	public Integer createComment(Long sponsor, Long receiver,
			Integer checkInId, Date date, String comment) {
		User sponsorUser = userService.get(sponsor);
		User receiverUser = null;
		if (null != receiver)
			receiverUser = userService.get(receiver);
		CheckIn checkIn = checkInService.get(checkInId);

		Comment commentObj = new Comment().setCheckIn(checkIn)
				.setSponsor(sponsorUser).setReceiver(receiverUser)
				.setComment(comment);
		if (null != date)
			commentObj.setCreateTime(date);
		return commentDao.save(commentObj);
	}
//
//	@Override
//	public List<Comment> getCommentsForCheckin(Integer checkInId) {
//		//CheckIn checkIn = checkInDao.get(checkInId);
//		return  new ArrayList<Comment>(checkInService.getComments(checkInId));
//	}

//	@Override
//	public List<Comment> getCommentsForCheckin(Integer checkInId,
//			Integer startId, Integer length, boolean after) {
//		// return Utils.subList(start, end, getCommentsForCheckin(checkInId));
//		return Utils.cutEventList(getCommentsForCheckin(checkInId), startId,
//				length, after, false);
//	}

	@Override
	public void deleteComment(Integer commentId) {
		delete(commentId);
	}

	@Override
	public void createCommentAndCreateInformation(Long sponsor,
			Long receiverId, Integer checkInId, String comment, String content) {
		// createComment(sponsor,receiverId,checkInId,comment);
		// CheckIn ck =checkInDao.get(checkInId);
		// Integer hid = ck.getHabit().getId();
		// eventService.createHabitInfo(sponsor,ck.getUser().getId(), hid,
		// EventType.COMMENT_CHECKIN, content, checkInId);
		// if(null != receiverId)
		// eventService.createHabitInfo(sponsor,receiverId, hid,
		// EventType.REPLY, content, checkInId);
		createCommentAndCreateInformation(sponsor, receiverId, checkInId, null,
				comment, content);

	}

	@Override
	public void createCommentAndCreateInformation(Long sponsor, Long receiver,
			Integer checkInId, Date date, String comment, String content) {
		if (sponsor == null)
			return;
		createComment(sponsor, receiver, checkInId, date, comment);
		CheckIn ck = checkInService.get(checkInId);
		Integer hid = ck.getHabit().getId();
		if (!sponsor.equals(ck.getUser().getId()))
			eventService.createHabitInfo(sponsor, ck.getUser().getId(), hid,
					EventType.COMMENT_CHECKIN, content, checkInId);
		if (receiver != null && !sponsor.equals(receiver)
				&& !receiver.equals(ck.getUser().getId()))
			eventService.createHabitInfo(sponsor, receiver, hid,
					EventType.REPLY, content, checkInId);

		// eventService.createHabitInfo(sponsor, ck.getUser().getId(), hid,
		// EventType.COMMENT_CHECKIN, content, checkInId);

	}

	@Override
	public void saveOrUpdate(Comment transientObject) {
		commentDao.saveOrUpdate(transientObject);		
	}

}

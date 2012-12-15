package com.ebupt.justholdon.server.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.Comparator;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {
	public Comment() {
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "sponsor")
	private User sponsor;
	@ManyToOne
	@JoinColumn(name = "receiver")
	private User receiver = null;
	@ManyToOne
	@JoinColumn(name = "checkInId")
	private CheckIn checkIn;
	private Date createTime = new Date();
	private String comment;

	private static Comparator<Comment> dateComparator = new Comparator<Comment>() {
		@Override
		public int compare(Comment arg0, Comment arg1) {
			return (int) (arg1.getCreateTime().getTime() - arg0.getCreateTime()
					.getTime());
		}

	};

	public static Comparator<Comment> getDateComparator() {
		return dateComparator;
	}

	public int getId() {
		return id;
	}

	public Comment setId(int id) {
		this.id = id;
		return this;
	}

	public User getSponsor() {
		return sponsor;
	}

	public Comment setSponsor(User sponsor) {
		this.sponsor = sponsor;
		if (null != sponsor)
			sponsor.getSponsorComments().add(this);
		return this;
	}

	public User getReceiver() {
		return receiver;
	}

	public Comment setReceiver(User receiver) {
		this.receiver = receiver;
		if (null != receiver)
			receiver.getReceiverComments().add(this);
		return this;
	}

	public CheckIn getCheckIn() {
		return checkIn;
	}

	// add comments
	public Comment setCheckIn(CheckIn checkIn) {
		this.checkIn = checkIn;
		if (null != checkIn)
			checkIn.getComments().add(this);
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Comment setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public Comment setComment(String comment) {
		this.comment = comment;
		return this;
	}

}

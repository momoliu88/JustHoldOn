package com.ebupt.justholdon.server.database.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

//import java.util.Comparator;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment implements BaseEntity<Integer>{
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
	private String comment="";
	private Date modifyTime = new Date();

	public Integer getId() {
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
	@Override
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
	@Override
	public Date getModifyTime() {
		return modifyTime;
	}
	@Override
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(null != o && o.getClass() == this.getClass()))
			return false;
		final Comment other = (Comment) o;
		return other.getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		int result = 31;
		result += this.getId() == null ? 0 : this.getId().hashCode();
		return result;
	}

	@Override
	@PreUpdate
	public void onUpdate() {
		setModifyTime(new Date());
	}
}

package com.ebupt.justholdon.server.database.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "approves")
public class Approve implements BaseEntity<Integer> {
	public Approve() {
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "checkin", nullable = false)
	private CheckIn checkin;
	private Date createTime = new Date();
	private Date modifyTime = new Date();

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public Approve setUser(User user) {
		this.user = user;
		if (null != user)
			user.getApproves().add(this);
		return this;
	}

	public CheckIn getCheckin() {
		return checkin;
	}

	public Approve setCheckin(CheckIn checkin) {
		this.checkin = checkin;
		if (null != checkin)
			checkin.getApproves().add(this);
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Approve setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	/*
	 * using approve's id to compare.
	 * */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(null != o && o.getClass() == this.getClass()))
			return false;
		final Approve other = (Approve) o;
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

package com.ebupt.justholdon.server.database.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "invitedUsers")
public class InvitedUser implements BaseEntity<Integer>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Long sponsorId;
	private String sponsorName;
	private String invitedUserName;
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	
	public Long getSponsorId() {
		return sponsorId;
	}

	public InvitedUser setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
		return this;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public InvitedUser setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
		return this;
	}

	public String getInvitedUserName() {
		return invitedUserName;
	}

	public InvitedUser setInvitedUserName(String invitedUserName) {
		this.invitedUserName = invitedUserName;
		return this;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public InvitedUser setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public Date getModifyTime() {
		return modifyTime;
	}

	@Override
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	@PreUpdate
	@Override
	public void onUpdate() {
		setModifyTime(new Date());
	}

}

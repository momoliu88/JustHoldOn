package com.ebupt.justholdon.server.database.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "system_info_sended_tb")
public class SystemInfoSended implements BaseEntity<Long>{
	public SystemInfoSended(){};
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "systemInfoId", nullable = false)
	private SystemInfo systemInfo;
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	private Date sendTime = new Date();
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public Date getModifyTime() {
		return modifyTime;
	}
	@Override
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public SystemInfo getSystemInfo() {
		return systemInfo;
	}
	public SystemInfoSended setSystemInfo(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
		if(null != systemInfo)
		systemInfo.getSendedSystemInfos().add(this);
		return this;
	}
	public User getUser() {
		return user;
	}
	public SystemInfoSended setUser(User user) {
		this.user = user;
		if(null != user)
			user.getReceiveSystemInfos().add(this);
		return this;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public SystemInfoSended setSendTime(Date sendTime) {
		this.sendTime = sendTime;
		return this;
	}
}

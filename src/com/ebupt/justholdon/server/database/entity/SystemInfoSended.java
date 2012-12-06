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
public class SystemInfoSended {
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
	
	public Long getId() {
		return id;
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

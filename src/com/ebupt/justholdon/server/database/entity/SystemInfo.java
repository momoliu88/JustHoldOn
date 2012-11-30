package com.ebupt.justholdon.server.database.entity;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "system_infos")
public class SystemInfo {
	public SystemInfo() {
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String content;
	private String extra;
	private Date createTime = new Date();

	@OneToMany(mappedBy="systemInfo",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<SystemInfoSended> sendedSystemInfos = new HashSet<SystemInfoSended>();
	public Integer getId() {
		return id;
	}
	private static Comparator<SystemInfo> dateComparator = new Comparator<SystemInfo>() {
		@Override
		public int compare(SystemInfo info1, SystemInfo info2) {
			return (int) (info2.getCreateTime().getTime()- info1.getCreateTime().getTime());
		}
	};
	
	public String getContent() {
		return content;
	}

	public static Comparator<SystemInfo> getDateComparator() {
		return dateComparator;
	}

	public SystemInfo setContent(String content) {
		this.content = content;
		return this;
	}

	public String getExtra() {
		return extra;
	}

	public SystemInfo setExtra(String extra) {
		this.extra = extra;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public SystemInfo setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public Set<SystemInfoSended> getSendedSystemInfos() {
		return sendedSystemInfos;
	}

	public SystemInfo setSendedSystemInfos(Set<SystemInfoSended> sendedSystemInfos) {
		this.sendedSystemInfos = sendedSystemInfos;
		return this;
	}
}

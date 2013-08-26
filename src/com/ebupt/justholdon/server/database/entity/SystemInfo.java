package com.ebupt.justholdon.server.database.entity;

//import java.util.Comparator;
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
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "system_infos")
public class SystemInfo implements BaseEntity<Integer>{
	public SystemInfo() {
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String content ="";
	private String extra = "";
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	private Boolean isSystemInfo = true;
	
	@OneToMany(mappedBy="systemInfo",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	private Set<SystemInfoSended> sendedSystemInfos = new HashSet<SystemInfoSended>();
	public Integer getId() {
		return id;
	}
//	private static Comparator<SystemInfo> dateComparator = new Comparator<SystemInfo>() {
//		@Override
//		public int compare(SystemInfo info1, SystemInfo info2) {
//			return (int) (info2.getCreateTime().getTime()- info1.getCreateTime().getTime());
//		}
//	};
	@Override
	public Date getModifyTime() {
		return modifyTime;
	}
	@Override
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getContent() {
		return content;
	}

//	public static Comparator<SystemInfo> getDateComparator() {
//		return dateComparator;
//	}

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

	public Boolean getIsSystemInfo() {
		return isSystemInfo;
	}

	public void setIsSystemInfo(Boolean isSystemInfo) {
		this.isSystemInfo = isSystemInfo;
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
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(null != o && o.getClass() == this.getClass()))
			return false;
		final SystemInfo other = (SystemInfo) o;
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

package com.ebupt.justholdon.server.database.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "versions")
public class Version implements BaseEntity<Integer>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String version = "";
	private DeviceType deviceType = null;
	private String description = "";
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	
	public String getVersion() {
		return version;
	}
	public Integer getId() {
		return id;
	}

	public Version setId(Integer id) {
		this.id = id;
		return this;
	}
	public Version setVersion(String version) {
		this.version = version;
		return this;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public Version setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Version setDescription(String description) {
		this.description = description;
		return this;
	}

	public Date getModifyTime() {
		return modifyTime;
	}
	@Override
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!((null != obj) && (obj.getClass() == this.getClass())))
			return false;
		final Version other = (Version) obj;
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

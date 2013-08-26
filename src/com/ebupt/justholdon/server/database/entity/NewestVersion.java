package com.ebupt.justholdon.server.database.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "newestVersions")
public class NewestVersion implements BaseEntity<Integer>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	@JoinColumn(name = "version")
	private Version version;
	private DeviceType type = null;
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	public Integer getId() {
			return id;
	}
	 
	public DeviceType getType() {
		return type;
	}

	public NewestVersion setType(DeviceType type) {
		this.type = type;
		return this;
	}

	public Version getVersion() {
		return version;
	}
	public NewestVersion setVersion(Version version) {
		this.version = version;
		return this;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public NewestVersion setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	@Override
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public NewestVersion setId(Integer id) {
		this.id = id;
		return this;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(null != o && o.getClass() == this.getClass()))
			return false;
		final NewestVersion other = (NewestVersion) o;
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

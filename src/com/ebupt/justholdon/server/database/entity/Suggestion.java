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
@Table(name = "suggestions")
public class Suggestion implements BaseEntity<Integer>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	private String content = "";
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	public Integer getId() {
		return id;
	}
	public Suggestion setId(Integer id) {
		this.id = id;
		return this;
	}
	public User getUser() {
		return user;
	}
	public Suggestion setUser(User user) {
		this.user = user;
		if(user != null)
			user.getSponsorSuggestions().add(this);
		return this;
	}
	public String getContent() {
		return content;
	}
	public Suggestion setContent(String content) {
		this.content = content;
		return this;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Suggestion setCreateTime(Date createTime) {
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
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(null != o && o.getClass() == this.getClass()))
			return false;
		final Suggestion other = (Suggestion) o;
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

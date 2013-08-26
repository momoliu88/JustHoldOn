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
@Table(name = "impressions")
public class Impression implements BaseEntity<Integer> {
	public Impression() {
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "userAId", nullable = false)
	private User sponsor;
	@ManyToOne
	@JoinColumn(name = "userBId", nullable = false)
	private User receiver;
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	private String content ="";

	@Override
	public Integer getId() {
		return id;
	}

	public Impression setId(int id) {
		this.id = id;
		return this;
	}

	public User getSponsor() {
		return sponsor;
	}

	public Impression setSponsor(User sponsor) {
		this.sponsor = sponsor;
		if (sponsor != null)
			sponsor.getSponseImpressiones().add(this);
		return this;
	}

	public User getReceiver() {
		return receiver;
	}

	public Impression setReceiver(User receiver) {
		this.receiver = receiver;
		if (receiver != null)
			receiver.getReceivedImpressiones().add(this);
		return this;
	}
	@Override
	public Date getCreateTime() {
		return createTime;
	}

	public Impression setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Impression setContent(String content) {
		this.content = content;
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
		final Impression other = (Impression) o;
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

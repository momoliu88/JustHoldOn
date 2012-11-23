package com.ebupt.justholdon.server.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "impressions")
public class Impression {
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
	private Date createTime;
	private String content;

	public int getId() {
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
		return this;
	}

	public User getReceiver() {
		return receiver;
	}

	public Impression setReceiver(User receiver) {
		this.receiver = receiver;
		return this;
	}

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

}

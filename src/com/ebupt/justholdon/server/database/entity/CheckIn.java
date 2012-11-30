package com.ebupt.justholdon.server.database.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "checkin")
public class CheckIn {
	public CheckIn() {
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "checkinTime")
	private Date checkInTime = new Date();
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "habitId", nullable = false)
	private Habit habit;
	private String description;
	private String picUrl;
	private String audioUrl;
	private String location;

	@OneToMany(mappedBy = "checkin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Approve> approves = new HashSet<Approve>();

	@OneToMany(mappedBy = "checkIn", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Comment> comments = new HashSet<Comment>();
	public int getId() {
		return id;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public CheckIn setComments(Set<Comment> comments) {
		this.comments = comments;
		return this;
	}

	public CheckIn setId(int id) {
		this.id = id;
		return this;
	}

	public Date getCheckInTime() {
		return checkInTime;
	}

	public CheckIn setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
		return this;
	}

	public User getUser() {
		return user;
	}

	public CheckIn setUser(User user) {
		this.user = user;
		return this;
	}

	public Habit getHabit() {
		return habit;
	}

	public CheckIn setHabit(Habit habit) {
		this.habit = habit;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CheckIn setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public CheckIn setPicUrl(String picUrl) {
		this.picUrl = picUrl;
		return this;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public CheckIn setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
		return this;
	}

	public String getLocation() {
		return location;
	}

	public CheckIn setLocation(String location) {
		this.location = location;
		return this;
	}

	public Set<Approve> getApproves() {
		return approves;
	}

	public CheckIn setApproves(Set<Approve> approves) {
		this.approves = approves;
		return this;
	}
}

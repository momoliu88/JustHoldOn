package com.ebupt.justholdon.server.database.entity;

import java.util.ArrayList;
//import java.util.Comparator;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OrderBy;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "checkin")
public class CheckIn implements BaseEntity<Integer> {
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
	private String description="";
	private String picUrl="";
	private String audioUrl="";
	private String location="";
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	private Boolean isDeleted = false;

	/*
	 * HAVE NOTHING TO EXPLAIN THIS FUCKIN THINGS
	 * */
	@OneToMany(mappedBy = "checkin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	@OrderBy("createTime DESC,id DESC")
	private List<Approve> approves = new ArrayList<Approve>();

	@OneToMany(mappedBy = "checkIn", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	@OrderBy("createTime DESC,id DESC")
	private List<Comment> comments = new ArrayList<Comment>();

	private static Comparator<CheckIn> dateComparator = new Comparator<CheckIn>() {
		@Override
		public int compare(CheckIn arg0, CheckIn arg1) {
			Long comp = arg1.getCheckInTime().getTime()
					- arg0.getCheckInTime().getTime();

			if (0 == comp)
				return (int) (arg1.getId() - arg0.getId());
			if (comp > 0)
				return 1;
			return -1;
		}

	};
	private static int countCommentsAndApproves(CheckIn ck){
		return ck.getApproves().size()+ck.getComments().size();
	}
	public static Comparator<CheckIn> hotCheckInComp = new Comparator<CheckIn>() {
		@Override
		public int compare(CheckIn arg0, CheckIn arg1) {
			return countCommentsAndApproves(arg1)-countCommentsAndApproves(arg0);
		}

	};
	
	public Integer getId() {
		return id;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public CheckIn setComments(List<Comment> comments) {
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
		if (null != user)
		{
			user.getCheckIns().add(this);
			user.setCheckInNums(user.getCheckInNums()+1);
		}
		return this;
	}

	public Habit getHabit() {
		return habit;
	}

	public CheckIn setHabit(Habit habit) {
		this.habit = habit;
		if (null != habit)
			habit.getCheckIns().add(this);
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

	public List<Approve> getApproves() {
		return approves;
	}

	public CheckIn setApproves(List<Approve> approves) {
		this.approves = approves;
		return this;
	}

	public static Comparator<CheckIn> getDateComparator() {
		return dateComparator;
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public CheckIn setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(null != o && o.getClass() == this.getClass()))
			return false;
		final CheckIn other = (CheckIn) o;
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

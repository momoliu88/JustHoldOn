package com.ebupt.justholdon.server.database.entity;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "weeklySummary")
public class WeeklySummary {
	public WeeklySummary(){};
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	private int goalCheckInTimes;
	private int actualCheckInTimes;
	private Date createTime = new Date();
	private String comment;
	public int getId() {
		return id;
	}
	private static Comparator<WeeklySummary> dateComparator = new Comparator<WeeklySummary>()
			{
				@Override
				public int compare(WeeklySummary arg0, WeeklySummary arg1) {
					return (int) (arg1.getCreateTime().getTime() - arg0.getCreateTime().getTime());
				}
		
			};
	public User getUser() {
		return user;
	}
	public WeeklySummary setUser(User user) {
		this.user = user;
		if(null != user)
			user.getWeeklySummaries().add(this);
		return this;
	}
	public int getGoalCheckInTimes() {
		return goalCheckInTimes;
	}
	public WeeklySummary setGoalCheckInTimes(int goalCheckInTimes) {
		this.goalCheckInTimes = goalCheckInTimes;
		return this;
	}
	public int getActualCheckInTimes() {
		return actualCheckInTimes;
	}
	public WeeklySummary setActualCheckInTimes(int actualCheckInTimes) {
		this.actualCheckInTimes = actualCheckInTimes;
		return this;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public WeeklySummary setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}
	public String getComment() {
		return comment;
	}
	public WeeklySummary setComment(String comment) {
		this.comment = comment;
		return this;
	}
	public static Comparator<WeeklySummary> getDateComparator() {
		return dateComparator;
	}
}

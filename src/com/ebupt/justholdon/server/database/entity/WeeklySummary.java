package com.ebupt.justholdon.server.database.entity;

//import java.util.Comparator;
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
@Table(name = "weeklySummary")
public class WeeklySummary implements BaseEntity<Integer>{
	public WeeklySummary(){};
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	private int goalCheckInTimes = 0;
	private int actualCheckInTimes = 0;
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	private String comment = "";
	
	@Override
	public Integer getId() {
		return id;
	}
//	private static Comparator<WeeklySummary> dateComparator = new Comparator<WeeklySummary>()
//			{
//				@Override
//				public int compare(WeeklySummary arg0, WeeklySummary arg1) {
//					return (int) (arg1.getCreateTime().getTime() - arg0.getCreateTime().getTime());
//				}
//		
//			};
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
//	public static Comparator<WeeklySummary> getDateComparator() {
//		return dateComparator;
//	}
	@Override
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
		final WeeklySummary other = (WeeklySummary) obj;
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

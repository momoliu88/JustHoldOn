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

import com.ebupt.justholdon.server.database.service.HabitState;

@Entity
@Table(name = "userHabits")
public class UserHabit {
	public UserHabit() {
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "habitId", nullable = false)
	private Habit habit;
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	private AlarmType needAlarm;
	private Date alarmTime;
	private Date createTime = new Date();
	private Date endTime;
	private String currentStage = "INIT";
	private HabitState stat = HabitState.ING;
	private Date modifyTime;
	private PrivilegeType privilege = PrivilegeType.ALL;
	
	
	private static Comparator<UserHabit> dateComparator = new Comparator<UserHabit>() {
		@Override
		public int compare(UserHabit arg1, UserHabit arg2) {
			return (int) (arg2.getCreateTime().getTime() - arg1.getCreateTime().getTime());
		}
	};
	public int getId() {
		return id;
	}

	public UserHabit setId(int id) {
		this.id = id;return this;
	}

	public Habit getHabit() {
		return habit;
	}

	public UserHabit setHabit(Habit habit) {
		this.habit = habit;
		if(null != habit)
			habit.getUserHabits().add(this);
		return this;
	}

	public User getUser() {
		return user;
	}

	public UserHabit setUser(User user) {
		this.user = user;
		if(null != user)
			user.getUserHabits().add(this);
		return this;
	}

	public AlarmType getNeedAlarm() {
		return needAlarm;
	}

	public UserHabit setNeedAlarm(AlarmType needAlarm) {
		this.needAlarm = needAlarm;
		return this;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public UserHabit setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public UserHabit setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}


	public Date getEndTime() {
		return endTime;
	}

	public UserHabit setEndTime(Date endTime) {
		this.endTime = endTime;
		return this;
	}

	public HabitState getStat() {
		return stat;
	}

	public UserHabit setStat(HabitState stat) {
		this.stat = stat;
		return this;
	}

	public String getCurrentStage() {
		return currentStage;
	}

	public UserHabit setCurrentStage(String currentStage) {
		this.currentStage = currentStage;
		return this;
	}


	public Date getModifyTime() {
		return modifyTime;
	}

	public UserHabit setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;return this;
	}

	public PrivilegeType getPrivilege() {
		return privilege;
	}

	public UserHabit setPrivilege(PrivilegeType privilege) {
		this.privilege = privilege;
		return this;
	}

	public static Comparator<UserHabit> getDateComparator() {
		return dateComparator;
	}
	
	 
}

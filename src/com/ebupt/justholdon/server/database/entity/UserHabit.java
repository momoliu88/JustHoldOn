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

import com.ebupt.justholdon.server.database.service.HabitState;

@Entity
@Table(name = "userHabits")
public class UserHabit implements BaseEntity<Integer> {
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
	private AlarmType needAlarm = AlarmType.NOTALARM;
	private Date alarmTime;
	private Date createTime = new Date();
	private Date endTime = null;
	private String currentStage = "INIT";
	private HabitState stat = HabitState.ING;

	private Date modifyTime = new Date();
	private int times = 0;
	private PrivilegeType privilege = PrivilegeType.ALL;
	private PersistUnit unit = PersistUnit.DAY;
	private String stages = "";
	private String description = "";

	// private static Comparator<UserHabit> dateComparator = new
	// Comparator<UserHabit>() {
	// @Override
	// public int compare(UserHabit arg1, UserHabit arg2) {
	// return (int) (arg2.getCreateTime().getTime() -
	// arg1.getCreateTime().getTime());
	// }
	// };

	public String getDescription() {
		return description;
	}

	public UserHabit setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public UserHabit setId(int id) {
		this.id = id;
		return this;
	}

	public Habit getHabit() {
		return habit;
	}

	public UserHabit setHabit(Habit habit) {
		this.habit = habit;
		if (null != habit)
		{
			habit.getUserHabits().add(this);
//			habit.setActiveUserNum(habit.getActiveUserNum()+1);
		}
		return this;
	}

	public User getUser() {
		return user;
	}

	public UserHabit setUser(User user) {
		this.user = user;
		if (null != user)
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
	@Override
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public PrivilegeType getPrivilege() {
		return privilege;
	}

	public UserHabit setPrivilege(PrivilegeType privilege) {
		this.privilege = privilege;
		return this;
	}

	// public static Comparator<UserHabit> getDateComparator() {
	// return dateComparator;
	// }

	public int getTimes() {
		return times;
	}

	public UserHabit setTimes(int times) {
		this.times = times;
		return this;
	}

	public PersistUnit getUnit() {
		return unit;
	}

	public UserHabit setUnit(PersistUnit unit) {
		this.unit = unit;
		return this;
	}

	public String getStages() {
		return stages;
	}

	public UserHabit setStages(String stages) {
		this.stages = stages;
		return this;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!((null != obj) && (obj.getClass() == this.getClass())))
			return false;
		final UserHabit other = (UserHabit) obj;
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

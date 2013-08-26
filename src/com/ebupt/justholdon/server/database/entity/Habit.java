package com.ebupt.justholdon.server.database.entity;

import java.util.Comparator;
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

//import com.ebupt.justholdon.server.database.service.HabitState;

@Entity
@Table(name = "habits")
public class Habit implements BaseEntity<Integer>{
	public Habit() {
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "habitId")
	private int id;
	private String habitName ="";
	private HabitType type =HabitType.SYSTEM;
	private PersistUnit unit = PersistUnit.DAY;
 	private String stages ="";
	private String groupName="";
	private String description = "";
	private Integer times = 0;
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	private Long createUid = null;
	private UserHabitRestrict limitCond = UserHabitRestrict.NONE;
	/**
	 * update habits set activeUserNum=0 where activeUserNum is null;
	 * update habits A set activeUserNum= (select count(*) from userHabits B where A.habitId=B.habitId and B.endTime is NULL group by B.habitId);
	 */
	private Integer activeUserNum = 0;
	@OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	private Set<CheckIn> checkIns = new HashSet<CheckIn>();
	
	@OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	private Set<Event> events = new HashSet<Event>();
	
	@OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	private Set<UserHabit> userHabits = new HashSet<UserHabit>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	private Set<Flag> flags = new HashSet<Flag>();
	
	public Integer getTimes() {
		return times;
	}

	public Habit setTimes(Integer times) {
		this.times = times;
		return this;
	}

	public Long getCreateUid() {
		return createUid;
	}

	public Habit setCreateUid(Long createUid) {
		this.createUid = createUid;
		return this;
	}

	public Set<Flag> getFlags() {
		return flags;
	}

	public void setFlags(Set<Flag> flags) {
		this.flags = flags;
	}

	public Set<UserHabit> getUserHabits() {
		return userHabits;
	}

	public void setUserHabits(Set<UserHabit> userHabits) {
		this.userHabits = userHabits;
	}
	@Override
	public Integer getId() {
		return id;
	}

	public Habit setId(int id) {
		this.id = id;
		return this;
	}

	public String getHabitName() {
		return habitName;
	}

	public Habit setHabitName(String habitName) {
		this.habitName = habitName;
		return this;
	}

	public HabitType getType() {
		return type;
	}

	public Habit setType(HabitType type) {
		this.type = type;
		return this;
	}

	public PersistUnit getUnit() {
		return unit;
	}

	public Habit setUnit(PersistUnit unit) {
		this.unit = unit;
		return this;
	}

	 
	public String getStages() {
		return stages;
	}

	public Habit setStages(String stages) {
		this.stages = stages;
		return this;
	}

	public String getGroupName() {
		return groupName;
	}

	public Habit setGroupName(String groupName) {
		this.groupName = groupName;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Habit setDescription(String description) {
		this.description = description;
		return this;
	}

	public Set<CheckIn> getCheckIns() {
		return checkIns;
	}

	public Habit setCheckIns(Set<CheckIn> checkIns) {
		this.checkIns = checkIns;
		return this;
	}
//	private static int getActiveUserNumber(Habit habit){
//		int count = 0 ;
//		Set<UserHabit> uHs = habit.getUserHabits();
//		for(UserHabit uh:uHs)
//			if(!uh.getStat().equals(HabitState.DELETED))
//				count ++;
//		return count;
//	}
	private static Comparator<Habit> hotComparator = new Comparator<Habit>() {
		@Override
		/*exists effective problem 
		 * */
		public int compare(Habit habit1, Habit habit2) {
	//		return getActiveUserNumber(habit2) - getActiveUserNumber(habit1);
			return habit2.getActiveUserNum() - habit1.getActiveUserNum();
 		//	return habit2.getUserHabits().size()-habit1.getUserHabits().size();
		}
	};
	
	public static Comparator<Habit> getHotComparator() {
		return hotComparator;
	}
	public String toString()
	{
		 return new StringBuilder().append("#").append(id).append(" HabitName:")
					.append(habitName).append(" activeUserNums:").append(activeUserNum)
					.toString();
	}
		 

	public Set<Event> getEvents() {
		return events;
	}

	public Habit setEvents(Set<Event> events) {
		this.events = events;
		return this;
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
	public UserHabitRestrict getRestrict() {
		return limitCond;
	}

	public Habit setRestrict(UserHabitRestrict restrict) {
		this.limitCond = restrict;
		return this;
	}
	
	public Integer getActiveUserNum() {
		return activeUserNum;
	}

	public void setActiveUserNum(Integer activeUserNum) {
		this.activeUserNum = activeUserNum;
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(null != o && o.getClass() == this.getClass()))
			return false;
		final Habit other = (Habit) o;
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


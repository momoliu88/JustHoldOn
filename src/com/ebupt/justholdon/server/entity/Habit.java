package com.ebupt.justholdon.server.entity;

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
import javax.persistence.Table;

@Entity
@Table(name = "habits")
public class Habit {
	public Habit() {
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "habitId")
	private int id;
	private String habitName;
	private HabitType type;
	private PersistUnit unit;
 	private String stages;
	private String groupName;
	private String description;

	@OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<CheckIn> checkIns = new HashSet<CheckIn>();
	@OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<UserHabit> userHabits;
	@ManyToMany
	private Set<Flag> flags = new HashSet<Flag>();
	
	
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

	public int getId() {
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
}

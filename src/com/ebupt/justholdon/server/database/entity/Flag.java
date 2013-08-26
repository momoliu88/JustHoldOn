package com.ebupt.justholdon.server.database.entity;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "flag")
public class Flag  implements BaseEntity<Integer>{
	public Flag() {
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String content="";
	private String target = "";
	@ManyToMany(mappedBy = "flags",fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	private Set<Habit> habits = new HashSet<Habit>();
	@ManyToMany(mappedBy = "flags",fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	private Set<User> users = new HashSet<User>();
	private Integer userNums = 0 ;
	private Integer habitNums = 0;
	
	private Date createTime = new Date();
	private Date modifyTime = new Date();

	public Integer getUserNums() {
		return userNums;
	}

	public Flag setUserNums(Integer userNums) {
		this.userNums = userNums;
		return this;
	}

	public Integer getHabitNums() {
		return habitNums;
	}

	public Flag setHabitNums(Integer habitNums) {
		this.habitNums = habitNums;
		return this;
	}

	private static Comparator<Flag> comparator = new Comparator<Flag>() {
		@Override
		public int compare(Flag flag1, Flag flag2) {
			return flag2.getUsers().size() - flag1.getUsers().size();
		}
	};
	
	public static Comparator<Flag> getHotComparator() {
		return comparator;
	}

	public String getTarget() {
		return target;
	}

	public Flag setTarget(String target) {
		this.target = target;
		return this;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@Override
	public Integer getId() {
		return id;
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

	public Flag setId(int id) {
		this.id = id;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Flag setContent(String content) {
		this.content = content;
		return this;
	}

	public Set<Habit> getHabits() {
		return habits;
	}

	public Flag setHabits(Set<Habit> habits) {
		this.habits = habits;
		return this;
	}

	public String toString() {
		return new StringBuilder().append("#").append(id).append(":[")
				.append(content).append("]").toString();
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(null != o && o.getClass() == this.getClass()))
			return false;
		final Flag other = (Flag) o;
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

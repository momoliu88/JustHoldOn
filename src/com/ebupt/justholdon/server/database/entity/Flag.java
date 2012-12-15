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
import javax.persistence.Table;

@Entity
@Table(name = "flag")
public class Flag  implements BaseEntity<Integer>{
	public Flag() {
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String content;
	private String target;
	@ManyToMany(mappedBy = "flags",fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Habit> habits = new HashSet<Habit>();
	@ManyToMany(mappedBy = "flags",fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<User> users = new HashSet<User>();
	private Date createTime = new Date();
	private Date modifyTime = new Date();

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
}

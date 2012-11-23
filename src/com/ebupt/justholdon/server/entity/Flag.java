package com.ebupt.justholdon.server.entity;

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
public class Flag {
	public Flag() {
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String content;
	@ManyToMany(mappedBy = "flags")
	private Set<Habit> habits = new HashSet<Habit>();
	@ManyToMany(mappedBy = "flags",fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<User> users = new HashSet<User>();

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public int getId() {
		return id;
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

package com.ebupt.justholdon.server.database.service;

public class HabitGroupName {
	public HabitGroupName(){};
	private String name;
	private int habitnum;
	public String getName() {
		return name;
	}
	public HabitGroupName setName(String name) {
		this.name = name;
		return this;
	}
	public int getHabitnum() {
		return habitnum;
	}
	public HabitGroupName setHabitnum(int habitnum) {
		this.habitnum = habitnum;
		return this;
	}
	
}

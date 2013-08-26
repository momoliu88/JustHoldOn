package com.ebupt.justholdon.server.database.entity;

public class TbName {
	public final static String checkinTb = getName(CheckIn.class);
	public final static String userHabitTb = getName(UserHabit.class);
	public final static String eventTb = getName(Event.class);
	public final static String approveTb = getName(Approve.class);
	public final static String commentTb = getName(Comment.class);
	public final static String flagTb = getName(Flag.class);
	public final static String habitTb = getName(Habit.class);
	public final static String impressionTb = getName(Impression.class);
	public final static String newestVersionTb = getName(NewestVersion.class);
	public final static String quoteTb = getName(Quote.class);
	public final static String suggestionTb = getName(Suggestion.class);
	public final static String systemInfoTb = getName(SystemInfo.class);
	public final static String systemInfoSendedTb = getName(SystemInfoSended.class);
	public final static String userTb = getName(User.class);
	public final static String userFieldTb = getName(UserField.class);
	public final static String versionTb = getName(Version.class);
	public final static String weeklySummaryTb = getName(WeeklySummary.class);

	private static String getName(Class<?> classType) {
		return classType.getName();

	}
}

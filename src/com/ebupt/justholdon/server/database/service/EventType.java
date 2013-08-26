package com.ebupt.justholdon.server.database.service;

public enum EventType {
	SYSTEM_INFORMATION,
	SOMEBODY_JOININ_APP, /* A加入应用，其微博好友且已在应用的好友是否加起为好友 */
	WANT_BE_FRIEND, /* A想和B成为朋友 */
	INVITE_JOININ_HABIT, /* A邀请B加入习惯 */
	REMAIND_SOMEBODY, /* A对B的习惯进行提醒 */
	COMMENT_CHECKIN, /* A对B的习惯的一次签到进行评论 */
	REPLY,
	APPROVE_HABIT, /* A对B的某一习惯进行鼓励 */
	WEEKLYSUMMARY,/*每周总结*/
	
	SOMEBODY_JOININ_HABIT, /* A加入了某习惯 非通知 */
	HOLDON_STAGE, /* A的习惯达到了某一阶段 非通知 */
	QUIT_HABIT, /* A退出某习惯 非通知 */
	SOMEBODY_CHECKIN,/* A对某习惯进行了签到 非通知 */
	ACCESS_FRIEND;
}

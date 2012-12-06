package com.ebupt.justholdon.server.database.entity;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	public User() {
	};

	public User(String userName, String password, String avatar, Long uid,
			String deviceToken) {
		this.userName = userName;
		this.id = uid;
		this.deviceToken = deviceToken;
		this.password = password;
		this.avatar = avatar;
		this.createTime = new Date();
		this.socialBind = BindType.WEIBO;
	}

	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userId")
	private Long id;
	private String userName;
	private String password;
	private String avatar;

	private BindType socialBind = BindType.WEIBO;
	private Date createTime;
	private String deviceToken;
	private String token;
	
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<SystemInfoSended> receiveSystemInfos = new HashSet<SystemInfoSended>();
	
	@OneToMany(mappedBy="sponsor",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Event> sponsorEvent = new HashSet<Event>();
	@OneToMany(mappedBy="receiver",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Event> receiverEvent = new HashSet<Event>();
	
	@OneToMany(mappedBy="sponsor",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Comment> sponsorComments = new HashSet<Comment>();
	@OneToMany(mappedBy="receiver",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Comment> receiverComments = new HashSet<Comment>();
	
//	@OneToMany(mappedBy="sponsor",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private Set<Event> sponsorMessages = new HashSet<Event>();
//	
//	@OneToMany(mappedBy="receiver",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private Set<Event> receiveMessages = new HashSet<Event>();
	
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<WeeklySummary> weeklySummaries = new HashSet<WeeklySummary>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Approve> approves = new HashSet<Approve>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<CheckIn> checkIns = new HashSet<CheckIn>();

	@OneToMany(mappedBy = "sponsor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Impression> sponseImpressiones = new HashSet<Impression>();
	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Impression> receivedImpressiones = new HashSet<Impression>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<User> friends = new HashSet<User>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<UserHabit> userHabits = new HashSet<UserHabit>();
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Flag> flags = new HashSet<Flag>();

	private static Comparator<User> friendsMostComparator = new Comparator<User>() {
		@Override
		public int compare(User user1, User user2) {
			return user2.getFriends().size() - user1.getFriends().size();
		}
	};
	private static Comparator<User> checkInMostComparator = new Comparator<User>() {
		@Override
		public int compare(User user1, User user2) {
			return user2.getCheckIns().size() - user1.getCheckIns().size();
		}
	};
	
	public Set<SystemInfoSended> getReceiveSystemInfos() {
		return receiveSystemInfos;
	}

	public void setReceiveSystemInfos(Set<SystemInfoSended> receiveSystemInfos) {
		this.receiveSystemInfos = receiveSystemInfos;
	}

	public static Comparator<User> getCheckInMostComparator() {
		return checkInMostComparator;
	}

	public static Comparator<User> getFriendsMostComparator() {
		return friendsMostComparator;
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

	public Set<User> getFriends() {
		return friends;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	public User setUserHabits(Set<UserHabit> userHabits) {
		this.userHabits = userHabits;
		return this;
	}

	public Long getId() {
		return id;
	}

	public User setId(Long id) {
		this.id = id;
		return this;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public User setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
		return this;
	}

	public Set<Impression> getSponseImpressiones() {
		return sponseImpressiones;
	}

	public User setSponseImpressiones(Set<Impression> sponseImpressiones) {
		this.sponseImpressiones = sponseImpressiones;
		return this;
	}

	public Set<Impression> getReceivedImpressiones() {
		return receivedImpressiones;
	}

	public User setReceivedImpressiones(Set<Impression> receivedImpressiones) {
		this.receivedImpressiones = receivedImpressiones;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public User setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getAvatar() {
		return avatar;
	}

	public User setAvatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	public BindType getSocialBind() {
		return socialBind;
	}

	public User setSocialBind(BindType socialBind) {
		this.socialBind = socialBind;
		return this;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public User setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public String getToken() {
		return token;
	}

	public User setToken(String token) {
		this.token = token;
		return this;
	}

	public Set<Approve> getApproves() {
		return approves;
	}

	public User setApproves(Set<Approve> approves) {
		this.approves = approves;
		return this;
	}

	public Set<CheckIn> getCheckIns() {
		return checkIns;
	}

	public User setCheckIns(Set<CheckIn> checkIns) {
		this.checkIns = checkIns;
		return this;
	}

	public UserField toUserField() {
		return new UserField().setId(getId()).setAvatar(getAvatar())
				.setUserName(getUserName()).setPassword(getPassword())
				.setWeiboKey(getToken()).setDeviceToken(getDeviceToken())
				.setCreateTime(getCreateTime()).setSocialBind(getSocialBind());
	}

	public Set<WeeklySummary> getWeeklySummaries() {
		return weeklySummaries;
	}

	public User setWeeklySummaries(Set<WeeklySummary> weeklySummaries) {
		this.weeklySummaries = weeklySummaries;
		return this;
	}

//	public Set<Event> getSponsorMessages() {
//		return sponsorMessages;
//	}

	public Set<Event> getSponsorEvent() {
		return sponsorEvent;
	}

	public void setSponsorEvent(Set<Event> sponsorEvent) {
		this.sponsorEvent = sponsorEvent;
	}

	public Set<Event> getReceiverEvent() {
		return receiverEvent;
	}

	public void setReceiverEvent(Set<Event> receiverEvent) {
		this.receiverEvent = receiverEvent;
	}

//	public User setSponsorMessages(Set<Event> sponsorMessages) {
//		this.sponsorMessages = sponsorMessages;
//		return this;
//	}
//
//	public Set<Event> getReceiveMessages() {
//		return receiveMessages;
//	}
//
//	public User setReceiveMessages(Set<Event> receiveMessages) {
//		this.receiveMessages = receiveMessages;
//		return this;
//	}

	public Set<Comment> getSponsorComments() {
		return sponsorComments;
	}

	public User setSponsorComments(Set<Comment> sponsorComments) {
		this.sponsorComments = sponsorComments;
		return this;
	}

	public Set<Comment> getReceiverComments() {
		return receiverComments;
	}

	public User setReceiverComments(Set<Comment> receiverComments) {
		this.receiverComments = receiverComments;
		return this;
	}

	public boolean equals(Object obj) {
		if (false == obj instanceof User)
			return false;
		User user = (User) obj;
		if (!user.getUserName().equals(this.getUserName()))
			return false;
		if (!user.getAvatar().equals(this.getAvatar()))
			return false;
		if (!user.getPassword().equals(this.getPassword()))
			return false;
		if (!user.getSocialBind().equals(this.getSocialBind()))
			return false;
		return true;
	}

}

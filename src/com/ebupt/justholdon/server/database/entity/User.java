package com.ebupt.justholdon.server.database.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OrderBy;
import javax.persistence.PreUpdate;
import javax.persistence.UniqueConstraint;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {
		"userId", "userName" }))
public class User implements BaseEntity<Long> {
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
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userId")
	private Long id;
	private String userName =null;
	private String password = null;
	private String avatar = "";

	private BindType socialBind = BindType.WEIBO;
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	private String deviceToken = "";
	private String token = "";

	private Integer friendNums = 0 ;
	private Integer checkInNums = 0;
	private Integer limitedUser = 1;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	@OrderBy("createTime DESC")
	private List<SystemInfoSended> receiveSystemInfos = new ArrayList<SystemInfoSended>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	@OrderBy("createTime DESC")
	private List<Suggestion> sponsorSuggestions = new ArrayList<Suggestion>();

	@OneToMany(mappedBy = "sponsor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	@OrderBy("createTime DESC")
	private List<Event> sponsorEvent = new ArrayList<Event>();
	// not use by now
	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	@OrderBy("createTime DESC")
	private List<Event> receiverEvent = new ArrayList<Event>();
	// not use by now
	@OneToMany(mappedBy = "sponsor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	@OrderBy("createTime DESC")
	private List<Comment> sponsorComments = new ArrayList<Comment>();
	// not use by now
	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	@OrderBy("createTime DESC")
	private List<Comment> receiverComments = new ArrayList<Comment>();

	// @OneToMany(mappedBy="sponsor",cascade = CascadeType.ALL, fetch =
	// FetchType.LAZY)
	// private Set<Event> sponsorMessages = new HashSet<Event>();
	//
	// @OneToMany(mappedBy="receiver",cascade = CascadeType.ALL, fetch =
	// FetchType.LAZY)
	// private Set<Event> receiveMessages = new HashSet<Event>();
	// not use by now
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	@OrderBy("createTime DESC")
	private List<WeeklySummary> weeklySummaries = new ArrayList<WeeklySummary>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	@OrderBy("createTime DESC")
	private List<Approve> approves = new ArrayList<Approve>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	@OrderBy("checkInTime DESC")
	private List<CheckIn> checkIns = new ArrayList<CheckIn>();

	@OneToMany(mappedBy = "sponsor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	@OrderBy("createTime DESC")
	private List<Impression> sponseImpressiones = new ArrayList<Impression>();
	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	@OrderBy("createTime DESC")
	private List<Impression> receivedImpressiones = new ArrayList<Impression>();

	@OneToMany(mappedBy="userA",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	private Set<RelationShip> relationShips = new HashSet<RelationShip>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	private Set<UserHabit> userHabits = new HashSet<UserHabit>();
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@org.hibernate.annotations.LazyCollection(org.hibernate.annotations.LazyCollectionOption.EXTRA)
	private Set<Flag> flags = new HashSet<Flag>();

	private static Comparator<User> friendsMostComparator = new Comparator<User>() {
		@Override
		public int compare(User user1, User user2) {
			return user2.getRelationShips().size() - user1.getRelationShips().size();
		}
	};
	private static Comparator<User> checkInMostComparator = new Comparator<User>() {
		@Override
		public int compare(User user1, User user2) {
			return user2.getCheckIns().size() - user1.getCheckIns().size();
		}
	};

	public List<SystemInfoSended> getReceiveSystemInfos() {
		return receiveSystemInfos;
	}

	public void setReceiveSystemInfos(List<SystemInfoSended> receiveSystemInfos) {
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

	public Integer getLimitedUser() {
		return limitedUser;
	}

	public User setLimitedUser(Integer limitedUser) {
		this.limitedUser = limitedUser;
		return this;
	}

	public void setFlags(Set<Flag> flags) {
		this.flags = flags;
	}

	public Set<UserHabit> getUserHabits() {
		return userHabits;
	}
	
	public Set<RelationShip> getRelationShips() {
		return relationShips;
	}

	public void setRelationShips(Set<RelationShip> relationShips) {
		this.relationShips = relationShips;
	}

	public User setUserHabits(Set<UserHabit> userHabits) {
		this.userHabits = userHabits;
		return this;
	}

	public Long getId() {
		return id;
	}

	public List<Suggestion> getSponsorSuggestions() {
		return sponsorSuggestions;
	}

	public void setSponsorSuggestions(List<Suggestion> sponsorSuggestions) {
		this.sponsorSuggestions = sponsorSuggestions;
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

	public List<Impression> getSponseImpressiones() {
		return sponseImpressiones;
	}

	public User setSponseImpressiones(List<Impression> sponseImpressiones) {
		this.sponseImpressiones = sponseImpressiones;
		return this;
	}

	public List<Impression> getReceivedImpressiones() {
		return receivedImpressiones;
	}

	public User setReceivedImpressiones(List<Impression> receivedImpressiones) {
		this.receivedImpressiones = receivedImpressiones;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public Integer getFriendNums() {
		return friendNums;
	}

	public void setFriendNums(Integer friendNums) {
		this.friendNums = friendNums;
	}

	public Integer getCheckInNums() {
		return checkInNums;
	}

	public void setCheckInNums(Integer checkInNums) {
		this.checkInNums = checkInNums;
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

	public List<Approve> getApproves() {
		return approves;
	}

	public User setApproves(List<Approve> approves) {
		this.approves = approves;
		return this;
	}

	public List<CheckIn> getCheckIns() {
		return checkIns;
	}

	public User setCheckIns(List<CheckIn> checkIns) {
		this.checkIns = checkIns;
		return this;
	}

	public UserField toUserField() {
		return new UserField().setId(getId()).setAvatar(getAvatar())
				.setUserName(getUserName()).setPassword(getPassword())
				.setWeiboKey(getToken()).setDeviceToken(getDeviceToken())
				.setCreateTime(getCreateTime()).setSocialBind(getSocialBind());
	}

	public List<WeeklySummary> getWeeklySummaries() {
		return weeklySummaries;
	}

	public User setWeeklySummaries(List<WeeklySummary> weeklySummaries) {
		this.weeklySummaries = weeklySummaries;
		return this;
	}

	// public Set<Event> getSponsorMessages() {
	// return sponsorMessages;
	// }

	public List<Event> getSponsorEvent() {
		return sponsorEvent;
	}

	public void setSponsorEvent(List<Event> sponsorEvent) {
		this.sponsorEvent = sponsorEvent;
	}

	public List<Event> getReceiverEvent() {
		return receiverEvent;
	}

	public void setReceiverEvent(List<Event> receiverEvent) {
		this.receiverEvent = receiverEvent;
	}

	// public User setSponsorMessages(Set<Event> sponsorMessages) {
	// this.sponsorMessages = sponsorMessages;
	// return this;
	// }
	//
	// public Set<Event> getReceiveMessages() {
	// return receiveMessages;
	// }
	//
	// public User setReceiveMessages(Set<Event> receiveMessages) {
	// this.receiveMessages = receiveMessages;
	// return this;
	// }

	public List<Comment> getSponsorComments() {
		return sponsorComments;
	}

	public User setSponsorComments(List<Comment> sponsorComments) {
		this.sponsorComments = sponsorComments;
		return this;
	}

	public List<Comment> getReceiverComments() {
		return receiverComments;
	}

	public User setReceiverComments(List<Comment> receiverComments) {
		this.receiverComments = receiverComments;
		return this;
	}

	@Override
	public Date getModifyTime() {
		return modifyTime;
	}

	@Override
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	@Override
	@PreUpdate
	public void onUpdate() {
		setModifyTime(new Date());
	}
	@Override
	public String toString(){
		return new StringBuilder().append("#").append(id).append(" ").append(userName).toString();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!((null != obj) && (obj.getClass() == this.getClass())))
			return false;
		final User other = (User) obj;
		return other.getId().equals(this.getId());
	}

	@Override
	public int hashCode() {
		int result = 31;
		result += this.getId() == null ? 0 : this.getId().hashCode();
		return result;
	}
}

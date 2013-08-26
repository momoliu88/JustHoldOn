package com.ebupt.justholdon.server.database.entity;

//import java.util.Comparator;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.ebupt.justholdon.server.database.service.EventType;
import com.ebupt.justholdon.server.database.service.MessageFlag;

@Entity
@Table(name = "events")
public class Event implements BaseEntity<Integer>{
	public Event() {
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "sponsor")
	private User sponsor;
	@ManyToOne
	@JoinColumn(name = "receiver")
	private User receiver;
	@ManyToOne
	@JoinColumn(name = "habitId")
	private Habit habit;
	private EventType type;
	private Integer relationId = 0;
	private String content = "";
	private MessageFlag flag = MessageFlag.JUST_EVENT;
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	private Boolean isSystemInfo = false;
	private Boolean isDeleted = false;
	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getSponsor() {
		return sponsor;
	}

	public Event setSponsor(User sponsor) {
		this.sponsor = sponsor;
		if (null != sponsor)
			sponsor.getSponsorEvent().add(this);
		return this;
	}

	public User getReceiver() {
		return receiver;
	}

	public Event setReceiver(User receiver) {
		this.receiver = receiver;
		if (null != receiver)
			receiver.getReceiverEvent().add(this);
		return this;
	}

	public Habit getHabit() {
		return habit;
	}

	public Event setHabit(Habit habit) {
		this.habit = habit;
		if (null != habit)
			habit.getEvents().add(this);
		return this;
	}

	// public String getHabitName() {
	// return habitName;
	// }
	// public Event setHabitName(String habitName) {
	// this.habitName = habitName;
	// return this;
	// }
	public EventType getType() {
		return type;
	}

	public Event setType(EventType type) {
		this.type = type;
		return this;
	}

	public int getRelationId() {
		return relationId;
	}

	public Event setRelationId(Integer relationId) {
		this.relationId = relationId;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Event setContent(String content) {
		this.content = content;
		return this;
	}

	public MessageFlag getFlag() {
		return flag;
	}

	public Event setFlag(MessageFlag flag) {
		this.flag = flag;
		return this;
	}
	@Override
	public Date getCreateTime() {
		return createTime;
	}

	public Event setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	public Boolean getIsSystemInfo() {
		return isSystemInfo;
	}

	public void setIsSystemInfo(Boolean isSystemInfo) {
		this.isSystemInfo = isSystemInfo;
	}

//	public static Comparator<Event> getDateComparator() {
//		return dateComparator;
//	}
//
//	public static Comparator<Event> getIdComparator() {
//		return idComparator;
//	}
	@Override
	public Date getModifyTime() {
		return modifyTime;
	}
	@Override
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public Event setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}
	@Override
	public String toString(){
		return new StringBuilder().append("#").append(id).append(" createTime:").append(createTime).append(" FLAG:").append(flag)
				.append(" SPONSOR:").append(sponsor).toString();
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(null != o && o.getClass() == this.getClass()))
			return false;
		final Event other = (Event) o;
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

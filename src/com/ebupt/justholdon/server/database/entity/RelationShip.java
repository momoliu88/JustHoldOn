package com.ebupt.justholdon.server.database.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
@Entity
@Table(name = "relationships")
public class RelationShip  implements BaseEntity<Integer>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "userA", nullable = false)
	private User userA;
	@JoinColumn(name = "userAName", nullable = false)
	private String userAName;
	@ManyToOne
	@JoinColumn(name = "userB", nullable = false)
	private User userB;
	@JoinColumn(name = "userBName", nullable = false)
	private String userBName;
	private Date createTime = new Date();
	private Date modifyTime = new Date();
//	private boolean isDeleted = false;
	/**
	 update relationships R SET userBName = (select userName from user U where U.userId = R.userB);
	 update relationships R SET userAName = (select userName from user U where U.userId = R.userA);
	 */
	public User getUserA() {
		return userA;
	}

//	public boolean isDeleted() {
//		return isDeleted;
//	}
//
//	public RelationShip setDeleted(boolean isDeleted) {
//		this.isDeleted = isDeleted;
//		return this;
//	}

	public String getUserAName() {
		return userAName;
	}

	public RelationShip setUserAName(String userAName) {
		this.userAName = userAName;
		return this;
	}

	public String getUserBName() {
		return userBName;
	}

	public RelationShip setUserBName(String userBName) {
		this.userBName = userBName;
		return this;
	}

	public RelationShip setUserA(User userA) {
		this.userA = userA;
		if(null != userA){
			userA.getRelationShips().add(this);
			userA.setFriendNums(userA.getFriendNums()+1);
		}
		return this;
	}

	public User getUserB() {
		return userB;
	}

	public RelationShip setUserB(User userB) {
		this.userB = userB;
//		if(null != userB)
//			userB.getRelationShips().add(this);
		return this;
	}

	public RelationShip setId(int id) {
		this.id = id;
		return this;
	}

	public RelationShip setCreateTime(Date createTime) {
		this.createTime = createTime;
		return this;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
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
}

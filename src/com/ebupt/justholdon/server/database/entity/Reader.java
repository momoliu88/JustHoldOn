package com.ebupt.justholdon.server.database.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "readers")
public class Reader implements BaseEntity<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "bookId", nullable = false)
	private Book book;
	private ReadStat stat = ReadStat.UNREADED;
	private Integer curPage = 0;
	private Date finishTime;
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	
	public User getUser() {
		return user;
	}

	public Reader setUser(User user) {
		this.user = user;
		return this;
	}

	public Book getBook() {
		return book;
	}

	public Reader setBook(Book book) {
		this.book = book;
		return this;
	}

	public ReadStat getStat() {
		return stat;
	}

	public Reader setStat(ReadStat stat) {
		this.stat = stat;
		return this;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public Reader setCurPage(Integer curPage) {
		this.curPage = curPage;
		return this;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public Reader setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
		return this;
	}

	public Reader setId(int id) {
		this.id = id;
		return this;
	}

	public Reader setCreateTime(Date createTime) {
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
	public void onUpdate() {
		setModifyTime(new Date());
	}
	
	@Override
	public String toString(){
		return "#user: "+user.getUserName()+" bookName:"+book.getName()+" readStat:"+stat+" page:"+curPage; 
	}
}

package com.ebupt.justholdon.server.database.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book implements BaseEntity<Integer>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Column(unique=true)
	private String isbn;
	private String author;
	private String coverAddr;
	private Integer page;
	private String introduction;
	private String DoubanLink;
	private Date createTime = new Date();
	private Date modifyTime = new Date();
	@Override
	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Book setName(String name) {
		this.name = name;
		return this;	
	}

	public String getIsbn() {
		return isbn;
	}

	public Book setIsbn(String isbn) {
		this.isbn = isbn;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public Book setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getCoverAddr() {
		return coverAddr;
	}

	public Book setCoverAddr(String coverAddr) {
		this.coverAddr = coverAddr;
		return this;
	}

	public Integer getPage() {
		return page;
	}

	public Book setPage(Integer page) {
		this.page = page;
		return this;
	}

	public String getIntroduction() {
		return introduction;
	}

	public Book setIntroduction(String introduction) {
		this.introduction = introduction;
		return this;
	}

	public String getLinkForDouban() {
		return DoubanLink;
	}

	public Book setLinkForDouban(String linkForDouban) {
		this.DoubanLink = linkForDouban;
		return this;
	}

	public Book setId(int id) {
		this.id = id;
		return this;
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

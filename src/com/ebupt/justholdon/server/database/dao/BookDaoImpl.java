package com.ebupt.justholdon.server.database.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.entity.Book;

@Transactional
@Repository("bookDao")
public class BookDaoImpl  extends GenericHibernateDaoImpl<Book,Integer> implements BookDao{

	public BookDaoImpl(Class<Book> type) {
		super(type);
	}
	public BookDaoImpl(){
		this(Book.class);
	}
	 
}

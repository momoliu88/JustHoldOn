package com.ebupt.justholdon.server.database.service;

import com.ebupt.justholdon.server.database.entity.Book;

public interface BookService extends GenericService<Book,Integer> {
	public Integer createBook(Book book);
	public boolean isAdded(String isbn,String name);
	public Book getBook(String isbn,String name);
 }

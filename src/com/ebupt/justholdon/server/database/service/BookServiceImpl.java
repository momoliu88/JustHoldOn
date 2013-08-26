package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebupt.justholdon.server.database.dao.BookDao;
import com.ebupt.justholdon.server.database.entity.Book;

@Transactional
@Service("bookService")
public class BookServiceImpl implements BookService{
	@Autowired
	private BookDao bookDao;
	
	@Override
	public Integer save(Book newInstance) {
		return bookDao.save(newInstance);
	}

	@Override
	public Book get(Integer id) {
		return bookDao.get(id);
	}

	@Override
	public void update(Book transientObject) {
		bookDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return bookDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
	//	Nothing
	}

	@Override
	public List<Book> findAll() {
		return bookDao.findAll();
	}

	@Override
	public void saveOrUpdate(Book transientObject) {
		bookDao.saveOrUpdate(transientObject);
	}

	@Override
	public Integer createBook(Book book) {
		return save(book);
	}

	@Override
	public boolean isAdded(String isbn, String name) {
		return null!=getBook(isbn,name);
	}

	@Override
	public Book getBook(String isbn, String name) {
		List<Criterion> crits = new ArrayList<Criterion>();
		if(null != isbn)
			crits.add(Restrictions.eq("isbn", isbn));
		if(null != name)
			crits.add(Restrictions.eq("name", name));
		Criterion [] criterions = new Criterion[crits.size()];
		List<Book> books = bookDao.findByCriteria(crits.toArray(criterions));
		if(null == books || books.isEmpty()) return null;
		return books.get(0);
	}

}

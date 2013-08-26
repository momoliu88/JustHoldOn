package com.ebupt.justholdon.server.database.service;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebupt.justholdon.server.database.dao.ReaderDao;
import com.ebupt.justholdon.server.database.entity.Book;
import com.ebupt.justholdon.server.database.entity.ReadStat;
import com.ebupt.justholdon.server.database.entity.Reader;
import com.ebupt.justholdon.server.database.entity.User;

@Service("readerService")
@Transactional
public class ReaderServiceImpl implements ReaderService{

	@Autowired
	private RelationShipService relationShipService;
	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReaderDao readerDao;
	
	@Override
	public Integer save(Reader newInstance) {
		return readerDao.save(newInstance);
	}

	@Override
	public Reader get(Integer id) {
		return readerDao.get(id);
	}

	@Override
	public void update(Reader transientObject) {
		readerDao.update(transientObject);
	}

	@Override
	public int update(Integer id, Map<String, Object> infos) {
		return readerDao.update(id, infos);
	}

	@Override
	public void delete(Integer id) {
		//nothing
		readerDao.delete(id);
	}

	@Override
	public List<Reader> findAll() {
		return readerDao.findAll();
	}

	@Override
	public void saveOrUpdate(Reader transientObject) {
		readerDao.saveOrUpdate(transientObject);
	}

	@Override
	public Integer read(Long uid, Integer bid) {
		Book book = bookService.get(bid);
		User user = userService.get(uid);
		Reader reader = new Reader().setBook(book).setUser(user).setStat(ReadStat.READING);
		return save(reader);
	}

	private Reader findReader(Long uid,Integer bid){
		User user = userService.get(uid);
		Book book = bookService.get(bid);
		Criterion crits [] = new Criterion[]{
				Restrictions.eq("user", user),
				Restrictions.eq("book", book),
				Restrictions.ne("stat", ReadStat.UNREADED)
		};
		List<Reader> readers = readerDao.findByCriteria(crits);
		if(readers == null || readers.isEmpty()) return null;
		return readers.get(0);
	}
	@Override
	public boolean updateState(Long uid, Integer bid, ReadStat stat) {
		Reader reader = findReader(uid,bid);
		if(null == reader) return false;
		reader.setStat(stat);
		update(reader);
		return true;
	}

	@Override
	public boolean updatePage(Long uid, Integer bid, Integer curpage) {
		Reader reader = findReader(uid,bid);
		if(null == reader) return false;
		reader.setCurPage(curpage);
		return true;
	}
	private Integer getReaderNums(Criterion[] crits)
	{
		ProjectionList projectionList = Projections.projectionList().add(
				Projections.count("user")
				);
		List<?> result = readerDao.findByCriteria(projectionList,crits);
		return Integer.valueOf((result.get(0)).toString());
	}
	
	@Override
	public Integer getFriendNum(Long uid, Integer bid) {
		List<User> friends = relationShipService.findFriends(uid);
		if(friends == null || friends.isEmpty()) return 0;
		Book book = bookService.get(bid);
		Criterion [] crits = new Criterion[]{
				Restrictions.in("user", friends),
				Restrictions.eq("book", book),
				Restrictions.ne("stat", ReadStat.UNREADED)
		};
		return getReaderNums(crits);
	}

	@Override
	public Integer getReaders(Integer bid) {
		Book book = bookService.get(bid);
		Criterion [] crits = new Criterion[]{
				Restrictions.eq("book", book)
		};
		return getReaderNums(crits);
	}
	private List<Book> warpCriterions(Long uid,Integer length,Integer startId,boolean after,Criterion...crits){
		ProjectionList projList = Projections.projectionList().add(
				Projections.property("book")
				);
		List<?> results;
		if(Utils.checkIdIsZero(startId)) 
		{
			results = readerDao.findByCriteria(projList,length,crits);
			return Utils.convertResults(results);
		}
		Reader reader = getReader(uid,startId);
		results =  readerDao.findByCriteria(projList,length,
				Utils.warpIdRangeLimit(reader.getCreateTime(),"createTime",after, crits));
		return Utils.convertResults(results);
	}
	@Override
	public List<Book> getBooks(Long uid,Integer startId,Integer length,boolean after){
		Criterion [] crits = new Criterion[]{
				Restrictions.eq("user", userService.get(uid))
		};
		return warpCriterions(uid,length,startId,after,crits);
	}

	public List<Book> getBooks(Long uid) {
		User user = userService.get(uid);
		Criterion [] crits = new Criterion[]{
				Restrictions.eq("user", user)
		};
		ProjectionList projList = Projections.projectionList().add(
				Projections.property("book")
				);
		List<?> results = readerDao.findByCriteria(projList,crits);
		return Utils.convertResults(results);
	}

	@Override
	public Reader getReader(Long uid, Integer bid) {
		return findReader(uid,bid);
	}
	

}

package com.ebupt.justholdon.server.database.service;

import java.util.List;

import com.ebupt.justholdon.server.database.entity.Book;
import com.ebupt.justholdon.server.database.entity.ReadStat;
import com.ebupt.justholdon.server.database.entity.Reader;

public interface ReaderService extends GenericService<Reader,Integer>{
	public Integer read(Long uid,Integer bid);
	public boolean updateState(Long uid,Integer bid,ReadStat stat);
	public boolean updatePage(Long uid,Integer bid,Integer curpage);
	public Integer getFriendNum(Long uid,Integer bid);
	public Integer getReaders(Integer bid);
	public List<Book> getBooks(Long uid,Integer startId,Integer length,boolean after);
	public Reader getReader(Long uid,Integer bid);
}

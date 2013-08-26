package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

//import com.ebupt.justholdon.server.database.dao.GenericHibernateDao;
import com.ebupt.justholdon.server.database.entity.BaseEntity;
//import com.ebupt.justholdon.server.database.entity.Event;
import com.ebupt.justholdon.server.database.entity.GenericComparator;

public class Utils {
	private static Logger logger = Logger.getLogger(Utils.class);
	private static int MAXMULTI = 1024;

	public static boolean checkIndex(Integer start, Integer end, int maxSize) {
		if (start < 0 || end < 0) {
			logger.warn("startIndex:" + start + ",endIndex:" + end
					+ " can't < 0");
			return false;
		}
		if (start >= end) {
			logger.warn("startIndex:" + start + ",endIndex:" + end + " error");
			return false;
		}
		if (start >= maxSize)
			return false;
		return true;
	}
	public static int getMulti(Integer original){
		if(original >= MAXMULTI) return original;
		else {
			int ret = original.intValue() << 1;
			original=Integer.valueOf(ret); 
			return ret;
		}
	}
	public static<T> List<T> convertResults(List<?> objects){
		Iterator<?> itr = objects.iterator();
		List<T> ret = new ArrayList<T>();
		while(itr.hasNext()){
			@SuppressWarnings("unchecked")
			T item = (T) itr.next();
			ret.add(item);
		}
		return ret;
	}
	public static <T> List<T> subList(Integer start, Integer end, List<T> list) {
		if (null == list)
			return null;
		int maxSize = list.size();
		if (!checkIndex(start, end, maxSize))
			return new ArrayList<T>();
		if (end > maxSize)
			end = maxSize;
		return list.subList(start, end);
	}
	public static int getNewestId(boolean after,List<? extends BaseEntity<Integer>> list){
		if(after)
			return list.get(list.size()-1).getId();
		return list.get(0).getId();
		
	}
//	public static  Criterion[] warpIdRangeLimit(boolean after,Long startId,Criterion... criterion){
//		return warpIdRangeLimit(after,startId,criterion);
//	}
	public static boolean checkIdIsZero(Number startId){
		Number zeroId ;
		if(Long.class.isInstance(startId))
			zeroId = Long.valueOf(0L);	
		else
			zeroId = Integer.valueOf(0);
		if(startId.equals(zeroId))
			return true;
		return false;
	}
	
	public static Criterion[] warpIdRangeLimit(Object obj,String comparedCol,boolean after,Criterion... criterion)
	{
		List<Criterion> criteriation = new ArrayList<Criterion>(Arrays.asList(criterion));
		if(null != obj)
		{			
			if (after)
				criteriation.add(Restrictions.lt(comparedCol,obj));
			else
				criteriation.add(Restrictions.gt(comparedCol, obj));
		}
		Criterion[] ret = new Criterion[criteriation.size()];
		return criteriation.toArray(ret);
	}
	public static Criterion[] warpIdRangeLimit(Object obj,boolean after,Criterion... criterion){
		return warpIdRangeLimit(obj,"createTime",after,criterion);
	}
//	public static  Criterion[] warpIdRangeLimit(boolean after,Integer startId,Criterion... criterion){
//		List<Criterion> criteriation = new ArrayList<Criterion>(Arrays.asList(criterion));
//		if(startId.equals(0))
//			criteriation.add(Restrictions.gt("id", startId));
//		else{
//			if (after)
//				criteriation.add(Restrictions.lt("id", startId));
//			else
//				criteriation.add(Restrictions.gt("id", startId));
//		}
//		Criterion[] ret = new Criterion[criteriation.size()];
//		return criteriation.toArray(ret);	
//	}
	
	@SuppressWarnings("unchecked")
	public static <T extends BaseEntity<Integer>> List<T> cutEventList(List<T> list,
			Integer startId, Integer length, boolean after, Boolean sorted) {
		Comparator<T> comparator = GenericComparator.getInstance()
				.getDateComparator();
		if (!sorted)
			Collections.sort(list, comparator);
		Integer startpos = null;
		Integer endpos = null;
		if (list.isEmpty())
			return new ArrayList<T>();

		if (startId.equals(0))
			return Utils.subList(0, length, list);
		Integer indx = 0;
		boolean finded = false;
		for (T obj : list) {
			if (obj.getId().equals(startId)) {
				finded = true;
				break;
			}
			indx++;
		}
		if (!finded)
			return new ArrayList<T>();

		if (after) {
			startpos = indx + 1;
			endpos = indx + length + 1;
		} else {
			startpos = indx - length;
			startpos = (startpos < 0) ? 0 : startpos;
			endpos = indx;
		}
		return subList(startpos, endpos, list);
	}
}

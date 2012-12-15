package com.ebupt.justholdon.server.database.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ebupt.justholdon.server.database.entity.BaseEntity;
import com.ebupt.justholdon.server.database.entity.GenericComparator;

public class Utils {
	private static Logger logger = Logger.getLogger(Utils.class);

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
	public static <T> List<T> subList(Integer start,Integer end,List<T> list)
	{
		if(null == list) return null;
		int maxSize = list.size();
		if(!checkIndex(start,end,maxSize)) return new ArrayList<T>();
		if(end > maxSize) end = maxSize;
		return list.subList(start, end);
	} 
	@SuppressWarnings({"unchecked","rawtypes"})
	public static <T extends BaseEntity<?>> List<T> cutEventList(List<T> list, Integer startId,
			Integer length, boolean after,Boolean... sortedArg) {
		Comparator comparator =  GenericComparator.getInstance().getDateComparator();
		boolean sorted = false;
		if(null != sortedArg && sortedArg.length > 0)
			sorted = sortedArg[0];
		if(!sorted)
			Collections.sort(list, comparator);
		Integer startpos = null;
		Integer endpos = null;
		if (list.isEmpty())
			return new ArrayList<T>();

		if (null == startId)
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
			if (startpos < 0)
				startpos = 0;
			endpos = indx;
		}
		return subList(startpos, endpos, list);
	}
}

package com.ebupt.justholdon.server.database.service;

import java.util.List;

import org.apache.log4j.Logger;

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
		if(!checkIndex(start,end,maxSize)) return null;
		if(end > maxSize) end = maxSize;
		return list.subList(start, end);
	} 
}

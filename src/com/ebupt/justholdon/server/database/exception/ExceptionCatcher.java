package com.ebupt.justholdon.server.database.exception;

import org.apache.log4j.Logger;
public class ExceptionCatcher {
	public ExceptionCatcher(){};
	public void catcher(){};
	Logger logger = Logger.getLogger(ExceptionCatcher.class);

	public void throwExp(Throwable exp) throws DBServiceException 
	{
		logger.warn("exp",exp);
		//throw new DBServiceException("DBService",exp);
	}
 
}

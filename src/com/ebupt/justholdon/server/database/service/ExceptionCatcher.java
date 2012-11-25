package com.ebupt.justholdon.server.database.service;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ExceptionCatcher {
	@Pointcut("execution (* com.ebupt.justholdon.server.database..*.*(..))")
	public void catcher(){};
	Logger logger = Logger.getLogger(ExceptionCatcher.class);
	
	@AfterThrowing(pointcut="catcher()",throwing="ex")
	public void throwExp(Exception ex) throws DBServiceException
	{
		logger.warn("Exception By DBService",ex);
		//throw new DBServiceException(ex.getMessage());
	}
	
	
}

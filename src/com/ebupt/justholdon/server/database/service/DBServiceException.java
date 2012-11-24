package com.ebupt.justholdon.server.database.service;

public class DBServiceException extends Exception{

	/**
	 * 
	 */
	private String msg;
	private static final long serialVersionUID = 1L;
	public DBServiceException(String msg)
	{
		super(msg);
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}

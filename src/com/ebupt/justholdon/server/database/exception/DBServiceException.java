package com.ebupt.justholdon.server.database.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class DBServiceException extends Exception {

	/**
	 * 
	 */
	private String msg;
	private static final long serialVersionUID = 1L;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private Throwable throwable;

	/**
	 * クラスのコンストラクタ
	 * 
	 * @param なし
	 */
	public DBServiceException() {
		super();
	}

	/**
	 * クラスのコンストラクタ
	 * 
	 * @param msg
	 *            エラーメッセージ
	 */
	public DBServiceException(String msg) {
		super(msg);

	}

	/**
	 * クラスのコンストラクタ
	 * 
	 * @param msg
	 *            エラーメッセージ
	 * @param throwable
	 */
	public DBServiceException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	/**
	 * 
	 * @param なし
	 */
	public Throwable getException() {
		throwable = super.getCause();

		return throwable;
	}

	/**
	 * 
	 * @param なし
	 */
	public void printStackTrace() {
		super.printStackTrace();
	}

	/**
	 * 
	 * @param printStream
	 */
	public void printStackTrace(PrintStream printStream) {
		super.printStackTrace(printStream);
	}

	/**
	 * 
	 * @param printWriter
	 */
	public void printStackTrace(PrintWriter printWriter) {
		super.printStackTrace(printWriter);

	}

}

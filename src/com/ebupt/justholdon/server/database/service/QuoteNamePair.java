package com.ebupt.justholdon.server.database.service;

public class QuoteNamePair {
	public QuoteNamePair(){};
	private String content;
	private String author;
	public String getContent() {
		return content;
	}
	public QuoteNamePair setContent(String content) {
		this.content = content;
		return this;
	}
	public String getAuthor() {
		return author;
	}
	public QuoteNamePair setAuthor(String author) {
		this.author = author;
		return this;
	}
	
}

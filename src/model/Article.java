package model;

import java.util.Date;

public class Article {
	private String id;
	private String title;
	private String publication;
	private String author;
	private Date date;
	private String url;
	private String content;
	
	public Article() {
		super();
	}
	
	public Article(String id, String title, String publication, String author, Date date, String url, String content) {
		super();
		this.id = id;
		this.title = title;
		this.publication = publication;
		this.author = author;
		this.date = date;
		this.url = url;
		this.content = content;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublication() {
		return publication;
	}
	public void setPublication(String publication) {
		this.publication = publication;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}

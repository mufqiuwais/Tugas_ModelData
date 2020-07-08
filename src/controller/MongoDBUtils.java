package controller;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.*;


import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;

import model.Admin;
import model.Article;

public class MongoDBUtils {	
	MongoDatabase database;
	MongoCollection<Article> collection;
	MongoCollection<Admin> adminCollection;
	final static int TOP_ARTICLES_LIMIT = 10;
	
	public MongoDBUtils() {
		// Creating Credentials 
		MongoCredential credential; 
		credential = MongoCredential.createCredential("sampleUser", "myDb", 
				"password".toCharArray()); 
		System.out.println("Connected to the database successfully");  
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));		
		 MongoClient mongo = new MongoClient("localhost", MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
		// Accessing the database 
		database = mongo.getDatabase("myDb"); 
		database = database.withCodecRegistry(pojoCodecRegistry);
		System.out.println("Credentials ::"+ credential);
		adminCollection = database.getCollection("admins", Admin.class);
		collection = database.getCollection("articles", Article.class);
		if(adminCollection.count()==0) {
			Admin admin = new Admin("admin","password");
			adminCollection.insertOne(admin);
		}
	}
	
	public boolean authenticate(String username, String password) {
		FindIterable<Admin> adminIterable = adminCollection.find();
		for (Admin admin : adminIterable) {
			if(admin.getUsername().equals(username)&&
				admin.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}
	
	public long getTotalCountArticles() {
		return collection.count();
	}

	public ArrayList<Article> getArticles() throws IOException {
		ArrayList<Article> resultList = new ArrayList<>();
		FindIterable<Article> articleIterable = collection.find();
		for (Article article : articleIterable) {
//			System.out.println(article);
			resultList.add(article);
		}		
		return resultList;
	}
	
	public ArrayList<Article> getArticlesByPage(int page) throws IOException {
		
		ArrayList<Article> resultList = new ArrayList<>();
		int first = 20*(page-1);
//		long last = first+20-1;
//		if(last>getTotalCountArticles()) {
//			last = getTotalCountArticles();
//		}
		FindIterable<Article> articleIterable = collection.find().skip(first).limit(20);
		for (Article article : articleIterable) {
//			System.out.println(article);
			resultList.add(article);
		}	
		return resultList;
	}
	
	public Article getOneArticleById(String id) throws IOException {
		Article result = new Article();
		FindIterable<Article> articleIterable = collection.find(eq("_id",id));
		for (Article article : articleIterable) {
			System.out.println(article);
			result = article;
		}		
		return result;
	}
	
	public long getTotalArticlesByKeySearch(String keySearch) throws IOException {
//		int first = 20*(page-1);
		long count=0;
		FindIterable<Article> articleIterable = collection
				.find(eq("$text",eq("$search",keySearch)));
		for (Article article : articleIterable) {
//			System.out.println(article);
			count++;
		}		
		return count;
	}
	
	public ArrayList<Article> getArticlesByKeySearch(String keySearch, int page) throws IOException {
		int first = 20*(page-1);
		ArrayList<Article> resultList = new ArrayList<>();
		FindIterable<Article> articleIterable = collection
				.find(eq("$text",eq("$search",keySearch))).skip(first).limit(20);
		for (Article article : articleIterable) {
//			System.out.println(article);
			resultList.add(article);
		}		
		return resultList;
	}
	
	public ArrayList<Article> getTopArticles() throws IOException {
		ArrayList<Article> resultList = new ArrayList<>();
		ArrayList<Document> listCommand = new ArrayList<>();
		listCommand.add(new Document("$sort",new Document("date",-1)));
		listCommand.add(new Document("$limit",TOP_ARTICLES_LIMIT));
		listCommand.add(new Document("$sort",new Document("visitors",-1)));
		AggregateIterable<Article> articleIterable = collection.aggregate(listCommand);
		for (Article article : articleIterable) {
			System.out.println(article);
			resultList.add(article);
		}		
		return resultList;
	}
	
	public ArrayList<Article> getLatestArticles() throws IOException {
		ArrayList<Article> resultList = new ArrayList<>();
		ArrayList<Document> listCommand = new ArrayList<>();
		listCommand.add(new Document("$sort",new Document("date",-1)));
		listCommand.add(new Document("$limit",TOP_ARTICLES_LIMIT));
		AggregateIterable<Article> articleIterable = collection.aggregate(listCommand);
		for (Article article : articleIterable) {
			System.out.println(article);
			resultList.add(article);
		}		
		return resultList;
	}
	
	public String generateNewId() throws IOException {
		int newId=0;
		String newStringId;
		FindIterable<Article> lastArticle = collection.find()
				.sort(new Document("_id",-1))
				.limit(1);
		for (Article article : lastArticle) {
			newId = Integer.parseInt(article.getId());
			newId++;
		}	
		newStringId = Integer.toString(newId);
		return newStringId;
	}
	
	public boolean insertData(String id, String title, String publication,
			String author, Date date, String url, String content, long visitors) {
		try {	
			Article article = new Article(id, title, publication, author,
					date, url, content, visitors);
			collection.insertOne(article);	
			System.out.println("data inserted");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
		return true;
	}
	
	public boolean updateData(String id, String title, String publication,
			String author, Date date, String url, String content, long visitors) {	
		try{
			collection.updateOne(eq("_id",id),combine(set("title", title),
					set("publication", publication),
					set("author", author),
					set("date", date),
					set("url", url),
					set("content", content),
					set("visitors", visitors)));
			return true;
		}catch (Exception e) {
			return false;
		}
	}
		
	public boolean delete(String id) {
//		DeleteResult del = collection.deleteOne(eq("_id", row));
		DeleteResult del = collection.deleteOne(eq("_id", id));
		System.out.println("del = "+del.getDeletedCount());
		return true;
	}
	
}

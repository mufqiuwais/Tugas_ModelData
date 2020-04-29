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
import java.util.List;

import org.json.*;


import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
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
	final static int TOP_ARTICLES_LIMIT = 5;
	
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
	
	public boolean insertFromJson(String path) {
JSONParser parser = new JSONParser();
JSONArray a = (JSONArray) parser.parse(new FileReader("c:\\exer4-courses.json"));

for (Object o : a)
{
JSONObject person = (JSONObject) o;
String name = (String) person.get("name");
System.out.println(name);

String city = (String) person.get("city");
System.out.println(city);

String job = (String) person.get("job");
System.out.println(job);

JSONArray cars = (JSONArray) person.get("cars");

for (Object c : cars)
{
System.out.println(c+"");
}
}
		return false;
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

	public ArrayList<Article> getArticles() throws IOException {
		ArrayList<Article> resultList = new ArrayList<>();
		FindIterable<Article> articleIterable = collection.find();
		for (Article article : articleIterable) {
			System.out.println(article);
			resultList.add(article);
		}		
		return resultList;
	}
	
	public ArrayList<Article> getTopArticles() throws IOException {
		ArrayList<Article> resultList = new ArrayList<>();
		FindIterable<Article> articleIterable = collection.find()
				.sort(new Document("visitors",-1))
				.limit(TOP_ARTICLES_LIMIT);
		for (Article article : articleIterable) {
			System.out.println(article);
			resultList.add(article);
		}		
		return resultList;
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

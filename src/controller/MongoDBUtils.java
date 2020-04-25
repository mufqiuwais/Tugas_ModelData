package controller;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.mongodb.client.result.DeleteResult;

import model.Admin;
import model.Article;

public class MongoDBUtils {	
	MongoDatabase database;
	MongoCollection<Article> collection;
	MongoCollection<Admin> adminCollection;
	
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
		if(adminCollection.count()==0) {
			Admin admin = new Admin("admin","password");
			adminCollection.insertOne(admin);
		}
	}
	
	public boolean authenticate(String username, String password) {
		adminCollection = database.getCollection("admins", Admin.class);
		ArrayList<Admin> adminList = new ArrayList<>();
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
		collection = database.getCollection("articles", Article.class);
		ArrayList<Article> resultList = new ArrayList<>();
		FindIterable<Article> articleIterable = collection.find();
		for (Article article : articleIterable) {
			System.out.println(article);
			resultList.add(article);
		}		
		return resultList;
	}
	
	public boolean insertData(String id, String title, String publication,
			String author, Date date, String url, String content) {
		collection = database.getCollection("articles", Article.class);
		try {	
			Article article = new Article(id, title, publication, author,
					date, url, content);
			collection.insertOne(article);	
			System.out.println("data inserted");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
		return true;
	}
	
	public boolean updateData(String row, String name, String city, String designation, int salary) {		
		return true;
	}
		
	public boolean delete(String row) {
//		DeleteResult del = collection.deleteOne(eq("_id", row));
		DeleteResult del = collection.deleteOne(eq("kode", row));
		System.out.println("del = "+del.getDeletedCount());
		return true;
	}
	
}

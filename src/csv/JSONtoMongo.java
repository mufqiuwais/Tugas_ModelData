package csv;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.Article;

/**
 * Java MongoDB : Convert JSON data to DBObject and insert it to dab
 *
 */

public class JSONtoMongo {
    public static void main(String[] args) throws FileNotFoundException, IOException, org.json.simple.parser.ParseException {
    	
    	List<JSONObject> ojson = new ArrayList<>();
    	JSONParser parser = new JSONParser();
    	Object obj = parser.parse(new FileReader("3000articles.json"));
		 
		// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
		JSONArray jsonArray = (JSONArray) obj;
		jsonArray.forEach(emp -> ojson.add((JSONObject)emp) );
		MongoDatabase database;
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
		MongoCollection collection = database.getCollection("articles", Article.class);

		List<Article> docs = new ArrayList<>();
		
		ojson.forEach(j -> docs.add(parseJSONtoArticle(j)));
		
		collection.insertMany(docs);
		

        System.out.println("Done");
    }
    
    public static Article parseJSONtoArticle(JSONObject json) {
    	String id = json.get("_id").toString();
		String title = json.get("title").toString();
		String publication = json.get("publication").toString();
		String author = json.get("author").toString();
    	String sdate = json.get("date").toString();
		Date date = new Date();
		try {
			date = new SimpleDateFormat("MM/dd/yyyy").parse(sdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = json.get("url").toString();
		String content = json.get("content").toString();
		
		long visitors = 0;
		Article article = new Article(id, title, publication, author,
				date, url, content, visitors);
		return article;
    }
}
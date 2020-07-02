package csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.*;
import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.util.JSON;

/**
 * Java MongoDB : Convert JSON data to DBObject and insert it to dab
 *
 */

public class JSONtoMongo {
    public static void main(String[] args) throws FileNotFoundException, IOException {
    	
    	List<String> sjson = new ArrayList<>();
    	JSONParser parser = new JSONParser();
    	try {
    		Object obj = parser.parse(new FileReader("3000articles.json"));
    		 
    		// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
    		JSONArray jsonArray = (JSONArray) obj;
    		
    		jsonArray.forEach(emp -> sjson.add(emp.toString()) );
        	// Creating a Mongo client 
    		MongoClient mongo = new MongoClient( "localhost" , 27017 ); 

    		// Creating Credentials 
    		MongoCredential credential; 
    		credential = MongoCredential.createCredential("sampleUser", "myDb", 
        				"password".toCharArray()); 
    		System.out.println("Connected to the database successfully");  

    		// Accessing the database 
    		MongoDatabase database = mongo.getDatabase("myDb");
//        		database.createCollection("testCollection"); 
    		MongoCollection<Document> collection = database.getCollection("testCollection");

    		List<Document> docs = new ArrayList<>();
    		
    		sjson.forEach(j -> docs.add((Document) Document.parse(j)));
    		
    		collection.insertMany(docs);
    	} catch (ParseException e) {
            e.printStackTrace();
        }
		

        System.out.println("Done");
    }
}
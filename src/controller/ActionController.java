package controller;



import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

import model.Article;

public class ActionController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActionController() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println("ACTION = "+action);
		MongoDBUtils mongodbUtils = new MongoDBUtils();
		if("Retrieve".equals(action)){
			showAllData(request, response, mongodbUtils);
		}else if("Insert".equals(action)){
			// TODO
			ArrayList<String> list = new ArrayList<>();
			String id = request.getParameter("id");
			String title = request.getParameter("title");
			String publication = request.getParameter("publication");
			String author = request.getParameter("author");
			String sdate = request.getParameter("date");
			Date date = new Date();
			try {
				date = new SimpleDateFormat("MM/dd/yyyy").parse(sdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String url = request.getParameter("url");
			String content = request.getParameter("content");
			
			boolean result = mongodbUtils.insertData(id, title,
					publication, author, date, url, content);
			if(result) {
				request.getRequestDispatcher("/main.jsp").forward(request, response);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
			}			
		} else if("Add Article".equals(action)){
			RequestDispatcher rd = request.getRequestDispatcher("/add_article.jsp");
			rd.forward(request, response);
		}
	}
	
	public void showAllData(HttpServletRequest request, HttpServletResponse response,
			MongoDBUtils mongodbUtils) {
		try {
			ArrayList<Article> listArticle = mongodbUtils.getArticles();
			request.setAttribute("dataList", listArticle);
			request.getRequestDispatcher("/main.jsp").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
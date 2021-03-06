package controller;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

import csv.*;
import model.Article;

public class ActionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MongoDBUtils mongodbUtils;
	String action;
	ArrayList<Article> listArticle;
	ArrayList<Article> latestArticle;
	ArrayList<Article> pageArticle;
	SimpleDateFormat simpleDate;
	Date date;
	
	public ActionController() {
		super();
		mongodbUtils = new MongoDBUtils();
		pageArticle = new ArrayList<>();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		action = request.getParameter("action");
		System.out.println("ACTION = "+action);
		if("Main Menu".equals(action)||"Guest User".equals(action)||"User Home".equals(action)){
			try {
				listArticle = mongodbUtils.getTopArticles();
				latestArticle = mongodbUtils.getLatestArticles();
				request.setAttribute("topArticles", listArticle);
				request.setAttribute("latestArticles", latestArticle);
				request.getRequestDispatcher("/user_home.jsp").forward(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}if("Details".equals(action)){
			String id = request.getParameter("id");
			Article article = mongodbUtils.getOneArticleById(id);
			request.setAttribute("article", article);
			boolean resultUpdate = mongodbUtils.updateData(id, article.getTitle(), 
					article.getPublication(), article.getAuthor(), article.getDate(),
					article.getUrl(), article.getContent(), article.getVisitors()+1);
			if(true) {
				request.getRequestDispatcher("/user_detail.jsp").forward(request, response);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
			}	
		}if("Admin Details".equals(action)){
			String id = request.getParameter("id");
			Article article = mongodbUtils.getOneArticleById(id);
			request.setAttribute("article", article);
			boolean resultUpdate = mongodbUtils.updateData(id, article.getTitle(), 
					article.getPublication(), article.getAuthor(), article.getDate(),
					article.getUrl(), article.getContent(), article.getVisitors());
			if(true) {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				request.getRequestDispatcher("/admin_detail.jsp").forward(request, response);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
			}	
		}if("List of All Articles".equals(action)||"U_Back".equals(action)
				||"U_Next".equals(action)){
			int page=0;
			long totalCount = mongodbUtils.getTotalCountArticles();
			int count;
			if("List of All Articles".equals(action)) {
				page = 1;
			}
			if("U_Back".equals(action)){
				page = Integer.parseInt(request.getParameter("page"));
				page--;
			}
			if("U_Next".equals(action)){
				page = Integer.parseInt(request.getParameter("page"));
				page++;
			}
			pageArticle = mongodbUtils.getArticlesByPage(page);
			count = pageArticle.size();
			try {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				request.setAttribute("totalCount", totalCount);
				request.setAttribute("count", count);
				request.setAttribute("page", page);
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				request.setAttribute("dataList", pageArticle);
				request.getRequestDispatcher("/user_read.jsp").forward(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}if("Admin's List of All Articles".equals(action)
				||"Back".equals(action)
				||"Next".equals(action)){
			int page=0;
			long totalCount = mongodbUtils.getTotalCountArticles();
			int count;
			if("Admin's List of All Articles".equals(action)) {
				page = 1;
			}
			if("Back".equals(action)){
				page = Integer.parseInt(request.getParameter("page"));
				page--;
			}
			if("Next".equals(action)){
				page = Integer.parseInt(request.getParameter("page"));
				page++;
			}
			pageArticle = mongodbUtils.getArticlesByPage(page);
			count = pageArticle.size();
			try {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				request.setAttribute("totalCount", totalCount);
				request.setAttribute("count", count);
				request.setAttribute("page", page);
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				request.setAttribute("dataList", pageArticle);
				request.getRequestDispatcher("/admin_read.jsp").forward(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}if("Search".equals(action)||"Back_ST".equals(action)||"Next_ST".equals(action)){
			int page=0;
			long totalCount;
			int count;
			if("Search".equals(action)) {
				page = 1;
			}
			if("Back_ST".equals(action)){
				page = Integer.parseInt(request.getParameter("page"));
				page--;
			}
			if("Next_ST".equals(action)){
				page = Integer.parseInt(request.getParameter("page"));
				page++;
			}
			String keySearch = request.getParameter("keySearch");
			totalCount = mongodbUtils.getTotalArticlesByKeySearch(keySearch);
			pageArticle = mongodbUtils.getArticlesByKeySearch(keySearch,page);
			count = pageArticle.size();
			try {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				request.setAttribute("keySearch", keySearch);
				request.setAttribute("totalCount", totalCount);
				request.setAttribute("count", count);
				request.setAttribute("page", page);
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				request.setAttribute("dataList", pageArticle);
				request.getRequestDispatcher("/admin_search_title.jsp")
					.forward(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}if("User Search".equals(action)||"U_Back_ST".equals(action)
				||"U_Next_ST".equals(action)){
			int page=0;
			long totalCount;
			int count;
			if("User Search".equals(action)) {
				page = 1;
			}
			if("U_Back_ST".equals(action)){
				page = Integer.parseInt(request.getParameter("page"));
				page--;
			}
			if("U_Next_ST".equals(action)){
				page = Integer.parseInt(request.getParameter("page"));
				page++;
			}
			String keySearch = request.getParameter("keySearch");
			totalCount = mongodbUtils.getTotalArticlesByKeySearch(keySearch);
			pageArticle = mongodbUtils.getArticlesByKeySearch(keySearch,page);
			count = pageArticle.size();
			try {
				request.setAttribute("keySearch", keySearch);
				request.setAttribute("totalCount", totalCount);
				request.setAttribute("count", count);
				request.setAttribute("page", page);
				request.setAttribute("dataList", pageArticle);
				request.getRequestDispatcher("/user_search_title.jsp")
					.forward(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}if("Admin".equals(action)||"Login Admin".equals(action)){
			RequestDispatcher rd = request.getRequestDispatcher("/admin_login.jsp");
			rd.forward(request, response);
		}if("Authentication".equals(action)||"Admin Home".equals(action)){
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			boolean result = mongodbUtils.authenticate(username, password);
			if(result) {
				listArticle = mongodbUtils.getTopArticles();
				latestArticle = mongodbUtils.getLatestArticles();
				request.setAttribute("topArticles", listArticle);
				request.setAttribute("latestArticles", latestArticle);
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				request.getRequestDispatcher("/admin_home.jsp").forward(request, response);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
			}
		}else if("Insert".equals(action)){
			String id = mongodbUtils.generateNewId();
			String title = request.getParameter("title");
			String publication = request.getParameter("publication");
			String author = request.getParameter("author");
			simpleDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			date = new Date();
			try {
				date = simpleDate.parse(simpleDate.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String url = request.getParameter("url");
			String content = request.getParameter("content");
			boolean result = mongodbUtils.insertData(id, title,
					publication, author, date, url, content, 0);
			if(result) {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				listArticle = mongodbUtils.getTopArticles();
				latestArticle = mongodbUtils.getLatestArticles();
				request.setAttribute("topArticles", listArticle);
				request.setAttribute("latestArticles", latestArticle);
				request.getRequestDispatcher("/admin_home.jsp").forward(request, response);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
			}			
		} else if("Add Article".equals(action)){
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			RequestDispatcher rd = request.getRequestDispatcher("/add_article.jsp");
			rd.forward(request, response);
		}else if("delete".equals(action)){
			String id = request.getParameter("id");
			System.out.println("ROW DELETED = "+id);
			
			boolean result = mongodbUtils.delete(id);
			if(result) {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				request.getRequestDispatcher("/admin_read.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
		}else if("to_update".equals(action)) {
			String id = request.getParameter("id");
			Article article = mongodbUtils.getOneArticleById(id);
			article.setTitle(request.getParameter("title"));
			article.setPublication(request.getParameter("publication"));
			article.setAuthor(request.getParameter("author"));
			article.setUrl(request.getParameter("url"));
			article.setContent(request.getParameter("content"));
			request.setAttribute("article", article);	
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			request.getRequestDispatcher("/update_article.jsp").forward(request, response);
		}else if("update".equals(action)) {
			String id = request.getParameter("id");
			String title = request.getParameter("title");
			String publication = request.getParameter("publication");
			String author = request.getParameter("author");
			String sdate = request.getParameter("date");
			date = new Date();
			try {
				date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(sdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String url = request.getParameter("url");
			String content = request.getParameter("content");
			long visitors = Integer.parseInt(request.getParameter("visitors"));
			System.out.println("ROW UPDATED = "+id);
			boolean resultUpdate = mongodbUtils.updateData(id, title, publication, author, date, url, content, visitors);
			if(resultUpdate) {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				request.getRequestDispatcher("/admin_read.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}			
		}
	}
}
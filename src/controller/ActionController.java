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
		if("Top Articles".equals(action)||"Main Menu".equals(action)){
			try {
				ArrayList<Article> listArticle = mongodbUtils.getTopArticles();
				request.setAttribute("dataList", listArticle);
				request.getRequestDispatcher("/main.jsp").forward(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}if("Details".equals(action)){
			String id = request.getParameter("id");
			String title = request.getParameter("title");
			String publication = request.getParameter("publication");
			String author = request.getParameter("author");
			String sdate = request.getParameter("date");
			Date date = new Date();
			try {
				date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(sdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String url = request.getParameter("url");
			String content = request.getParameter("content");
			
			long visitors = Integer.parseInt(request.getParameter("visitors"));
			Article article = new Article(id, title, publication, author,
					date, url, content, visitors+1);
			request.setAttribute("article", article);
			boolean resultUpdate = mongodbUtils.updateData(id, title, publication, author, date, url, content, visitors+1);
			if(resultUpdate) {
				request.getRequestDispatcher("/user_detail.jsp").forward(request, response);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
			}	
		}if("Search Article".equals(action)){
			try {
				ArrayList<Article> listArticle = mongodbUtils.getArticles();
				request.setAttribute("dataList", listArticle);
				request.getRequestDispatcher("/user_read.jsp").forward(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}if("Admin Search Article".equals(action)){
			try {
				ArrayList<Article> listArticle = mongodbUtils.getArticles();
				request.setAttribute("dataList", listArticle);
				request.getRequestDispatcher("/admin_read.jsp").forward(request, response);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}if("Login Admin".equals(action)){
			RequestDispatcher rd = request.getRequestDispatcher("/admin_login.jsp");
			rd.forward(request, response);
		}if("Authentication".equals(action)){
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			boolean result = mongodbUtils.authenticate(username, password);
			if(result) {
				request.setAttribute("username", username);
				request.getRequestDispatcher("/admin_main.jsp").forward(request, response);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
			}
		}else if("Insert".equals(action)){
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
					publication, author, date, url, content, 0);
			if(result) {
				request.getRequestDispatcher("/admin_main.jsp").forward(request, response);
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
			}			
		} else if("Add Article".equals(action)){
			RequestDispatcher rd = request.getRequestDispatcher("/add_article.jsp");
			rd.forward(request, response);
		}else if("delete".equals(action)){
			String id = request.getParameter("id");
			System.out.println("ROW DELETED = "+id);
			
			boolean result = mongodbUtils.delete(id);
			if(result) {
				request.getRequestDispatcher("/admin_read.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}
		}else if("to_update".equals(action)) {
			String id = request.getParameter("id");
			String title = request.getParameter("title");
			String publication = request.getParameter("publication");
			String author = request.getParameter("author");
			String sdate = request.getParameter("date");
			Date date = new Date();
			try {
				date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(sdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String url = request.getParameter("url");
			String content = request.getParameter("content");
			long visitors = Integer.parseInt(request.getParameter("visitors"));
			Article article = new Article(id, title, publication, author,
					date, url, content, visitors);
			request.setAttribute("article", article);	
			request.getRequestDispatcher("/Edit.jsp").forward(request, response);
		}else if("update".equals(action)) {
			String id = request.getParameter("id");
			String title = request.getParameter("title");
			String publication = request.getParameter("publication");
			String author = request.getParameter("author");
			String sdate = request.getParameter("date");
			Date date = new Date();
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
				request.getRequestDispatcher("/admin_read.jsp").forward(request, response);
			}else {
				request.getRequestDispatcher("/error.jsp").forward(request, response);
			}			
		}
	}
}
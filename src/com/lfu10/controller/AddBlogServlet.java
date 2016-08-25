package com.lfu10.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lfu10.dao.BlogDao;
import com.lfu10.dao.impl.BlogDaoImpl;
import com.lfu10.entity.Blog;
import com.lfu10.entity.User;

/**
 * Servlet implementation class AddBlogServlet
 */
@WebServlet("/AddBlogServlet")
public class AddBlogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBlogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String temp = (String) request.getParameter("temp");
		HttpSession session = request.getSession();
		
		User mySelf = (User) session.getAttribute("mySelf");
		int userId = mySelf.getUserId();
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		Blog blog = new Blog(userId, title, content);
		BlogDao blogDao = new BlogDaoImpl();
		boolean success = blogDao.addBlog(blog);
		if(!success) {
			System.out.println("failed to add a blog!");
			return;
		} else {
			response.getWriter().print("<script type='text/javascript'>alert('发表日志成功！');</script>");
			
			System.out.println(blog.getContent());
			
			request.getRequestDispatcher("PagingBlogServlet").forward(request, response);
		}
	}

}

package com.lfu10.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lfu10.dao.BlogDao;
import com.lfu10.dao.impl.BlogDaoImpl;
import com.lfu10.entity.Blog;
import com.lfu10.entity.User;

/**
 * Servlet implementation class BlogViewServlet
 */
@WebServlet("/BlogViewServlet")
public class BlogViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BlogViewServlet() {
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
		
		String str_blogId = (String) request.getParameter("blogId");
		int blogId = Integer.parseInt(str_blogId);
		
		/** 根据blogId 获取blog */
		BlogDao blogDao = new BlogDaoImpl();
		Blog blog = blogDao.getBlogById(blogId);
		
		/** 根据blogId 获取blogback 和 所有回复人信息 */
		ArrayList<String> blogBackContentList = blogDao.getBlogBackContentList(blog);
		ArrayList<User> blogBackUserList = blogDao.getBlogBackUserList(blog);
		
		/** */
		if(blogBackContentList == null || blogBackUserList == null) {
			System.out.println("没有任何回复内容");
		}
		
		HashMap<String, String> blogBack = new HashMap<String, String> ();
		for(int i=0, j=0; i<blogBackContentList.size() && j<blogBackUserList.size(); ++i, ++j) {
			String userName = blogBackUserList.get(j).getUserName();
			String back = blogBackContentList.get(i);
			blogBack.put(userName, back);
		}
		
		request.setAttribute("blog", blog);
		request.setAttribute("blogBack", blogBack);
		request.setAttribute("blogBackCount", blogBack == null? 0: blogBack.size());
		request.getRequestDispatcher("myblogshow.jsp").forward(request, response);
	}

}

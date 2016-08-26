package com.lfu10.controller;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class PagingBlogServlet
 */
@WebServlet("/PagingBlogServlet")
public class PagingBlogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagingBlogServlet() {
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
		int page = 1; /** 默认是第一页 */
		int size = 9; /** 每页显示多少条blog */
		int pageCount = 1;
		String s_jumpPage = (String) request.getParameter("jumpPage");
		HttpSession session = request.getSession();
		
		if(temp == null) {
			
			User mySelf = (User) session.getAttribute("mySelf");
			BlogDao blogDao = new BlogDaoImpl();
			ArrayList<Blog> blogList = blogDao.getBlogList(mySelf);
			ArrayList<Blog> pagingBlogList = new ArrayList<Blog> ();
			
			page = (s_jumpPage == null)? page: Integer.parseInt(s_jumpPage);
			for(int i=(page-1)*size; i<page*size && blogList != null && i<blogList.size(); ++i) {
				pagingBlogList.add(blogList.get(i));
			}
			blogList = pagingBlogList.isEmpty()? null: pagingBlogList;
			
			request.setAttribute("blogList", blogList);
			request.setAttribute("page", page);
			request.setAttribute("blogCount", blogList == null? 0: blogList.size());
			request.setAttribute("pageCount", blogList == null? 1: blogList.size()/size + 1);
			request.getRequestDispatcher("mybloglist.jsp").forward(request, response);

		} else {
			
			
		}
		
	}

}

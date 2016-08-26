package com.lfu10.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lfu10.dao.UserImageDao;
import com.lfu10.dao.impl.UserImageDaoImpl;
import com.lfu10.entity.User;
import com.lfu10.entity.UserImage;

/**
 * Servlet implementation class PagingUserImageServlet
 */
@WebServlet("/PagingUserImageServlet")
public class PagingUserImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagingUserImageServlet() {
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
		
		/** 根据temp值来判断这个是从friendpage.jsp 中发出的请求还是 从mypage.jsp中发出来的*/
		String temp = (String) request.getParameter("temp");
		HttpSession session = request.getSession();
		UserImageDao userImageDao = new UserImageDaoImpl();
		
		if(temp == null) {
			User mySelf = (User) session.getAttribute("mySelf");
			ArrayList<UserImage> userImageList = userImageDao.getUserImageList(mySelf.getUserId());
			
			request.setAttribute("userImageList", userImageList);
			request.getRequestDispatcher("myphoto.jsp").forward(request, response);
			
		} else {
			
			User friend = (User) session.getAttribute("friend");
			ArrayList<UserImage> userImageList = userImageDao.getUserImageList(friend.getUserId());
			request.setAttribute("userImageList", userImageList);
			request.getRequestDispatcher("friendphoto.jsp").forward(request, response);
		}
		
	}

}

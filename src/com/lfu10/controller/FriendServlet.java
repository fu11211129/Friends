package com.lfu10.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lfu10.dao.UserDao;
import com.lfu10.dao.impl.UserDaoImpl;
import com.lfu10.entity.User;

/**
 * Servlet implementation class FriendServlet
 */
@WebServlet("/FriendServlet")
public class FriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendServlet() {
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
		
		String str_friendId = request.getParameter("friendId");
		if(str_friendId == null) {
			System.out.println("没有从myfriend.jsp获取到friendId");
			return;
		}
		
		int friendId = Integer.parseInt(str_friendId);
		UserDao userDao = new UserDaoImpl();
		User friend = userDao.getUserById(friendId);
		/** 获取friend的friendlist */
		HashSet<User> friendList = userDao.getFriendList(friend);
		friend.setFriendList(friendList);
		
		HttpSession session = request.getSession();
		session.setAttribute("friend", friend);
		request.getRequestDispatcher("friendpage.jsp").forward(request, response);
	}

}

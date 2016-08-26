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

import com.lfu10.dao.FriendDao;
import com.lfu10.dao.UserDao;
import com.lfu10.dao.impl.FriendDaoImpl;
import com.lfu10.dao.impl.UserDaoImpl;
import com.lfu10.entity.User;

/**
 * Servlet implementation class BuildFriendshipServlet
 */
@WebServlet("/BuildFriendshipServlet")
public class BuildFriendshipServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuildFriendshipServlet() {
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
		
		/** 从friendnetwork.jsp 页面中获取到想要建立朋友关系的id号 */
		Integer friendId = Integer.parseInt(request.getParameter("friendId"));
		if(friendId == null) {
			System.out.println("没有获取到来自friendnetwork.jsp页面的值");
		}
		UserDao userDao = new UserDaoImpl();
		User friend = userDao.getUserById(friendId);
		
		
		HttpSession session = request.getSession();
		User mySelf = (User) session.getAttribute("mySelf");
		
		FriendDao friendDao = new FriendDaoImpl();
		boolean success = friendDao.buildFriendShip(mySelf, friend);
		if(success) {
			System.out.println("Congratulation, you make a friend with " + friend.getNickName());
			/** 更新session域中用户的信息 */
			mySelf.addFriend(friend);
			//friend.addFriend(mySelf);
			session.setAttribute("mySelf", mySelf);
		} else {
			System.out.println("Sorry, you fail to make friend with "+friend.getNickName());
		}
		
		request.getRequestDispatcher("friendnetwork.jsp").forward(request, response);
	}

}

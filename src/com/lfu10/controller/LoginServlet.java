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
import com.lfu10.util.BeanUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		
		/** 从login.jsp页面中获取用户名和密码 */
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		
		User user = BeanUtil.toUser(userName, passWord);
		UserDao userDao = new UserDaoImpl();
		User mySelf = userDao.login(user);
		
		/** 如果该用户确实存在 */
		if(mySelf != null) {			
			/** @通过user找到user的朋友
			 * @如果没有朋友返回空集合不是null  */
			HashSet<User> friendList = userDao.getFriendList(mySelf);
			mySelf.setFriendList(friendList);
			
			/** @获得目前还不是user的朋友的人的名单
			 * @如果没有未来朋友 返回空集合，不是null */
			HashSet<User> prospectiveFriendList = userDao.getProspectiveFriendList(mySelf);
			mySelf.setProspectiveFriendList(prospectiveFriendList);
			
			/** @存入session中 */
			HttpSession session = request.getSession();
			session.setAttribute("mySelf", mySelf);
			
			/** @转发到mypage.jsp页面 */
			request.getRequestDispatcher("mypage.jsp").forward(request, response);
		} 
		else {
			request.getRequestDispatcher("login.jsp").forward(request, response);
			System.out.println("wrong username or password!");
		}
	}

}

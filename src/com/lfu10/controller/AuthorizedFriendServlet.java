package com.lfu10.controller;

import java.io.IOException;

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
 * Servlet implementation class AuthorizedFriendServlet
 */
@WebServlet("/AuthorizedFriendServlet")
public class AuthorizedFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthorizedFriendServlet() {
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
		
		HttpSession session = request.getSession();
		User mySelf = (User) session.getAttribute("mySelf"); 
		int friendId = Integer.parseInt(request.getParameter("friendId"));
		UserDao userDao = new UserDaoImpl();
		User friend = userDao.getUserById(friendId);
		
		FriendDao friendDao = new FriendDaoImpl();
		if(friendDao.buildFriendShip(friend, mySelf) == true) {
			System.out.println(mySelf.getNickName()+" makes friend with "+friend.getNickName()+ " successfully!");
		}
		
		request.getRequestDispatcher("PagingFriendServlet").forward(request, response);
	}

}

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
import com.lfu10.dao.TalkDao;
import com.lfu10.dao.UserDao;
import com.lfu10.dao.UserImageDao;
import com.lfu10.dao.impl.FriendDaoImpl;
import com.lfu10.dao.impl.TalkDaoImpl;
import com.lfu10.dao.impl.UserDaoImpl;
import com.lfu10.dao.impl.UserImageDaoImpl;
import com.lfu10.entity.Talk;
import com.lfu10.entity.User;
import com.lfu10.entity.UserImage;
import com.lfu10.util.BeanUtil;

/**
 * Servlet implementation class PagingFriendServlet
 */
@WebServlet("/PagingFriendServlet")
public class PagingFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagingFriendServlet() {
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
		User user = null;
		/** 根据temp值存在与否，判断是处理自身用户的信息 还是 friend用户的信息 */
		if(temp == null) {
			user = (User) session.getAttribute("mySelf");
		} else {
			user = (User) session.getAttribute("friend");
		}
		
		HashSet<User> myFriendList = user.getFriendList();
		UserDao userDao = new UserDaoImpl();
		FriendDao friendDao = new FriendDaoImpl();
		
		ArrayList<Boolean> isFriendship = null;
		ArrayList<Talk> friendRecentTalkList = null;
		/** 如果该用户有朋友 */
		if(myFriendList != null) {
			friendRecentTalkList = new ArrayList<Talk> ();
			isFriendship = new ArrayList<Boolean> ();
			
			for(User friend: myFriendList) {
				Talk talk = userDao.getRecentTalk(friend);
				/** 如果该用户至少发表过一条说说 */
				if(talk != null) {
					friendRecentTalkList.add(talk);
				} else {
					/** 如果该用户没有发表过说说，就显示如下信息 */
					talk = BeanUtil.toTalk(user.getUserId(), "he has not publish any talks recently");
					friendRecentTalkList.add(talk);
				}
				
				boolean b = friendDao.isFriendship(user, friend);
				isFriendship.add(b);
			}
		}
		/** 调试程序提醒 */
		if(friendRecentTalkList == null) {
			System.out.println("Hay, your friends has no talks recently ... ");
		}
		
		request.setAttribute("friendRecentTalkList", friendRecentTalkList);
		request.setAttribute("isFriendship", isFriendship);
		
		if(temp == null) {
			
			request.getRequestDispatcher("myfriend.jsp").forward(request, response);
			
		} else {
			
			request.getRequestDispatcher("friend2nd.jsp").forward(request, response);
			
		}
		
	}

}

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
import com.lfu10.util.BeanUtil;

/**
 * Servlet implementation class DeleteFriendServlet
 */
@WebServlet("/DeleteFriendServlet")
public class DeleteFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFriendServlet() {
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
		
		String s_friendId = (String) request.getParameter("friendId");
		HttpSession session = request.getSession();
		User mySelf = (User) session.getAttribute("mySelf");
		
		if(s_friendId != null) {
			int friendId = (int) Integer.parseInt(s_friendId);
			UserDao userDao = new UserDaoImpl();
			User friend = userDao.getUserById(friendId);
			
			/** 将该好友从好友列表删除, 并且添加该好友到未来的好友列表中 */
			mySelf.getFriendList().remove(friend);
			mySelf.getProspectiveFriendList().add(friend);
			
			FriendDao friendDao = new FriendDaoImpl();
			//int type = friendDao.typeOfFriendShip(mySelf, friend);
			boolean success = friendDao.removeFriendShip(mySelf, friend);
				
			if(!success) {
				System.out.println("fail to delete friend");
			} else {
				//System.out.println("");
			}

			/** 更新用户信息 */
			session.setAttribute("mySelf", mySelf);
			/** 处理完删除好友业务以后，将数据控制权交给PagingFriendServlet 控制器，让它去显示好友信息*/
			request.getRequestDispatcher("PagingFriendServlet").forward(request, response);
		}
	}

}

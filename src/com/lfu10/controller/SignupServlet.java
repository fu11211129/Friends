package com.lfu10.controller;
/**
 * 处理注册业务的servlet
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lfu10.dao.UserDao;
import com.lfu10.dao.impl.UserDaoImpl;
import com.lfu10.entity.User;
import com.lfu10.util.BeanUtil;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
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
		String userName = (String) request.getParameter("userName");
		String passWord = (String) request.getParameter("passWord");
		
		User user = BeanUtil.toUser(userName, passWord);
		
		UserDao userDao = new UserDaoImpl();
		/** 首先判断是否有相同用户名的存在 */
		if(!userDao.exist(user)) {
			
			boolean success = userDao.addUser(user);
			if(success) {
				System.out.println("successfully create a user!");
			} else {
				System.out.println("fail to create a user!");
			}
			
		} else {
			System.out.println("there are some users with the same username!");
		}
	}

}

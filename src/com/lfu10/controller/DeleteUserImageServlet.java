package com.lfu10.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lfu10.dao.UserImageDao;
import com.lfu10.dao.impl.UserImageDaoImpl;

/**
 * Servlet implementation class DeleteUserImageServlet
 */
@WebServlet("/DeleteUserImageServlet")
public class DeleteUserImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserImageServlet() {
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
		
		String str_deletedUserImageId = request.getParameter("deletedUserImageId");
		UserImageDao userImageDao = new UserImageDaoImpl();
		int deletedUserImageId = Integer.parseInt(str_deletedUserImageId);
		
		if(userImageDao.deleteUserImageById(deletedUserImageId)) {
			System.out.println("userimage has been deleted!");
		} else {
			System.out.println("userimage has not been deleted!");
		}
		
		request.getRequestDispatcher("PagingUserImageServlet").forward(request, response);
	}

}

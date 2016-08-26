package com.lfu10.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lfu10.dao.TalkDao;
import com.lfu10.dao.impl.TalkDaoImpl;
import com.lfu10.entity.Talk;
import com.lfu10.entity.User;
import com.lfu10.util.BeanUtil;

/**
 * Servlet implementation class AddTalkServlet
 */
@WebServlet("/AddTalkServlet")
public class AddTalkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTalkServlet() {
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
		
		String talkContent = request.getParameter("talkContent");
		HttpSession session = request.getSession();
		User mySelf = (User) session.getAttribute("mySelf");
		
		/** 把说说内容封装成 Talk 类对象 */
		Talk talk = BeanUtil.toTalk(mySelf.getUserId(), talkContent);
		TalkDao talkDao = new TalkDaoImpl();
		
		int row_affected = talkDao.addTalk(talk);
		if(row_affected == 1) {
			System.out.println("add a talk successfully");
		} else {
			System.out.println("fail to add a talk");
		}
		request.getRequestDispatcher("addtalk.jsp").forward(request, response);
	}

}

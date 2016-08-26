package com.lfu10.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lfu10.dao.TalkBackDao;
import com.lfu10.dao.TalkDao;
import com.lfu10.dao.impl.TalkBackDaoImpl;
import com.lfu10.dao.impl.TalkDaoImpl;
import com.lfu10.entity.Talk;
import com.lfu10.entity.TalkBack;
import com.lfu10.entity.User;

/**
 * Servlet implementation class PagingTalkBackServlet
 */
@WebServlet("/PagingTalkBackServlet")
public class PagingTalkBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagingTalkBackServlet() {
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
		
		String s_talkId = (String) request.getParameter("talkId");
		if(s_talkId != null) {
			int talkId = (int) Integer.parseInt(s_talkId);
			TalkDao talkDao = new TalkDaoImpl();
			Talk talk = talkDao.getTalkByTalkId(talkId);
			
			TalkBackDao talkBackDao = new TalkBackDaoImpl();
			ArrayList<TalkBack> talkBackList = talkBackDao.getTalkBackList(talk);
			ArrayList<User> talkBackUserList = talkBackDao.getTalkBackUserList(talk);
			
			request.setAttribute("talk", talk);
			request.setAttribute("talkBackList", talkBackList);
			request.setAttribute("talkBackUserList", talkBackUserList);
			request.getRequestDispatcher("mytalkback.jsp").forward(request, response);
			
		} else {
			System.out.println("talkId 没有从mytalk.jsp 页面传过来!");
		}
	}

}

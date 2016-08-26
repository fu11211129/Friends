package com.lfu10.controller;

import java.io.IOException;
import java.util.ArrayList;

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

/**
 * Servlet implementation class PagingTalkServlet
 */
@WebServlet("/PagingTalkServlet")
public class PagingTalkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagingTalkServlet() {
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
		
		/** temp 值如果被传入到控制器中，说明是从friendpage.jsp 发出的 */
		String temp = (String) request.getParameter("temp");
		int page = 1; /** 默认是第一页 */
		int size = 9; /** 每页显示多少条说说 */
		int pageCount = 1;
		String s_jumpPage = (String) request.getParameter("jumpPage");
		HttpSession session = request.getSession();
		
		if(temp == null) {
			User mySelf = (User) session.getAttribute("mySelf");
			
			TalkDao talkDao = new TalkDaoImpl();
			ArrayList<Talk> talkList = talkDao.getTalkListByUserId(mySelf.getUserId());
			ArrayList<Talk> pagingTalkList = new ArrayList<Talk> ();
			
			/** 当jumpPage为空时，说明是默认第一页 */
			page = (s_jumpPage == null)? page: Integer.parseInt(s_jumpPage);
			
			for(int i=(page-1)*size; i<page*size && talkList != null && i<talkList.size(); ++i) {
				pagingTalkList.add(talkList.get(i));
			}
			talkList = pagingTalkList.isEmpty()? null: pagingTalkList;
			
			request.setAttribute("talkList", talkList);
			request.setAttribute("page", page);
			request.setAttribute("talkCount", talkList == null? 0: talkList.size());
			request.setAttribute("pageCount", talkList == null? 1: talkList.size()/size + 1);
			request.getRequestDispatcher("mytalk.jsp").forward(request, response);
			
		}  else {
			
			User friend = (User) session.getAttribute("friend");
			TalkDao talkDao = new TalkDaoImpl();
			ArrayList<Talk> talkList = talkDao.getTalkListByUserId(friend.getUserId());
			ArrayList<Talk> pagingTalkList = new ArrayList<Talk> ();
			
			page = (s_jumpPage == null)? page: Integer.parseInt(s_jumpPage);
			
			for(int i=(page-1)*size; i<page*size && talkList != null && i<talkList.size(); ++i) {
				pagingTalkList.add(talkList.get(i));
			}
			talkList = pagingTalkList.isEmpty()? null: pagingTalkList;
			
			request.setAttribute("talkList", talkList);
			request.setAttribute("page", page);
			request.setAttribute("talkCount", talkList == null? 0: talkList.size());
			request.setAttribute("pageCount", talkList == null? 1: talkList.size()/size + 1);
			request.getRequestDispatcher("mytalk.jsp").forward(request, response);
		}
	}

}

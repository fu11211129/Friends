package com.lfu10.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lfu10.dao.TalkDao;
import com.lfu10.dao.impl.TalkDaoImpl;

/**
 * Servlet implementation class DeleteTalkServlet
 */
@WebServlet("/DeleteTalkServlet")
public class DeleteTalkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteTalkServlet() {
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
		String deleteType = (String) request.getParameter("type");
		
		/** 如果deleteType为空的话，说明是批量删除*/
		if(deleteType == null) {
			System.out.println("we are going to delete talks");
			String select[] = request.getParameterValues("talkCheckBox");
			
			int[] deleteTalkId = null;
			
			if(select != null) {
				deleteTalkId = new int[select.length];
				for(int i=0; i<deleteTalkId.length; ++i) {
					deleteTalkId[i] = (int) Integer.parseInt(select[i]);
					System.out.print(deleteTalkId[i] + " ");
				}System.out.println();
			}
			
			if(deleteTalkId != null) {
				TalkDao talkDao = new TalkDaoImpl();
				boolean success = talkDao.batchDeleteTalk(deleteTalkId);
				if(!success) {
					System.out.println("fail to delete talks");
				}
			}
			
		} else if (deleteType.equals("singleDelete")){
			String s_deleteTalkId = (String) request.getParameter("deleteTalkId");
			
			if(s_deleteTalkId != null) {
				int deleteTalkId = (int) Integer.parseInt(s_deleteTalkId);
				TalkDao talkDao = new TalkDaoImpl();
				
				boolean success = talkDao.singleDeleteTalk(deleteTalkId);
				if(!success) {
					System.out.println("fail to delete talk");
				}
			}

		}
		
		request.getRequestDispatcher("PagingTalkServlet").forward(request, response);
	}

}

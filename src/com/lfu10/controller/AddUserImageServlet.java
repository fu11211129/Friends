package com.lfu10.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.lfu10.dao.UserImageDao;
import com.lfu10.dao.impl.UserImageDaoImpl;
import com.lfu10.entity.User;
import com.lfu10.entity.UserImage;

/**
 * Servlet implementation class AddUserImageServlet
 */
@WebServlet("/AddUserImageServlet")
public class AddUserImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserImageServlet() {
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
		/**@实现一下功能需要安装三个jar包
		 * commons-fileupload-x.x.x. jar
		 * commons-io-2.0.1. jar
		 * commons-lang3-3.1. jar
		 */
		HttpSession session = request.getSession();
		User mySelf = (User) session.getAttribute("mySelf");
		
		/**@ 构造一个userimage 对象 */
		UserImage userImage = new UserImage();
		userImage.setUserId(mySelf.getUserId());
		
		/** 使用DiskFileItemFactory 对象需要引入 org.apache.commons.fileupload jar包*/
		DiskFileItemFactory factory = new DiskFileItemFactory ();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			/** 客户端递交的请求：request 被包装成 http packet
			 * 然后我们在服务器端，准确来说是 servlet 内将提交的http报文
			 * 进行解析 */
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			
			while(iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				
				/** 如果该item不是普通文本字段，比如是图片 */
				if(!item.isFormField()) {
					if(item.getName() != null && !item.getName().equals("")) {
						/** 获取该字段（这里是图片）的全路径名，比如：
						 * "D:\MyEclipse 2015-work-space\Friends\photo collection\img1B.jpg"
						 */
						String fullFileName = item.getName();
						
						/** 截取文件自身的名字 */
						String fileName = fullFileName.substring(fullFileName.lastIndexOf('\\') + 1);
						System.out.println(fileName);
						/** 通过getSession 获取server端的“位置”（目前这么说，可能不是很准确） 
						 *  然后getServletContext 获取当前servlet所在是哪一个应用 （所有的应用都放在tomcat x.0/webapps 目录下）
						 *  继而getRealPath 定位到该应用（这里是Friends）的 /userImages 目录下
						 *  最后new File(第一个参数（位置信息）, 第二个参数传入该文件的名称，比如说 abc.jpg)
						 *  值得注意的是这一步仅仅在 内存中创立了一个指向该文件的指针，并没有“真正的”创立该文件 */
						// String path = request.getSession().getServletContext().getRealPath("photo");
						String path = "D:\\webapp_resource\\Friends\\photo";
						File file = new File(path, fileName);
						
						try {
							/** 把FileItem的内容写入到file 文件中，此时file才被真正的创建
							 *  事实上，如果是tomcat服务器，该文件的位置应该是：
							 *  ......\Tomcat x.0\webapps\Friends(project name)\photo\fileName */
							item.write(file);							
							userImage.setImgUrl(fileName);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					} else {
						System.out.println("you have not selected a file to upload yet!");
					}
					
				} 
				/** 如果是普通字段*/
				else {
					/** 比如说提交上的form表单中有<input name="imgName"/>
					 *  getFieldName 获取的就是 input的name属性值 */
					if("imgName".equals(item.getFieldName())) {
						String imgName = item.getString("UTF-8");
						userImage.setImgName(imgName);
					}					
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		
		UserImageDao userImageDao = new UserImageDaoImpl();
		boolean success = userImageDao.addUserImage(userImage);
		
		if(success) {
			System.out.println("you successfully add a user image!");
		} else {
			System.out.println("you fail to add a user image!");
		}
		
		request.getRequestDispatcher("PagingUserImageServlet").forward(request, response);
	}

}

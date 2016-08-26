/**
 * 
 */
package com.lfu10.dao;

import java.util.ArrayList;

import com.lfu10.entity.Blog;
import com.lfu10.entity.BlogBack;
import com.lfu10.entity.User;

/**
 * @author fulia
 *
 */
public interface BlogDao {
	
	public boolean addBlog(Blog blog);
	public Blog getBlogById(int blogId);
	public ArrayList<Blog> getBlogList(User user);
	public ArrayList<String> getBlogBackContentList(Blog blog);
	public ArrayList<User> getBlogBackUserList(Blog blog);
}

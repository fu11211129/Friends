package com.lfu10.dao;

import java.util.ArrayList;
import java.util.HashSet;

import com.lfu10.entity.Talk;
import com.lfu10.entity.User;

public interface UserDao {
	
	public User login(User user);
	public HashSet<User> getFriendList(User user);
	public User getUserById(int userId);
	public HashSet<User> getProspectiveFriendList(User user);
	public Talk getRecentTalk(User user);
	public boolean exist(User user);
	public boolean addUser(User user);
}

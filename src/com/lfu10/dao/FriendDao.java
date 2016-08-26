package com.lfu10.dao;

import java.sql.SQLException;

import com.lfu10.entity.User;

public interface FriendDao {

	public boolean buildFriendShip(User mySelf, User friend);
	public boolean isFriendship(User mySelf, User friend);
	
	/** 两个删除朋友的函数，第一个是删除authorized 朋友关系的方法，
	 * 另外一个是删除unauthorized 朋友关系的方法*/
	public boolean removeFriendShip(User mySelf, User friend);
	
	public String typeOfFriendShip(User mySelf, User friend);
}

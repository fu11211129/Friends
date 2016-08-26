package com.lfu10.dao;

import java.util.ArrayList;

import com.lfu10.entity.Talk;
import com.lfu10.entity.TalkBack;
import com.lfu10.entity.User;

public interface TalkBackDao {
	
	public TalkBack getTalkBackById(int id);
	public ArrayList<TalkBack> getTalkBackList(Talk talk);
	public ArrayList<User> getTalkBackUserList(Talk talk);
}

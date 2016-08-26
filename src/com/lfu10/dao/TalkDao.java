package com.lfu10.dao;

import java.util.ArrayList;

import com.lfu10.entity.Talk;

public interface TalkDao {
	
	public Talk getTalkByTalkId(int talkId);
	public ArrayList<Talk> getTalkListByUserId(int userId);
	public int addTalk(Talk talk);
	public boolean batchDeleteTalk(int[] deleteTalkId);
	public boolean singleDeleteTalk(int deleteTalkId);
}

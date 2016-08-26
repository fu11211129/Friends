package com.lfu10.dao;

import java.util.ArrayList;

import com.lfu10.entity.UserImage;

public interface UserImageDao {

	public UserImage getUserImageByImageId(int imgId);
	public ArrayList<UserImage> getUserImageList(int userId);
	public boolean addUserImage(UserImage userImage);
	public boolean deleteUserImageById(int imgId);
}

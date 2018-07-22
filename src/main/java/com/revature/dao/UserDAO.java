package com.revature.dao;

import com.revature.models.User;

public interface UserDAO {
	public User getByUserId(int userId);
	
	public User getByUsername(String username);
	
	public User getByEmail(String eMail);
	
	public User addUser(User u);
}
package com.revature.services;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.models.User;

public class UserService {	
	public User validateUser(String username, String password) {
		UserDAO userDao = new UserDAOImpl();
		// System.out.println("[LOG] UserService - validating user");
		
		//TODO: Or by email, need to restrict username options first
		User user = userDao.getByUsername(username);
		
		// If username is of a user and is the right password, return user
		if(user == null || !user.getPassword().equals(password)) {
			return null;
		} else {
			user.setPassword("*****");
			return user;
		}
	}
	
	public User addUser(String[] values) {
		UserDAO userDao = new UserDAOImpl();
		// System.out.println("[LOG] UserService - adding user");
		
		for(String value : values) {
			if(value == "")
				return null;
		}
		
		// TODO: Add more verification of values (i.e. username available)
		if(checkUsername(values[0]) == 1 || checkEmail(values[4]) == 1) {
			return null;
		}
		
		// Set values
		User user = new User();
		
		user.setUsername(values[0]);
		user.setPassword(values[1]);
		user.setFirstName(values[2]);
		user.setLastName(values[3]);
		user.setEMail(values[4]);
		user.setUserRole("Employee");
		
		// Attempt to add user
		return userDao.addUser(user);
	}
	
	// TODO: Profile changes
	
	public int checkUsername(String username) {
		UserDAO userDao = new UserDAOImpl();
		// System.out.println("[LOG] UserService - checking username");
		
		// TODO: username restrictions
		if(userDao.getByUsername(username) != null) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public int checkEmail(String eMail) {
		UserDAO userDao = new UserDAOImpl();
		// System.out.println("[LOG] UserService - checking username");
		
		// TODO: email restrictions
		if(userDao.getByEmail(eMail) != null) {
			return 1;
		} else {
			return 0;
		}
	}
}

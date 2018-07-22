package com.revature.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import com.revature.dao.ReimbursementDAO;
import com.revature.dao.ReimbursementDAOImpl;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.models.Reimbursement;
import com.revature.models.User;

public class ReimbursementService {
	public ArrayList<Reimbursement> getAllReimbursements(User u) {
		// System.out.println("[LOG] - ReimbursementService - retrieving reimbursements array");
		
		ReimbursementDAO reimbDao = new ReimbursementDAOImpl();
		if(u != null) {
			return reimbDao.viewAll(u);
		} else {
			return null;
		}
	}
	
	public ArrayList<Reimbursement> getReimbursementsByStatus(User u,int status) {
		// System.out.println("[LOG] - ReimbursementService - retrieving reimbursements array by status");
		
		ReimbursementDAO reimbDao = new ReimbursementDAOImpl();
		if(u != null) {
			return reimbDao.viewRequestsByStatus(u,status);
		} else {
			return null;
		}
	}
	
	public Reimbursement addRequest(String[] values, User u) {
		// System.out.println("[LOG] - ReimbursementService - adding reimbursement");
		
		ReimbursementDAO reimbDao = new ReimbursementDAOImpl();
		Reimbursement temp = new Reimbursement();
		
		for(String value : values) {
			// System.out.println(value);
			if(value == "")
				return null;
		}
		
		temp.setAmount(Double.parseDouble(values[1]));
		temp.setSubmitted(new java.sql.Date(new Date().getTime()));
		temp.setAuthor(u.getUserId());
		temp.setStatus(1); // Pending
		
		// Type of Reimbursement pre-defined
		// TODO: Fetch types from database and match from that
		String[] types = {"Lodging","Travel","Food","Other"};
		int typeId = 1;
		
		for(int i = 0; i < 4; i++,typeId++) {
			if(types[i].equals(values[0])) {
				break;
			}
		}
		
		temp.setType(typeId);
		
		return reimbDao.addRequest(temp);
	}
	
	public Reimbursement updateRequest(Object[] values, User u) {
		// System.out.println("[LOG] - ReimbursementService - updating reimbursement");
		
		Reimbursement temp = new Reimbursement();
		ReimbursementDAO reimbDao = new ReimbursementDAOImpl();
		
		// TODO: user values match
		
		@SuppressWarnings("unchecked")
		LinkedHashMap<String,Object> reimbValues = (LinkedHashMap<String, Object>) values[0];
		
		temp.setId((int) reimbValues.get("id"));
		temp.setAuthor((int) reimbValues.get("author"));
		temp.setResolver(u.getUserId());
		temp.setResolved(new java.sql.Date(new Date().getTime()));
		
		String approval = (String) values[1];
		
		if(temp.getAuthor() == u.getUserId()) {
			return null;
		}
		
		// TODO: Pull values from sql instead of hardcoding
		if(approval.equals("approve")) {
			temp.setStatus(2);

		} else if(approval.equals("deny")) {
			temp.setStatus(3);
		} else {
			return null;
		}
		
		return reimbDao.saveRequest(temp);
	}
	
	public String[] getHumanReadableValues(Reimbursement reimb) {
		// System.out.println("[LOG] - ReimbursementService - retrieving human readable values");
		
		String[] values = new String[4];
		
		// TODO: Get type values from SQL
		switch(reimb.getType()) {
		case 1:
			values[0] = "Lodging";
			break;
		case 2:
			values[0] = "Travel";
			break;
		case 3:
			values[0] = "Food";
			break;
		default:
			values[0] = "Other";
			break;
		}
		
		// TODO: Get status values from SQL
		switch(reimb.getStatus()) {
		case 1:
			values[1] = "Pending";
			break;
		case 2:
			values[1] = "Approved";
			break;
		case 3:
			values[1] = "Denied";
			break;
		default:
			values[1] = "";
		}
		
		values[2] = usernameByUserId(reimb.getAuthor());
		values[3] = usernameByUserId(reimb.getResolver());
		
		return values;
	}
	
	private String usernameByUserId(int userId) {
		UserDAO userDao = new UserDAOImpl();
		User user = userDao.getByUserId(userId);
		
		if(user != null) {
			return user.getFirstName() + " " + user.getLastName();
		} else {
			return "";
		}
	}

}

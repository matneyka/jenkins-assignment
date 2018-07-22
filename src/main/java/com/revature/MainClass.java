// Certain ids to Strings would be a query on that id with the associated table
// TODO: create log strings for exception todos
// TODO: Validity checks. Definitely where marked, look for other areas to do so.
// TODO: Considering the design of my ReimbDAO functions, should also check they (or an element of) don't equal an empty Reimb
// TODO: Add description, reciept, etc. (Data not saved yet)
// TODO: Add checks to returns of DAOs, they all have return values for a reason
package com.revature;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Date;
//
//import com.revature.dao.ReimbursementDAO;
//import com.revature.dao.ReimbursementDAOImpl;
//import com.revature.dao.UserDAO;
//import com.revature.dao.UserDAOImpl;
//import com.revature.models.Reimbursement;
//import com.revature.models.User;

public class MainClass {
//	static private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//	static private User user;
//	static private UserDAO userDao = new UserDAOImpl();
//	static private ArrayList<Reimbursement> reimbursements;
//	static private ReimbursementDAO reimbDao = new ReimbursementDAOImpl();
//
//	public static void main(String[] args) {
//		boolean end = false;
//
//		while(!end) {
//			end = login();
//		}
//
//		try {
//			br.close();
//		} catch (IOException e) {
//			System.out.println("[LOG] Input Error: main");
//			e.printStackTrace();
//		}
//
//	}
//
//	private static boolean login() {
//		user = new User();
//
//		System.out.println("Log In");
//
//		try {
//			System.out.println("Username: ");
//			user.setUsername(br.readLine());
//
//			System.out.println("Password:");
//			user.setPassword(br.readLine());
//
////			user = userDao.validateUser(user);
////			if(user == null) {
////				System.out.println("Login Credentials Incorrect. Please try again.");
////				System.out.println("NOTE: e-mail login not available at this time");
////				login();
////			} else {
////				if(user.getUserRole().equals("Employee")) {
////					employeeMenu();
////				} else if(user.getUserRole().equals("FinancMngr")) {
////					financeMenu();
////				} else {
////					System.out.println("[LOG] Unable to find user role. Check database.");
////				}
////
////				user = null;
////			}
//		} catch (IOException e) {
//			System.out.println("[LOG] Input Error: mainMenu");
//			e.printStackTrace();
//		}
//		return true;
//	}
//
//	private static void employeeMenu () {
//		String option;
//		Reimbursement reimb = new Reimbursement();
//		String amount = new String();
//		String type = new String();
//		String[] types = {"Lodging","Travel","Lodging","Food","Other"};
//		int typeId = 1;
//
//		System.out.println("[1] Add reimbursement request");
//		System.out.println("[2] View past tickets");
//		System.out.println("[3] Log out");
//
//		try {
//			option = br.readLine();
//
//			switch(option) {
//			case "1":
//				// Submit request
//				while(!type.matches("(Lodging)|(Travel)|(Food)|(Other)")) {
//					System.out.println("Type (Lodging, Travel, Food, Other):");
//					type = br.readLine();
//				};
//
//				while(!amount.matches("^\\d+\\.?\\d?\\d?$")) {
//					System.out.println("Amount?");
//					amount = br.readLine();
//				}
//
//				// Setting Reimbursement Object
//				reimb.setAmount(Double.parseDouble(amount));
//				reimb.setSubmitted(new java.sql.Date(new Date().getTime()));
//				reimb.setAuthor(user.getUserId());
//				reimb.setStatus(1); // Pending
//
//				for(int i = 0; i < 4; i++,typeId++) {
//					if(types[i].equals(type)) {
//						break;
//					}
//				}
//				reimb.setType(typeId);
//
//				reimbDao.addRequest(reimb);
//				employeeMenu();
//				break;
//			case "2":
//				// Find reimbursements
//				reimbursements = reimbDao.viewAll(user);
//
//				// View
//				if(reimbursements != null) {
//					for(Reimbursement reimbursement : reimbursements) {
//						System.out.println(reimbursement);
//					}
//				}
//
//				employeeMenu();
//				break;
//			case "3":
//				reimbursements = null;
//				user = null;
//				break;
//			default:
//				System.out.println("Invalid Input");
//				employeeMenu();
//				break;
//			}
//		} catch (IOException e) {
//			System.out.println("[Log] Input Error: employeeMenu");
//			e.printStackTrace();
//		}
//	}
//
//
//	// TODO: Input validity checks
//	private static void financeMenu () {
//		String option;
//		String[] stati = {"Pending","Approved","Denied"};
//		int statusId = 1;
//		String status = new String();
//
//		System.out.println("[1] View by status");
//		System.out.println("[2] View all reimbursements");
//		System.out.println("[3] Log out");
//
//		try {
//			option = br.readLine();
//
//			switch(option) {
//			case "1":
//				// get requests by status
//				while (!status.matches("(Pending)|(Approved)|(Denied)")) {
//					System.out.println("Status (Pending, Approved, Denied):");
//					status = br.readLine();
//				}
//				
//				// Setting statusID
//				for(int i = 0; i < 3; i++,statusId++) {
//					if(stati[i].equals(status)) {
//						break;
//					}
//				}
//				
//				reimbursements = reimbDao.viewRequestsByStatus(statusId);
//
//				changeRequest();
//				break;
//			case "2":
//				// get all reimbursements from all users
//				reimbursements = reimbDao.viewAll(user);
//
//				changeRequest();
//				break;
//			case "3":
//				user = null;
//				return;
//			default:
//				System.out.println("Invalid Option");
//				break;
//			}
//
//			financeMenu();
//		} catch (IOException e) {
//			System.out.println("[Log] Input Error: financeMenu");
//			e.printStackTrace();
//		}
//	}
//
//	// TODO: Input validity checks
//	private static void changeRequest() {
//		Reimbursement current = new Reimbursement();
//		String id;
//		boolean continuing = false;
//		String continueValue;
//		boolean equalToReimb = false;
//		String approval;
//
//		// view
//		for(Reimbursement reimbursement : reimbursements) {
//			System.out.println(reimbursement);
//		}
//
//		try {		
//			// get reimbursement id
//			System.out.println("Reimbursement Id:");
//			id = br.readLine();
//
//			for(Reimbursement reimbursement : reimbursements) {
//				if(Integer.parseInt(id) == reimbursement.getId()) {
//					System.out.println("entered");
//					current = reimbursement;
//					equalToReimb = true;
//					break;
//				}
//			}
//
//			// edit if pending, else error
//			if(equalToReimb) {
//				if(current.getStatus() == 1) {
//					// edit
//					System.out.println("[1] Still Pending");
//					System.out.println("[2] Approved");
//					System.out.println("[3] Denied");
//					
//					approval = br.readLine();
//					
//					if(approval.equals("2") || approval.equals("3")) {
//						current.setStatus(Integer.parseInt(approval));
//						current.setResolver(user.getUserId());
//						current.setResolved(new java.sql.Date(new Date().getTime()));
//						
//						reimbDao.saveRequest(current);
//						changeRequest();
//					} else if (approval.equals("1")){
//						System.out.println("No changes made");
//						changeRequest();
//					} else {
//						System.out.println("Invalid Input");
//						changeRequest();
//					}
//				} else if(id == "-1") {
//					return;
//				} else {
//					System.out.println("[LOG] Change Request: Is Resolved");
//				}
//			} else {
//				System.out.println("[LOG] Not related to a Reimbursement");
//			}
//
//			// Is continuing?
//			System.out.println("[1] Continue");
//			System.out.println("[2] End");
//
//			continueValue = br.readLine();
//
//			if(continueValue == "1") {
//				changeRequest();
//			} else if (continueValue == "2") {
//				return;
//			} else {
//				System.out.println("Invalid input");
//				return;
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		// if continuing this, else back to finance menu
//		if(continuing) {
//			changeRequest();
//		} else {
//			reimbursements = null;
//			financeMenu();
//		}
//	}
}

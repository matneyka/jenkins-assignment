package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.models.User;
import com.revature.util.ConnectionFactory;

public class UserDAOImpl implements UserDAO {
	@Override
	public User getByUserId(int userId) {
		return getBy("users_id",Integer.toString(userId));
	}

	@Override
	public User getByUsername(String username) {
		return getBy("username",username);
	}

	@Override
	public User getByEmail(String eMail) {
		return getBy("email",eMail);
	}
	
	private User getBy(String column, String value) {
		ArrayList<User> users = new ArrayList<>();
		User temp = new User();
		
		// Get Connection
		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			
			// Get user for comparison
			String sql = "SELECT * from users WHERE " + column + " = '" + value + "'";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// Add to the list of users
			while(rs.next()) {
				temp.setUserId(rs.getInt(1));
				temp.setUsername(rs.getString(2));
				temp.setPassword(rs.getString(3));
				temp.setFirstName(rs.getString(4));
				temp.setLastName(rs.getString(5));
				temp.setEMail(rs.getString(6));
				getUserRole(temp,rs.getInt(7));
				
				users.add(temp);
			}
			
			// Check size of array
			if(users.size() == 1) {
					return temp;
			} else if (users.size() == 0) {
				// There isn't a matching user
				return null;
			} else {
				// System.out.println("[LOG] - Check for duplicates");
				return null;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return null;	
	}

	@Override
	public User addUser(User u) {
		String[] values = new String[5];
		int userRoleId;
		String[] keys = new String[6];
		User temp = new User();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			// Values of new user
			values[0] = u.getUsername();
			values[1] = u.getPassword();
			values[2] = u.getFirstName();
			values[3] = u.getLastName();
			values[4] = u.getEMail();
			userRoleId = getUserRoleId(u.getUserRole());
			
			keys[0] = "users_id";
			keys[1] = "username";
			keys[2] = "first_name";
			keys[3] = "last_name";
			keys[4] = "email";
			keys[5] = "user_role_id";
			
			// Get insert new user
			String sql = "INSERT INTO users (username,passwd,first_name,last_name,email,user_role_id) VALUES (?,?,?,?,?,?)";

			PreparedStatement pstmt = conn.prepareStatement(sql,keys);
			
			for(int i = 0; i < values.length; i++) {
				pstmt.setString(i+1, values[i]);
			}
			
			pstmt.setInt(6, userRoleId);
			
			int rowsUpdated = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rowsUpdated == 1) {
				while(rs.next()) {
					temp.setUserId(rs.getInt(1));
					temp.setUsername(rs.getString(2));
					temp.setPassword("");
					temp.setFirstName(rs.getString(3));
					temp.setLastName(rs.getString(4));
					temp.setEMail(rs.getString(5));
					getUserRole(temp,rs.getInt(6));
				}
				
				return temp;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return null;
	}
	
	// TODO: convert to regular statement, no reason for prepared
	private void getUserRole(User u,int urid) {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "SELECT user_role FROM user_roles WHERE user_role_id = " + urid;
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				u.setUserRole(rs.getString(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int getUserRoleId(String userRole) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "SELECT user_role_id FROM user_roles WHERE user_role='" + userRole + "'";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
}
package com.revature.dao;

import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import oracle.jdbc.OracleTypes;

public class ReimbursementDAOImpl implements ReimbursementDAO {
	@Override
	public Reimbursement addRequest(Reimbursement reimb) {
		Reimbursement temp = new Reimbursement();
		String[] keys = {"reimb_id","reimb_submitted","reimb_amount","reimb_author","reimb_status_id","reimb_type_id"};
		
		if(!reimb.equals(new Reimbursement())) {
			try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
				String sql = "INSERT INTO reimbursement (reimb_amount,reimb_submitted,reimb_author,reimb_status_id,reimb_type_id) VALUES (?,?,?,?,?)";
				
				PreparedStatement pstmt = conn.prepareStatement(sql,keys);
				
				pstmt.setDouble(1, reimb.getAmount());
				pstmt.setDate(2, reimb.getSubmitted());
				pstmt.setInt(3, reimb.getAuthor());
				pstmt.setInt(4, reimb.getStatus());
				pstmt.setInt(5, reimb.getType());

				int rowsUpdated = pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				
				if(rowsUpdated != 0) {
					while(rs.next()) {
						temp = new Reimbursement();
						temp.setId(rs.getInt(1));
						temp.setSubmitted(rs.getDate(2));
						temp.setAmount(rs.getDouble(3));
						temp.setAuthor(rs.getInt(4));
						temp.setStatus(rs.getInt(5));
						temp.setType(rs.getInt(6));
					}
					
					return temp;
				} else {
					conn.rollback();
					return null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public ArrayList<Reimbursement> viewAll(User u) {
		// Different functions because different sql queries
		if(u.getUserRole().equals("Employee")) {
			// Employee
			return getEmployeeView(u);
		} else if (u.getUserRole().equals("FinancMngr")) {
			// manager
			return getManagerView(u);
		} else {
			// System.out.println("[Log] viewAll: Invalid Role. Check database");
			return null;
		}
	}

	private ArrayList<Reimbursement> getEmployeeView(User u) {
		ArrayList<Reimbursement> reimbursements = new ArrayList<>();
		Reimbursement temp;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			String sql = "Select * FROM reimbursement WHERE reimb_author = " + u.getUserId();
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				temp = new Reimbursement();
				temp.setAmount(rs.getDouble(2));
				temp.setAuthor(rs.getInt(7));
				temp.setId(rs.getInt(1));
				// temp.setReciept(rs.getBlob(6));
				temp.setResolved(rs.getDate(4));
				temp.setResolver(rs.getInt(8));
				temp.setStatus(rs.getInt(9));
				temp.setSubmitted(rs.getDate(3));
				temp.setType(rs.getInt(10));
				
				reimbursements.add(temp);
			}
			
			return reimbursements;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<Reimbursement> getManagerView(User u) {
		ArrayList<Reimbursement> reimbursements = new ArrayList<>();
		Reimbursement temp;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			String sql = "Select * FROM reimbursement";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				temp = new Reimbursement();
				temp.setAmount(rs.getDouble(2));
				temp.setAuthor(rs.getInt(7));
				temp.setId(rs.getInt(1));
				// temp.setReciept(rs.getBlob(6));
				temp.setResolved(rs.getDate(4));
				temp.setResolver(rs.getInt(8));
				temp.setStatus(rs.getInt(9));
				temp.setSubmitted(rs.getDate(3));
				temp.setType(rs.getInt(10));
				
				reimbursements.add(temp);
			}
			
			return reimbursements;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// @Override
	// public ArrayList<Reimbursement> viewRequestsById(int uid) {}

	@Override
	public ArrayList<Reimbursement> viewRequestsByStatus(User u, int status) {
		ArrayList<Reimbursement> reimbursements = new ArrayList<>();
		Reimbursement temp;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			String sql;
			if(u.getUserRole().equals("FinancMngr")) {
				sql = "Select * FROM reimbursement WHERE reimb_status_id = " + status;
			} else {
				sql = "Select * FROM reimbursement WHERE reimb_status_id = " + status + " AND reimb_author = " + u.getUserId();
			}
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				temp = new Reimbursement();
				temp.setAmount(rs.getDouble(2));
				temp.setAuthor(rs.getInt(7));
				temp.setId(rs.getInt(1));
				// temp.setReciept(rs.getBlob(6));
				temp.setResolved(rs.getDate(4));
				temp.setResolver(rs.getInt(8));
				temp.setStatus(rs.getInt(9));
				temp.setSubmitted(rs.getDate(3));
				temp.setType(rs.getInt(10));
				
				reimbursements.add(temp);
			}
			
			return reimbursements;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Reimbursement saveRequest(Reimbursement reimb) {
		Reimbursement temp = new Reimbursement();
		// Use a function/procedure to save request
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			String sql = "{CALL update_reimbursement(?,?,?,?,?)";
			
			CallableStatement cstmt = conn.prepareCall(sql);
			
			cstmt.setInt(1, reimb.getId());
			cstmt.setInt(2, reimb.getStatus());
			cstmt.setInt(3, reimb.getResolver());
			cstmt.setDate(4, reimb.getResolved());
			cstmt.registerOutParameter(5, OracleTypes.CURSOR);
			
			cstmt.execute();
			
			ResultSet rs = (ResultSet) cstmt.getObject(5);
			
			while(rs.next()) {
				temp.setAmount(rs.getDouble(2));
				temp.setAuthor(rs.getInt(7));
				temp.setId(rs.getInt(1));
				// temp.setReciept(rs.getBlob(6));
				temp.setResolved(rs.getDate(4));
				temp.setResolver(rs.getInt(8));
				temp.setStatus(rs.getInt(9));
				temp.setSubmitted(rs.getDate(3));
				temp.setType(rs.getInt(10));
			}
			
			return temp;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

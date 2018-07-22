package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.services.ReimbursementService;

@WebServlet("/addrequest")
public class addRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// System.out.println("[LOG] - Request sent to LogoutServlet.doGet().");
		// System.out.println("[LOG] - Redirecting to LogoutServlet.doPost()");
		
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// System.out.println("[LOG] - Request sent to addRequestServlet.doPost");
		ReimbursementService reimbService = new ReimbursementService();
		User u;
		HttpSession session = req.getSession();
		Reimbursement temp;
		ObjectMapper mapper = new ObjectMapper();
		
		u = (User) session.getAttribute("user");
		
		if(req.getInputStream() != null) {
		String[] reimbInfo = mapper.readValue(req.getInputStream(), String[].class);
		
		if(reimbInfo.length == 2) {
			temp = reimbService.addRequest(reimbInfo, u);

		} else {
			temp = null;
		}
		
		PrintWriter pw = resp.getWriter();
		resp.setContentType("application/json");
		
		pw.write(mapper.writeValueAsString(temp));
		}
	}
}

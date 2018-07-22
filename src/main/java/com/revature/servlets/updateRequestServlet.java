package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

@WebServlet("/updaterequest")
public class updateRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// System.out.println("[LOG] - Request sent to LogoutServlet.doGet().");
		// System.out.println("[LOG] - Redirecting to LogoutServlet.doPost()");
		
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// System.out.println("[LOG] - request sent to updateRequestServlet.doPost()");
		
		ReimbursementService reimbService = new ReimbursementService();
		User u;
		HttpSession session = req.getSession();
		Reimbursement temp = new Reimbursement();
		ObjectMapper mapper = new ObjectMapper();
		String[] humanReadableValues;
		ArrayList<Object> valuesToSend = new ArrayList<>();

		u = (User) session.getAttribute("user");

		if(req.getInputStream() != null) {
			Object[] values = mapper.readValue(req.getInputStream(), Object[].class);
			
			temp = reimbService.updateRequest(values, u);
			humanReadableValues = reimbService.getHumanReadableValues(temp);
			
			valuesToSend.add(temp);
			valuesToSend.add(humanReadableValues);
		} else {
			valuesToSend = null;
		}

		PrintWriter pw = resp.getWriter();
		resp.setContentType("application/json");

		pw.write(mapper.writeValueAsString(valuesToSend));
		
		
	}
}

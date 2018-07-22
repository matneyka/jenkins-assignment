package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.services.UserService;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// System.out.println("[LOG] - Request sent to LogoutServlet.doGet().");
		// System.out.println("[LOG] - Redirecting to LogoutServlet.doPost()");

		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// System.out.println("[LOG] - Request sent to RegistrationServlet.doPost()");

		User temp;
		UserService userService = new UserService();

		if(req.getInputStream() != null) {
			// Get values
			ObjectMapper mapper = new ObjectMapper();
			String[] userInfo = mapper.readValue(req.getInputStream(), String[].class);

			if(userInfo.length == 5) {
				// Attempt to register
				temp = userService.addUser(userInfo);

				if(temp != null) {
					temp.setPassword("****");
				}
			} else {
				temp = null;
			}

			// return response
			PrintWriter pw = resp.getWriter();
			resp.setContentType("application/json");
			pw.write(mapper.writeValueAsString(temp));
		}
	}
}

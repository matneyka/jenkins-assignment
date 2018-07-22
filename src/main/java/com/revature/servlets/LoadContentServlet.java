package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.util.RequestViewHelper;

@WebServlet("*.view")
public class LoadContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// System.out.println("[LOG] - Reqest sent to LoadContentServlet.doGet()");
		
		String nextView = new RequestViewHelper().process(req,resp);
		// System.out.println(nextView);
		
		req.getRequestDispatcher(nextView).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// System.out.println("[LOG] - Request sent to LogoutServlet.doPost()");
		// System.out.println("[LOG] - Redirecting to LogoutServlet.doGet()");
		
		doGet(req,resp);
	}
}

package com.revature.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestViewHelper {
	public String process(HttpServletRequest req, HttpServletResponse reps) {
		// System.out.println("[LOG] - Processing request with RequestViewHelper.process()");
		
		switch(req.getRequestURI()) {
		case "/jenkins-assignment/login.view":
			return "partials/login.html";
		case "/jenkins-assignment/register.view":
			return "partials/register.html";
		case "/jenkins-assignment/dashboard.view":
			return "partials/dashboard.html";
		case "/jenkins-assignment/addrequest.view":
			return "partials/addrequest.html";
		case "/jenkins-assignment/request.view":
			return "partials/request.html";
		default:
			return null;
		}
	}
}

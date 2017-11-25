package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;


public class PaymentServlet extends HttpServlet implements Servlet {
	public PaymentServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		Boolean isValidSession = false;
		if(session != null) {
			isValidSession = true;
			Item item = (Item) session.getAttribute("item");
			request.setAttribute("item", item);
		}

		request.setAttribute("isValidSession", isValidSession);
		request.getRequestDispatcher("/payment.jsp").forward(request, response);
	}

}
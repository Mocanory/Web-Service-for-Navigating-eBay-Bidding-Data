package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class ConfirmationServlet extends HttpServlet implements Servlet {
	public ConfirmationServlet() {}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Boolean isValidSession = false;
		Boolean isValidCard = false;
		if(session != null) {
			isValidSession = true;
			Item item = (Item) session.getAttribute("item");
			request.setAttribute("item", item);
			String cardNum = request.getParameter("cardNum");
			if(isValid(cardNum)){
					isValidCard = true;
        	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        	Date date = new Date();
					String paymentTime = dateFormat.format(date).toString();
					request.setAttribute("item", item);
					request.setAttribute("cardNum", cardNum);
					request.setAttribute("time", paymentTime);
		}
		request.setAttribute("isValidSession", isValidSession);
		request.setAttribute("isValidCard", isValidCard);
		request.getRequestDispatcher("confirmation.jsp").forward(request, response);
		}
	}

	final int HOME_PORT = 1448;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "http://" + request.getServerName() + ':' + HOME_PORT + request.getContextPath();
		response.sendRedirect(url);
		return;
	}

	private Boolean isValid (String cardNum){
		if (cardNum == null || cardNum == "") return false;
		int len = cardNum.length(), total = 0, temp;
		Boolean isOdd = (len & 1) == 1;
		for (int i = 0; i < len; i++) {
			if(cardNum.charAt(i)<'0' || cardNum.charAt(i)>'9') return false;
			temp = Integer.valueOf(String.valueOf(cardNum.charAt(i)));
			if (!isOdd && (i & 1) == 1) temp <<= 1;
			if (isOdd && ((i + 1) & 1) == 1) temp <<= 1;
			if (temp > 9) temp -= 9;
			total += temp;
		}
		return total % 10 == 0;
	}

}

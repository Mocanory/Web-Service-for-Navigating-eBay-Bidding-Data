<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="edu.ucla.cs.cs144.*" %>


<!DOCTYPE html>
<html>
  <head>
  	<link rel="stylesheet" href="css/payment.css"></link>


  	<!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  </head>

  <body>
  	<%
  	// 1046290388
      final int SECURE_PORT = 8443;
  		Boolean isValidSession = (Boolean)request.getAttribute("isValidSession");
      Item item = (Item)request.getAttribute("item");
	  	if(isValidSession == true && item != null) {
		    String itemID = item.getID();
		    String itemName = item.getName();
		    String buyPrice = item.getBuyPrice();
		    String url = "https://" + request.getServerName() + ':' + SECURE_PORT + request.getContextPath() + "/confirm";
	%>

	<%-- <%=isValidSession%> --%>
    <div class="payment">
    	<h1 style="text-align: center">Payment</h1>
      <br>
      <div style="margin-left: 10%">
    	   <p><span>Item ID:</span> <%=itemID%></p>
    	   <p><span>Item Name:</span> <%=itemName%></p>
    	   <p><span>Buy Price:</span> <%=buyPrice%></p>
      </div>
    	<form method="POST" action="<%=url%>" style="text-align: center">
    		<p>
    			<span style="float: left;	margin-left: 10%; padding-top: 3px;">Credit Card Number: &nbsp; &nbsp;</span>
    			<input class="form-control" type="text" name="cardNum" placeholder="Please enter your credit card number" style="width: 50%; font-size: 20px">
    		</p>
    		<br>
    		<input type="submit" class="btn btn-primary">
    		<br>
    		<br>
    	</form>
    </div>

    <%
    	} else {
    %>
    <script type="text/javascript">
    	alert("Invalid Session!");
      top.location='index.html';
    </script>

    <% } %>




  </body>

</html>

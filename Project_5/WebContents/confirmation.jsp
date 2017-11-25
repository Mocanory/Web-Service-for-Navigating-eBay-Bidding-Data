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
      Boolean isValidSession = (Boolean)request.getAttribute("isValidSession");
  		Boolean isValidCard = (Boolean)request.getAttribute("isValidCard");
      Item item = (Item)request.getAttribute("item");
	  	if(isValidSession == true && isValidCard == true && item != null) {
		     String itemID = item.getID();
		     String itemName = item.getName();
		     String buyPrice = item.getBuyPrice();
		     String cardNum = (String)request.getAttribute("cardNum");
         String time = (String)request.getAttribute("time");

	%>

    <div class="payment">
      <h1 style="text-align: center">Confirmation</h1>
      <div style="margin-left: 10%">
    	   <p><span>Item ID:</span> <%=itemID%></p>
    	   <p><span>Item Name:</span> <%=itemName%></p>
    	   <p><span>Buy Price:</span> <%=buyPrice%></p>
         <p><span>Credit Card Numer:</span> <%=cardNum%></p>
         <p><span>Time:</span> <%=time%></p>
         <br>
      </div>
      <div style="margin:0 auto; width: 100%; text-align: center">
      <button class="btn btn-primary" style="height: 40px; padding: 1px 7px; font-size: 25px">
      <a href="confirm" style="color: white;">Return to HomePage</a></button>
      </div>
      <br>
    </div>

  <%
} else if (isValidSession == true && isValidCard == false) {
  %>
    <div class="payment">
      <h1 style="text-align: center">
          Invalid Credit Card! Please try again... <br>
          Return back to payment in <span id="countNum">4</span> second(s);
      </h1>

      <br>
    </div>
    <script type="text/javascript">
    countNum = 4;
    function goBack(){
      document.getElementById('countNum').innerHTML=--countNum;
      if(countNum == 0) window.history.back(-1);
      else setTimeout("goBack()",1000);
    }
    setTimeout("goBack()",1000);
    </script>
  <%
} else {
  %>
  <script type="text/javascript">
    alert("Invalid Session!");
    top.location='confirm';
  </script>
  <%
}
  %>
  </body>
</html>

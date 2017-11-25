<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="edu.ucla.cs.cs144.*" %>

<!DOCTYPE html>
<html>
  <head>
    <title> eBay Keyword Search</title>
    <link rel="stylesheet" href="css/itemDetail.css"></link>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">

    <!-- jQuery library -->
	  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script async defer type="text/javaScript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAiyqbwQt1mOfB9QxPQSzytvqEiP_qk0RE"></script>


    <%
      Boolean itemNotFound = (Boolean)request.getAttribute("itemnotfound");
      Item item = new Item();
      String category_list[] = {};
      Location location = new Location();
      Bid bids[] = {};
      User seller = new User();
      String categories="";

      if(!itemNotFound) {
        item = (Item)request.getAttribute("item");
        category_list = item.getCategories();
        location = item.getLocation();
        bids = item.getBids();
        seller = item.getSeller();

        for(int i=0; i<category_list.length; ++i)
          categories += (' '+category_list[i]+';');
        categories = categories.substring(0,categories.length() - 1);
      }

    %>
    <script type="text/javascript">
      function loadMap(){
          var latitude = parseFloat("<%=location.getLatitude()%>");
          var longitude = parseFloat("<%=location.getLongitude()%>");
          if (isNaN(latitude) || isNaN(longitude)) return;
          var latlng  = new google.maps.LatLng(latitude, longitude);
          var myOptions = { zoom: 12, center: latlng};
          var map = new google.maps.Map(document.getElementById('map'), myOptions);
          var marker = new google.maps.Marker({position: latlng, map: map});
      }

      $('input').keydown(function(e){
        if(e.keyCode == 13){
          $(this).closest('form').submit();
        }
      });
    </script>


  </head>
  <% if(itemNotFound){ %>
  <body>
  <% } else {%>
  <body onload="loadMap()">
  <% } %>
    <div id="searchBar">
      <a href="/eBay"><img src="https://www.iabeurope.eu/wp-content/uploads/2016/01/EBay_former_logo.svg.png" width=100px height=40px></a>
      <form method="GET" action="item" id="form">
      	<div class="searchbox">
      		<a href="javascript:{}" class="glyphicon glyphicon-search" type="button"
          onclick="document.getElementById('form').submit();"></a>
        	<input class="form-control" type="text" name="id" value="" placeholder="<%=itemNotFound?"Please enter valid itemID to search...":item.getID()%>" />
        </div>
      </form>
    </div>

    <%
      if(itemNotFound){
    %>
        <br>
        <h4 style="margin-left: 10%">Item not found.</h4>
    <%
      } else {
    %>
        <div class="container">
          <div class="itemDetail">
            <div class="leftpanel">
              <h3 style="color:grey;">Item <%=item.getID()%></h3>
              <br>
              <p><span>Name</span> <%=item.getName()%></p>
              <p><span>Category</span> <%=categories%></p>
              <p><span>Current Price</span> <%=item.getCurrently()%></p>
              <p><span>First Bid</span> <%=item.getFirstBid()%></p>
              <%  if (item.getBuyPrice()!=null){ %>
              <p>
                <span>Buy Price</span> <%=item.getBuyPrice()%>
                <button class="btn btn-primary" style="margin-left: 10px; height: 30px; padding: 1px 7px;"
              ><a href="/eBay/payment" style="color: white;">Pay Now</a></button>
              </p>
              <%  }                              %>
              <p><span>Start time</span> <%=item.getStarted()%></p>
              <p><span>End time</span> <%=item.getEnds()%></p>
              <p><span>Location</span> <%=location.getName()%></p>
              <p><span>Country</span> <%=item.getCountry()%></p>
              <%  if (item.getDescription()!=null){ %>
              <p style="text-align: justify;"><span>Description</span> <%=item.getDescription()%></p>
              <%  }                                 %>

              <hr>

              <%  if (!item.getNumberOfBids().equals("0")){ %>
              <div class="biddingHistory">
                <h4>Bidding History</h4>
                <%  for (int i=0; i<bids.length; ++i){ %>
                <div class="bid">
                  <div class="pricebox">
                    <p><%=bids[i].getAmount()%></p>
                  </div>
                  <div class="bidInfo">
                    <p><span>Buyer ID</span> <%=bids[i].getBidder().getID()%></p>
                    <p><span>Rating</span> <%=bids[i].getBidder().getRating()%></p>
                    <%  if (bids[i].getBidder().getLocation()!=null){ %>
                    <p><span>Location</span> <%=bids[i].getBidder().getLocation()%></p>
                    <%  }                                             %>
                    <%  if (bids[i].getBidder().getCountry()!=null){  %>
                    <p><span>Country</span> <%=bids[i].getBidder().getCountry()%></p>
                    <%  }                                             %>
                    <p><span>Time</span> <%=bids[i].getTime()%></p>
                  </div>
                </div>
                <br>
                <%  }                                  %>
                <!-- end of bid -->
              </div>
              <%  }                                 %>
              <!-- end of bidding history -->
              <br>
              <br>
              <br>
              <br>
              <br>
              <br>
            </div>
            <!-- end of left panel -->
            <div class="rightpanel">
              <div class="sellerInfo">
                <h4>Seller Information</h4>
                <p><span>User ID</span> <%=seller.getID()%></p>
                <p><span>Rating</span> <%=seller.getRating()%></p>
              </div>

              <div id="map"></div>

            </div>
            <!-- end of rightpanel -->
          </div>
          <!-- end of itemDetail -->
        </div>
        <!-- end of container -->
      <% } %>
      <!-- end of else -->
   </body>
</html>

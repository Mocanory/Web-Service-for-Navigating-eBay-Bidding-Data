<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="edu.ucla.cs.cs144.*" %>


<!DOCTYPE html>
<html>
  <head>
    <title> eBay Keyword Search</title>
    <link rel="stylesheet" href="css/searchResult.css"></link>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
    <!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


    <link rel="stylesheet" type="text/css" href="css/autosuggest2.css"></link>
    <script type="text/javascript" src="js/autosuggest.js"></script>
    <script type="text/javascript" src="js/suggestions.js"></script>

    <script type="text/javascript">
      window.onload = function () {
        var oTextbox = new AutoSuggestControl(document.getElementById("searchbox"), new KeywordSearchSuggestions());
      }
    </script>

  </head>

  <body>
    <div id="searchBar">
      <a href="/eBay"><img src="https://www.iabeurope.eu/wp-content/uploads/2016/01/EBay_former_logo.svg.png" width=100px height=40px></a>

      <form method="GET" action="search" id="form">
      	<div class="searchbox">
      		<input style="display:none"/>
      		<a href="javascript:{}" class="glyphicon glyphicon-search" type="button"
      		onclick="document.getElementById('form').submit();"></a>
        	<input class="form-control" type="text" name="q" value="" id="searchbox" autocomplete="off" placeholder="<%=(String)request.getAttribute("query")%>" />
        </div>
        <input type="hidden" name="numResultsToSkip" value="0">
        <input type="hidden" name="numResultsToReturn" value="20">
        <!-- <button type="submit" class="btn btn-primary" style="float: right">&nbsp;Search&nbsp;</button> -->
      </form>
      <p></p>
    </div>

    <br>
	<div id="searchResult">
		<%
		String query = (String)request.getAttribute("query");
		int numResultsToSkip = (Integer)request.getAttribute("numResultsToSkip");
		int numResultsToReturn = (Integer)request.getAttribute("numResultsToReturn");
		int numTotalResults = (Integer)request.getAttribute("numTotalResults");
		SearchResult[] searchResult = (SearchResult[])request.getAttribute("searchResult");

		int prevNumResultsToSkip = numResultsToSkip - numResultsToReturn;
		int disablePrev = prevNumResultsToSkip >= 0 ? 0 : 1;

		int numResultsLeft = numTotalResults - (numResultsToSkip + numResultsToReturn);
		int nextNumResultsToSkip = numResultsToSkip + numResultsToReturn;
		int disableNext = numResultsLeft > 0 ? 0 : 1;
		%>

		<p><%=numTotalResults%> results found.</p>

		<%
		for (int i=0; i<searchResult.length;i++){
			SearchResult result = searchResult[i];
		%>
			<p><a href="item?id=<%=result.getItemId()%>"><%= result.getItemId() %></a> <%= result.getName() %></p>
		<%
		}
		%>
	</div>

	<br>
	<br>
	<nav aria-label="Page navigation" id="pagination">
	  <ul class="pagination">
	    <li>
	      <a href="search?q=<%=query%>&numResultsToSkip=<%=numResultsToSkip-numResultsToReturn%>&numResultsToReturn=<%=numResultsToReturn%>" aria-label="Previous" id="prev">
	        <span aria-hidden="true">&laquo;&nbsp; Previous</span>
	      </a>
	    </li>
	    <li>
	      <a href="search?q=<%=query%>&numResultsToSkip=<%=numResultsToSkip+numResultsToReturn%>&numResultsToReturn=<%=numResultsToReturn%>" aria-label="Next" id="next">
	        <span aria-hidden="true">&raquo;&nbsp; Next</span>
	      </a>
	    </li>
	  </ul>
	</nav>

	<script type="text/javascript">
		$('input').keydown(function(e){
			if(e.keyCode == 13){
				$(this).closest('form').submit();
			}
		});

		var query = "<%=query%>"
		var numResultsToSkip = "<%= prevNumResultsToSkip%>"
		var numResultsToReturn = "<%=numResultsToReturn%>"

		// enable and disable previous page
		var disablePrev ="<%=disablePrev%>"
		var prev = document.getElementById("prev");
		if(disablePrev == 1){
			prev.disabled = true;
			prev.removeAttribute('href');
        	prev.style.color = 'grey';
        	prev.style.cursor = 'default';
        }
        else{
        	prev.disabled = false;
        	var url = "search?q=" + query + "&numResultsToSkip=" + numResultsToSkip + "&numResultsToReturn=" + numResultsToReturn;
        	var attribute = document.createAttribute("href");
        	attribute.value = url;
        	prev.setAttributeNode(attribute);
			prev.style.color = 'steelblue';
        	prev.style.cursor = 'hand';
        }

        // enable and disable next page
        var disableNext ="<%=disableNext%>"
		var next = document.getElementById("next");
		if(disableNext == 1) {
			next.disabled = true;
			next.removeAttribute('href');
        	next.style.color = 'grey';
        	next.style.cursor = 'default';
		}
		else {
			next.disabled = false;
			var nextNumResultsToSkip = "<%=nextNumResultsToSkip%>"
        	var url = "search?q=" + query + "&numResultsToSkip=" + nextNumResultsToSkip + "&numResultsToReturn=" + numResultsToReturn;
        	var attribute = document.createAttribute("href");
        	attribute.value = url;
        	next.setAttributeNode(attribute);
			next.style.color = 'steelblue';
        	next.style.cursor = 'hand';
		}

	</script>

    </div>
  </body>
</html>

package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.*;

public class SearchServlet extends HttpServlet implements Servlet {

    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      try{
        	String query = request.getParameter("q");
        	String resultsToSkip = request.getParameter("numResultsToSkip");
        	String resultsToReturn = request.getParameter("numResultsToReturn");

          if(query == null || query.equals("")){
            response.sendRedirect("keywordSearch.html");
            return;
          }

          int numResultsToSkip, numResultsToReturn;
          if (resultsToSkip == null) numResultsToSkip = 0;
          else numResultsToSkip = Integer.parseInt(resultsToSkip);
          if (numResultsToSkip < 0) numResultsToSkip = 0;
        	if (resultsToReturn == null) numResultsToReturn = 20;
        	else numResultsToReturn = Integer.parseInt(resultsToReturn);
          if (numResultsToReturn < 0) numResultsToReturn = 20;

        	AuctionSearch searchEngine = new AuctionSearch();
        	int numTotalResults = searchEngine.basicSearch(query, 0, Integer.MAX_VALUE).length;
        	SearchResult[] searchRes = searchEngine.basicSearch(query, numResultsToSkip, numResultsToReturn);


        	request.setAttribute("searchResult", searchRes);
        	request.setAttribute("query", query);
        	request.setAttribute("numResultsToSkip", numResultsToSkip);
        	request.setAttribute("numResultsToReturn", numResultsToReturn);
        	request.setAttribute("numTotalResults", numTotalResults);

          request.getRequestDispatcher("/searchResult.jsp").forward(request, response);
        } catch (NumberFormatException e){
          response.sendRedirect("keywordSearch.html");
          return;
        }
    }
}

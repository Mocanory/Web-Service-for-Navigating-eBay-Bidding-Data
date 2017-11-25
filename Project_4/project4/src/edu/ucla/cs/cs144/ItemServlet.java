package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItemServlet extends HttpServlet implements Servlet {

    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Input data
        String itemID = request.getParameter("id");
        if(itemID == null || itemID.equals("")){
          response.sendRedirect("getItem.html");
          return;
        }
        // Query for string
        AuctionSearch searchEngine = new AuctionSearch();
        String item_XML = searchEngine.getXMLDataForItemId(itemID);
        // Error handling
        if(item_XML == null || item_XML.equals("")){
            Boolean itemnotfound = true;
            request.setAttribute("itemnotfound", itemnotfound);
        } else {
            Boolean itemnotfound = false;
            // Extract message
            Item item = MyParser.parseXML(item_XML);
            item.orderBids(); // reorder the bids list
            // // Setting attributes
            request.setAttribute("itemnotfound", itemnotfound);
            request.setAttribute("item",item);
        }
        // forward to the jsp
        request.getRequestDispatcher("/itemDetail.jsp").forward(request, response);
    }
}

// AIzaSyCqsrpFZKZWVEgRsggHp0aKItx4L2jrYLo

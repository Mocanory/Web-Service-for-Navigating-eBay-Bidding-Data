package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        try {
        	String query = request.getParameter("q");
        	URL url = new URL("http://google.com/complete/search?output=toolbar&q=" + URLEncoder.encode(query, "UTF-8"));
        	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        	conn.connect();

        	BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        	StringBuilder sb = new StringBuilder();

        	String line = "";
        	while((line = reader.readLine()) != null){
        		sb.append(line);
        	}
        	reader.close();
        	conn.disconnect();

        	response.setContentType("text/xml");
        	PrintWriter res = response.getWriter();
        	res.println(sb.toString());


        } catch (Exception e) {
        	e.printStackTrace();
        }


    }
}

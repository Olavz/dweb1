package com.olavz.demo.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GraphServlet extends HttpServlet {

    public void init() throws ServletException {
        // Read a file and process it for JSON
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println("<pre>ok</pre>");


        // TODO: Get menu options for data available to group by.

        // TODO: Present datastructure straight for PP with Chart.
    }

}

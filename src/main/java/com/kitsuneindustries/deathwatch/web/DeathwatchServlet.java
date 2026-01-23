package com.kitsuneindustries.deathwatch.web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/*")
public class DeathwatchServlet extends HttpServlet {

    private static final long serialVersionUID = -7539935506921331750L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/http");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello from DeathwatchServlet</h1>");
    }

}

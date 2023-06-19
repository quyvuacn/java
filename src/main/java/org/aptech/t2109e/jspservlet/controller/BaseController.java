package org.aptech.t2109e.jspservlet.controller;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class BaseController extends HttpServlet {

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected void view(String viewPath) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher(viewPath);
        view.forward(request, response);
    }

    protected void redirect(String url) throws IOException {
        response.sendRedirect( url);
    }
}

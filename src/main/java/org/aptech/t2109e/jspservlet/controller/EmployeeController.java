package org.aptech.t2109e.jspservlet.controller;

import org.aptech.t2109e.jspservlet.common.StatusCode;
import org.aptech.t2109e.jspservlet.entity.Employee;
import org.aptech.t2109e.jspservlet.service.impl.EmployeeServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class EmployeeController extends BaseController {

    private EmployeeServiceImpl employeeService;

    public void init() {
        employeeService = new EmployeeServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.request = request;
        this.response = response;

        String action = request.getServletPath();

        switch (action) {
            case "/":
                employee();
                break;
            case "/list":
                list();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.request = request;
        this.response = response;

        String action = request.getPathInfo();
        action = action == null ? "/" : action;

        switch (action) {
            case "/":
                store();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
    }


    private void list() throws ServletException, IOException {
        String search = request.getParameter("search");
        String pageParam = request.getParameter("page");
        String limitParam = request.getParameter("limit");

        int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        int limit = (limitParam != null) ? Integer.parseInt(limitParam) : 5;

        var result = employeeService.filterAndPagination(search, page, limit);

        if (result.statusCode == StatusCode.OK) {
            request.setAttribute("employees", result.data);
        }

        view("/view/list.jsp");
    }

    private void employee() throws ServletException, IOException {
        view("/view/employee.jsp");
    }

    private void store() throws IOException {
        String fullName = request.getParameter("fullName");
        String birthday = request.getParameter("birthday");
        String address = request.getParameter("address");
        String position = request.getParameter("position");
        String department = request.getParameter("department");

        Employee employee = new Employee(fullName, birthday, address, position, department);

        employeeService.create(employee);

        redirect("/employee/list");
    }

}

package org.aptech.t2109e.jspservlet.service;

import org.aptech.t2109e.jspservlet.common.ResultService;
import org.aptech.t2109e.jspservlet.entity.Employee;
import org.aptech.t2109e.jspservlet.utils.select.Pagination;

public interface EmployeeService {
    ResultService<Pagination<Employee>> filterAndPagination(String search, int page, int limit);
    ResultService<Employee> create(Employee employee);
}

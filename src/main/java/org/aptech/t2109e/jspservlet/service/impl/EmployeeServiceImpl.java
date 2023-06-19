package org.aptech.t2109e.jspservlet.service.impl;

import org.aptech.t2109e.jspservlet.common.ResultService;
import org.aptech.t2109e.jspservlet.common.WhereOperator;
import org.aptech.t2109e.jspservlet.entity.Employee;
import org.aptech.t2109e.jspservlet.service.EmployeeService;
import org.aptech.t2109e.jspservlet.repository.EmployeeRepositoryImpl;
import org.aptech.t2109e.jspservlet.utils.select.Pagination;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepositoryImpl employeeRepo = new EmployeeRepositoryImpl();
    
    public ResultService<Pagination<Employee>> filterAndPagination(String search, int page, int limit) {
        page = page <= 0 ? 1 : page;
        limit = limit <= 0 ? 20 : limit;

        Pagination<Employee> pagination = new Pagination<>();


        if(search != null && !search.equals("")){
            pagination.where("full_name", WhereOperator.LIKE , "%" +  search + "%");
        }

        pagination.of(page, limit);

        employeeRepo.filterAndPagination(pagination);

        return ResultService.OK(pagination);
    }

    @Override
    public ResultService<Employee> create(Employee employee) {
        employee = employeeRepo.create(employee);
        return ResultService.OK(employee);
    }
}

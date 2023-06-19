package org.aptech.t2109e.jspservlet.repository;

import org.aptech.t2109e.jspservlet.entity.Employee;
import org.aptech.t2109e.jspservlet.jpa.impl.JpaExecutorImpl;

public class EmployeeRepositoryImpl extends JpaExecutorImpl<Employee>
{
    public EmployeeRepositoryImpl() {
        super(Employee.class);
    }
}
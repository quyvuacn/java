package org.aptech.t2109e.jspservlet.database;

import org.aptech.t2109e.jspservlet.entity.Employee;
import org.aptech.t2109e.jspservlet.jpa.impl.JpaExecutorImpl;

public class SeedData {
    public static void main(String[] args) {
        JpaExecutorImpl<Employee> jpaExecutor = new JpaExecutorImpl<>(Employee.class);

        System.err.println(jpaExecutor.findAll()) ;

    }
}

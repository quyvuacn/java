package org.aptech.t2109e.jspservlet.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.aptech.t2109e.jspservlet.annotation.Column;
import org.aptech.t2109e.jspservlet.annotation.Entity;
import org.aptech.t2109e.jspservlet.annotation.Id;
import org.aptech.t2109e.jspservlet.common.SqlDataType;

import java.util.Date;

@Data
@Getter
@Setter
@Entity(tableName = "employee")
public class Employee{

    public Employee(String fullName, String birthday, String address, String position, String department) {
        this.fullName = fullName;
        this.birthday = birthday;
        this.address = address;
        this.position = position;
        this.department = department;
    }

    @Id(name = "id")
    private int id;

    @Column(name = "full_name", dataType = SqlDataType.TEXT)
    private String fullName;

    @Column(name = "birthday", dataType = SqlDataType.TEXT)
    private String birthday;

    @Column(name = "address", dataType = SqlDataType.TEXT)
    private String address;

    @Column(name = "position", dataType = SqlDataType.TEXT)
    private String position;

    @Column(name = "department", dataType = SqlDataType.TEXT)
    private String department;
}

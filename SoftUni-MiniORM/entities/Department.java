package app.entities;

import app.annotations.Column;
import app.annotations.Entity;
import app.annotations.Id;

@Entity(name = "departments")
public class Department {
    @Column(name = "id")
    @Id()
    private int id;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "company_name")
    private String company;

    @Column(name = "manager_id")
    private int managerID;

    @Column(name = "ucn")
    private String ucn;

}

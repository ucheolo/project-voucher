package com.example.project_voucher.storage.employee;

import com.example.project_voucher.storage.BaseEntity;
import jakarta.persistence.*;

@Table(name = "employee")
@Entity
public class EmployeeEntity extends BaseEntity {
    private String name;
    private String position;
    private String department;

    public EmployeeEntity() {
    }

    public EmployeeEntity(String name, String position, String department) {
        this.name = name;
        this.position = position;
        this.department = department;
    }

    public String getName() {
        return name;
    }
    public String getPosition() {
        return position;
    }
    public String getDepartment() {
        return department;
    }
}

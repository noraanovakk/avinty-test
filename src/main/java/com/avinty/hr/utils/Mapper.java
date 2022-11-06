package com.avinty.hr.utils;

import com.avinty.hr.dtos.EmployeeDTO;
import com.avinty.hr.entities.Employee;

public class Mapper {

    private Mapper() {
    }

    public static EmployeeDTO toEmployeeDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getFullName(),
                employee.getEmail());
    }
}

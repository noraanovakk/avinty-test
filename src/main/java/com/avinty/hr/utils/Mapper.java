package com.avinty.hr.utils;

import com.avinty.hr.dtos.DepartmentDTO;
import com.avinty.hr.dtos.EmployeeDTO;
import com.avinty.hr.entities.Department;
import com.avinty.hr.entities.Employee;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Mapper {

    private Mapper() {
    }

    public static EmployeeDTO toEmployeeDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getFullName(),
                employee.getEmail());
    }

    public static DepartmentDTO toDepartmentDTO(Department department) {
        return new DepartmentDTO(
                department.getId(),
                department.getName(),
                toEmployeeDTO(department.getManager()),
                toEmployeeDTOList(department.getEmployees())
        );
    }

    private static List<EmployeeDTO> toEmployeeDTOList(Iterable<Employee> employees) {
        return StreamSupport.stream(employees.spliterator(), false)
                .map(Mapper::toEmployeeDTO)
                .collect(Collectors.toList());
    }
}

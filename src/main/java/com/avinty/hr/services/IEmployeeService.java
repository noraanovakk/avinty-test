package com.avinty.hr.services;

import com.avinty.hr.entities.Department;
import com.avinty.hr.entities.Employee;

import java.util.Optional;

public interface IEmployeeService {

    Iterable<Employee> findAll();

    Optional<Employee> findById(Long id);

    void updateAllByDepartment(Department department);

    Employee save(Employee employee);
}

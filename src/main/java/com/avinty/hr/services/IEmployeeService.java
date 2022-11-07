package com.avinty.hr.services;

import com.avinty.hr.entities.Employee;

import java.util.Optional;

public interface IEmployeeService {

    Iterable<Employee> findAll();

    Optional<Employee> findById(Long id);
}

package com.avinty.hr.services;

import com.avinty.hr.entities.Employee;

import java.util.List;

public interface IEmployeeService {

    Iterable<Employee> findAll();
}

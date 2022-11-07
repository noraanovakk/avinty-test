package com.avinty.hr.services;

import com.avinty.hr.entities.Department;

import java.util.List;

public interface IDepartmentService {

    Iterable<Department> findAll();

    List<Department> findAllByName(String name);
}

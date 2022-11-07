package com.avinty.hr.services;

import com.avinty.hr.entities.Department;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {

    Iterable<Department> findAll();

    List<Department> findAllByName(String name);

    Optional<Department> findById(Long id);

    Department save(Department department);
}

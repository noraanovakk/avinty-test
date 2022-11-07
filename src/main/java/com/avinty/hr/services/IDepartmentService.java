package com.avinty.hr.services;

import com.avinty.hr.entities.Department;

public interface IDepartmentService {

    Iterable<Department> findAll();
}

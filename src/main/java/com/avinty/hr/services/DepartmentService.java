package com.avinty.hr.services;

import com.avinty.hr.entities.Department;
import com.avinty.hr.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService implements IDepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }
}

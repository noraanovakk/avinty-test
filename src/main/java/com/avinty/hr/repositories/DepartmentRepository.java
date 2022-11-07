package com.avinty.hr.repositories;

import com.avinty.hr.entities.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    List<Department> findAllByName(String name);
}

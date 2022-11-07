package com.avinty.hr.repositories;

import com.avinty.hr.entities.Department;
import com.avinty.hr.entities.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Modifying
    @Query("UPDATE Employee e SET e.department = NULL WHERE e.department = :department")
    void updateAllByDepartment(Department department);

    Optional<Employee> findByEmail(String email);
}

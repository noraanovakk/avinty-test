package com.avinty.hr.controllers;

import com.avinty.hr.dtos.DepartmentDTO;
import com.avinty.hr.dtos.EmployeeDTO;
import com.avinty.hr.entities.Department;
import com.avinty.hr.entities.Employee;
import com.avinty.hr.enums.Position;
import com.avinty.hr.services.IDepartmentService;
import com.avinty.hr.services.IEmployeeService;
import com.avinty.hr.utils.Mapper;
import com.avinty.hr.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@Slf4j
public class HrController implements HrAPI {

    private final IEmployeeService employeeService;
    private final IDepartmentService departmentService;

    @Autowired
    public HrController(IEmployeeService employeeService, IDepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @Override
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = StreamSupport.stream(employeeService.findAll().spliterator(), false)
                .map(Mapper::toEmployeeDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<List<DepartmentDTO>> getAllDepartmentsWithEmployees() {
        List<DepartmentDTO> departments = StreamSupport.stream(departmentService.findAll().spliterator(), false)
                .map(Mapper::toDepartmentDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(departments);
    }

    @Override
    public ResponseEntity<List<DepartmentDTO>> getDepartmentsByName(String name) {
        List<DepartmentDTO> departments = departmentService.findAllByName(name)
                .stream()
                .map(Mapper::toDepartmentDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(departments);
    }

    @Override
    public ResponseEntity<DepartmentDTO> updateManagerForDepartment(Long managerId, DepartmentDTO departmentDTO) {
        if(departmentDTO.getId() == null) {
            log.error("Department must have an id");
            return ResponseEntity.badRequest().build();
        }
        Optional<Department> department = departmentService.findById(departmentDTO.getId());
        if (department.isEmpty()) {
            log.error("Department with id {} not found", departmentDTO.getId());
            return ResponseEntity.notFound().build();
        }
        // TODO: make custom annotation to only accept employee with MANAGER position
        Optional<Employee> employee = employeeService.findById(managerId);
        if(employee.isEmpty()) {
            log.error("Employee with id {} not found", managerId);
            return ResponseEntity.notFound().build();
        }
        if(!Utils.isManager(employee.get())) {
            log.error("Employee is not a manager");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        //TODO: update employee's department + modified by + midified at

        Department departmentToUpdate = updateDepartment(department.get(), employee.get());
        return ResponseEntity.ok(Mapper.toDepartmentDTO(departmentService.save(departmentToUpdate)));
    }

    private Department updateDepartment(Department department, Employee employee) {
        department.setManager(employee);
        department.setModifiedAt(LocalDateTime.now());
        //TODO: update modified by
        return department;
    }
}

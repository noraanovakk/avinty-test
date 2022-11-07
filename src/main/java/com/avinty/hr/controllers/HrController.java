package com.avinty.hr.controllers;

import com.avinty.hr.dtos.DepartmentDTO;
import com.avinty.hr.dtos.EmployeeDTO;
import com.avinty.hr.entities.Department;
import com.avinty.hr.entities.Employee;
import com.avinty.hr.services.IDepartmentService;
import com.avinty.hr.services.IEmployeeService;
import com.avinty.hr.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
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
}

package com.avinty.hr.controllers;

import com.avinty.hr.dtos.DepartmentDTO;
import com.avinty.hr.dtos.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/API/V1/")
public interface HrAPI {

    @GetMapping("/employees")
    ResponseEntity<List<EmployeeDTO>> getAllEmployees();

    @GetMapping("/dep-emp")
    ResponseEntity<List<DepartmentDTO>> getAllDepartmentsWithEmployees();

    @GetMapping("/department")
    ResponseEntity<List<DepartmentDTO>> getDepartmentsByName(@RequestParam String name);
}

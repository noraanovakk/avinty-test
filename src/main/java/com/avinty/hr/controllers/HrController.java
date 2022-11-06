package com.avinty.hr.controllers;

import com.avinty.hr.dtos.EmployeeDTO;
import com.avinty.hr.entities.Employee;
import com.avinty.hr.services.IEmployeeService;
import com.avinty.hr.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class HrController implements HrAPI {

    private final IEmployeeService employeeService;

    @Autowired
    public HrController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = StreamSupport.stream(employeeService.findAll().spliterator(), false)
                .map(Mapper::toEmployeeDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(employees);
    }
}

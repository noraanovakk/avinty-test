package com.avinty.hr.controllers;

import com.avinty.hr.dtos.DepartmentDTO;
import com.avinty.hr.dtos.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/API/V1/")
@CrossOrigin(origins = "localhost:5000")
public interface HrAPI {

    @GetMapping("/employees")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    ResponseEntity<List<EmployeeDTO>> getAllEmployees();

    @GetMapping("/dep-emp")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    ResponseEntity<List<DepartmentDTO>> getAllDepartmentsWithEmployees();

    @GetMapping("/department")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    ResponseEntity<List<DepartmentDTO>> getDepartmentsByName(@RequestParam String name);

    @PatchMapping("/department/set-manager/{managerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<DepartmentDTO> updateManagerForDepartment(@PathVariable Long managerId,
                                                             @RequestBody DepartmentDTO departmentDTO);

    @DeleteMapping("/department/{departmentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<Void> deleteDepartment(@PathVariable Long departmentId);
}

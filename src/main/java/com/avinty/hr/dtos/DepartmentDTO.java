package com.avinty.hr.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class DepartmentDTO {

    private Long id;

    private String name;

    private EmployeeDTO manager;

    private List<EmployeeDTO> employees;
}

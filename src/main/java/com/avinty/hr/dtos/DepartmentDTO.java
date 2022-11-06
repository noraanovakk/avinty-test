package com.avinty.hr.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DepartmentDTO {

    private long id;

    private String name;

    private EmployeeDTO manager;
}

package com.avinty.hr.dtos;

import com.avinty.hr.entities.Department;
import com.avinty.hr.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class EmployeeDTO {

    private long id;

    private String fullName;

    private String email;

    private Position position;

    private Department department;
}

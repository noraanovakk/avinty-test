package com.avinty.hr.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class EmployeeDTO {

    private String fullName;

    private String email;
}

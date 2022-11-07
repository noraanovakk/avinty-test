package com.avinty.hr.controllers;

import com.avinty.hr.dtos.EmployeeDTO;
import com.avinty.hr.entities.Department;
import com.avinty.hr.entities.Employee;
import com.avinty.hr.services.DepartmentService;
import com.avinty.hr.services.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HrControllerTest {
    private Employee emp1;
    private Employee emp2;
    private EmployeeDTO empDTO1;
    private EmployeeDTO empDTO2;

    private Department department;

    @Mock
    EmployeeService employeeService;

    @Mock
    DepartmentService departmentService;

    HrController controller;

    @Before
    public void setup() {
        controller = new HrController(employeeService, departmentService);

        empDTO1 = new EmployeeDTO("Test Employee", "test@test.com");
        empDTO2 = new EmployeeDTO("Test Manager", "manager@test.com");
        emp1 = new Employee();
        emp1.setId(1L);
        emp1.setEmail("test@test.com");
        emp1.setFullName("Test Employee");

        emp2 = new Employee();
        emp2.setId(2L);
        emp2.setFullName("Test Manager");
        emp2.setEmail("manager@test.com");

        department = new Department();
        department.setId(1L);
        department.setName("testDep");
        department.setManager(emp2);
        department.setEmployees(Set.of(emp1, emp2));
    }

    @Test
    public void testGetAllEmployees() {
        when(employeeService.findAll()).thenReturn(List.of(emp1, emp2));

        var result = controller.getAllEmployees();

        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertNotNull(result.getBody());
        Assert.assertEquals(2, result.getBody().size());
    }
}

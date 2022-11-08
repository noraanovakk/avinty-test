package com.avinty.hr.controllers;

import com.avinty.hr.dtos.DepartmentDTO;
import com.avinty.hr.dtos.EmployeeDTO;
import com.avinty.hr.dtos.LoginDTO;
import com.avinty.hr.entities.Department;
import com.avinty.hr.entities.Employee;
import com.avinty.hr.enums.ERole;
import com.avinty.hr.enums.Position;
import com.avinty.hr.exceptions.NotManagerException;
import com.avinty.hr.security.JWTUtils;
import com.avinty.hr.security.UserDetailsImpl;
import com.avinty.hr.services.DepartmentService;
import com.avinty.hr.services.EmployeeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
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

    private DepartmentDTO departmentDTO;

    LoginDTO loginDTO;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JWTUtils jwtUtils;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;
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
        emp1.setPosition(Position.JAVA_DEVELOPER);

        emp2 = new Employee();
        emp2.setId(2L);
        emp2.setFullName("Test Manager");
        emp2.setEmail("manager@test.com");
        emp2.setPosition(Position.MANAGER);

        empDTO1 = new EmployeeDTO("Test Employee", "test@test.com");
        empDTO2 = new EmployeeDTO("Test Manager", "manager@test.com");

        department = new Department();
        department.setId(1L);
        department.setName("testDep");
        department.setEmployees(List.of(emp1, emp2));

        departmentDTO = new DepartmentDTO(1L, "testDep", empDTO2, List.of(empDTO1, empDTO2));
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

    @Test
    public void testGetAllDepartmentsWithEmployees() {
        department.setManager(emp2);
        when(departmentService.findAll()).thenReturn(List.of(department));

        var result = controller.getAllDepartmentsWithEmployees();

        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertNotNull(result.getBody());
        Assert.assertEquals(1, result.getBody().size());
        Assert.assertEquals(2, result.getBody().get(0).getEmployees().size());
    }

    @Test
    public void testUpdateManagerForDepartment_departmentIdNull() {
        departmentDTO.setId(null);

        var result = controller.updateManagerForDepartment(1L, departmentDTO);

        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testUpdateManagerForDepartment_departmentNotFound() {
        when(departmentService.findById(1L)).thenReturn(Optional.empty());

        var result = controller.updateManagerForDepartment(1L, departmentDTO);

        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testUpdateManagerForDepartment_managerNotFound() {
        when(departmentService.findById(1L)).thenReturn(Optional.of(department));
        when(employeeService.findById(1L)).thenReturn(Optional.empty());

        var result = controller.updateManagerForDepartment(1L, departmentDTO);

        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test(expected = NotManagerException.class)
    public void testUpdateManagerForDepartment_notManager() {
        when(departmentService.findById(1L)).thenReturn(Optional.of(department));
        when(employeeService.findById(1L)).thenReturn(Optional.of(emp1));

        var result = controller.updateManagerForDepartment(1L, departmentDTO);
    }

    @Test
    public void testUpdateManagerForDepartment_happyPath() {
        Department updatedDepartment = department;
        updatedDepartment.setManager(emp2);
        loginDTO = new LoginDTO("test@test.com", "pass123");

        UserDetailsImpl userDetails = new UserDetailsImpl(
                1, "test@test.com", "pass123", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())))
                .thenReturn(authentication);

        when(departmentService.findById(1L)).thenReturn(Optional.of(department));
        when(employeeService.findById(2L)).thenReturn(Optional.of(emp2));
        when(departmentService.save(department)).thenReturn(updatedDepartment);

        var result = controller.updateManagerForDepartment(2L, departmentDTO);

        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testDeleteDepartment_departmentNotFound() {
        when(departmentService.findById(1L)).thenReturn(Optional.empty());

        var result = controller.deleteDepartment(1L);

        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testDeleteDepartment_happyPath() {
        when(departmentService.findById(1L)).thenReturn(Optional.of(department));

        var result = controller.deleteDepartment(1L);

        Assert.assertNotNull(result);
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}

package com.hibernate.demo.controller;

import com.hibernate.demo.entity.Employee;
import com.hibernate.demo.exception.RecordNotFoundException;
import com.hibernate.demo.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "To get list of employees", description = "This API returns list of employees")
    @GetMapping
    public List<Employee> getListOfEmployees() {
        return employeeService.getAllEmployees();
    }

    @Operation(summary = "To create or update employee", description = "This API create and update employee")
    @PostMapping
    public Employee createOrUpdateEmployee(@RequestBody Employee employee) {
        return employeeService.createOrUpdate(employee);
    }

    @Operation(summary = "To delete employee by id", description = "This API used to delete employee by id")
    @DeleteMapping("/{id}")
    public HttpStatus deleteEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
        employeeService.deleteEmployeeById(id);
        return HttpStatus.OK;
    }

    @Operation(summary = "To get employee by id", description = "This API is used to retrieved record by Id")
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException {
        return employeeService.getEmployeeById(id);
    }
}

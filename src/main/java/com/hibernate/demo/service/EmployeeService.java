package com.hibernate.demo.service;

import com.hibernate.demo.entity.Employee;
import com.hibernate.demo.exception.RecordNotFoundException;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee createOrUpdate(Employee employee);

    void deleteEmployeeById(Long id) throws RecordNotFoundException;

    Employee getEmployeeById(Long id) throws RecordNotFoundException;

}

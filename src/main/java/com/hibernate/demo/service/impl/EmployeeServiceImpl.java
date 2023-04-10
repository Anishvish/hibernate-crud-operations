package com.hibernate.demo.service.impl;

import com.hibernate.demo.entity.Employee;
import com.hibernate.demo.exception.RecordNotFoundException;
import com.hibernate.demo.service.EmployeeService;
import com.hibernate.demo.util.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Employee> getAllEmployees() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee", Employee.class).list();
        } catch (HibernateException hibernateException) {
            log.error("Exception occurred ", hibernateException);
        }
        return new ArrayList<>();
    }

    @Override
    public Employee createOrUpdate(Employee employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee employeeData = session.get(Employee.class, employee.getEmpno());
        if (Objects.nonNull(employeeData)) {
            employeeData.setJob(employee.getJob());
            employeeData.setDeptno(employee.getDeptno());
            employeeData.setEname(employee.getEname());
            employeeData.setSal(employee.getSal());
            session.save(employeeData);
            return employeeData;
        } else {
            session.save(employee);
            return session.get(Employee.class, employee.getEmpno());
        }
    }

    @Override
    public void deleteEmployeeById(Long id) throws RecordNotFoundException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee employee = session.get(Employee.class, id);
        Transaction t = session.beginTransaction();
        if (Objects.nonNull(employee)) {
            session.delete(employee);
            t.commit();
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }

    @Override
    public Employee getEmployeeById(Long id) throws RecordNotFoundException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee employee = session.get(Employee.class, id);
        if (Objects.isNull(employee)) {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
        return employee;
    }
}

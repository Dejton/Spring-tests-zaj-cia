package com.spring_boot_testing_142.service;

import com.spring_boot_testing_142.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee save(Employee employee);
    List<Employee> findAll();
    Optional<Employee> findById(long id);
    Employee updateEmployee(Employee updatedEmployee);
    void deleteById(long id);
}

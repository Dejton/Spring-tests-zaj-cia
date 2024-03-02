package com.spring_boot_testing_142.service.impl;

import com.spring_boot_testing_142.entity.Employee;
import com.spring_boot_testing_142.exceptions.EmployeeAlreadyExists;
import com.spring_boot_testing_142.repository.EmployeeRepository;
import com.spring_boot_testing_142.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if(savedEmployee.isPresent())
            throw new EmployeeAlreadyExists("Employee already exists with given email: " + employee.getEmail());
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return null;
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return Optional.empty();
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee) {
        return null;
    }

    @Override
    public void deleteEmployee(long id) {

    }
}

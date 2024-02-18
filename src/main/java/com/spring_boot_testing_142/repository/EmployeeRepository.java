package com.spring_boot_testing_142.repository;

import com.spring_boot_testing_142.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

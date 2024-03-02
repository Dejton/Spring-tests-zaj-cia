package com.spring_boot_testing_142.repository;

import com.spring_boot_testing_142.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // JPQL - Java Persistence Query Language
    Optional<Employee> findByEmail(String email); // JPQL -  na podstawie słów kluczowych tworzone jest zapytanie, wynika to z 'Spring Data JPA'

    Optional<Employee> findByFirstNameAndLastName(String firstName, String lastName);
    // To samo co powyżej za pomocą JPQL
    Optional<Employee> findByJPQL(String firstName, String lastName);
}

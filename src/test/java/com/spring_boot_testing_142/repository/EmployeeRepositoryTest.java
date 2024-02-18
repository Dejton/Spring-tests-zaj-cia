package com.spring_boot_testing_142.repository;

import com.spring_boot_testing_142.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // używa domyślnie in-memory database, testy są wykonywane w ramach transakcji, jest robiony Roll-Back na koniec testu
//@SpringBootTest
class EmployeeRepositoryTest {
    // TDD - test driven development - najpierw piszemy test a potem metody
    // BDD - behavior driven development- dzielenie na sekcje:
    // given - aktualne wartości, obiekty
    // when - akcja lub testowane zachowanie
    // then - weryfikacja wyniku
    @Autowired
    private EmployeeRepository employeeRepository; // w testach wstrzykujemy przez pole

    private Employee employee;
    @BeforeEach
    void setUp(){
        employee = Employee.builder()
                .firstName("Adam")
                .lastName("Małysz")
                .email("adam@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Zapisywanie pracownika")
    void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        // given
        // when
        Employee savedEmployee = employeeRepository.save(employee);

        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
        assertThat(savedEmployee).isEqualTo(employee);
    }

    @DisplayName("pobieranie wszystkich pracowników")
    @Test
    void givenEmployeeList_whenFindAll_thenReturnEmployeeList(){
        // given
        Employee employee2 = Employee.builder()
                .firstName("Adam")
                .lastName("Małysz")
                .email("adam@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        // when
        List<Employee> employeeList = employeeRepository.findAll();

        // then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }
    // pobieranie pracowników po id
    // pobieranie pracowników po email
    // uaktualnianie danych pracowników
    // usuwanie pracownika
    // testy dla zapytań JPQL
    // testy dla zapytań z indeksowanymi parametrami
    // testy dla zapytań z nazwami parametrów
}
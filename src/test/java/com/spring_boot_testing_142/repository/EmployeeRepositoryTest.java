package com.spring_boot_testing_142.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // używa domyślnie in-memory database, testy są wykonywane w ramach transakcji, jest robiony Roll-Back na koniec testu
class EmployeeRepositoryTest {
    // TDD - test driven development - najpierw piszemy test a potem metody
    // BDD - behavior driven development- dzielenie na sekcje:
    // given - aktualne wartości, obiekty
    // when - akcja lub testowane zachowanie
    // then - weryfikacja wyniku

    // pobieranie wszystkich pracowników
    // pobieranie pracowników po id
    // pobieranie pracowników po email
    // uaktualnianie danych pracowników
    // usuwanie pracownika
    // testy dla zapytań JPQL
    // testy dla zapytań z indeksowanymi parametrami
    // testy dla zapytań z nazwami parametrów
}
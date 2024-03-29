package com.spring_boot_testing_142.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_boot_testing_142.entity.Employee;
import com.spring_boot_testing_142.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
//    testy tworzenia pracownika
//    testy pobierania pracowników
//    testy pobierania pracownika po id (pozytywny i negatywny scenariusz)
//    testy uaktualniania pracownika (pozytywny i negatywny scenariusz)
//    testy usuwania pracownika

    @Autowired
    private MockMvc mockMvc;  // niezbędne do tesrowania mVC- aby można było wywoływać endpointy

    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper; // do deserializacji JSON na obiekt oraz serializacji

    @Test
    @DisplayName("test tworzenia pracownika")
    void givenEmployee_whenCreatedEmployee_thenReturnSavedEmployee() throws Exception {
//        given
        Employee employee = Employee.builder()
                .firstName("Adam")
                .lastName("Małysz")
                .email("adam@gmail.com")
                .build();
        given(employeeService.save(any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));
//        when
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

//        then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    @DisplayName("test pobierania pracowników")
    @Test
    void givenListOfEmployees_whenGetAll_thenReturnEmployeesList() throws Exception {
//        given
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Adam").lastName("Małysz").email("adam@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Karol").lastName("Nowal").email("karol@gmail.com").build());

        given(employeeService.findAll()).willReturn(listOfEmployees);
//        when
        ResultActions response = mockMvc.perform(get("/api/employees"));
//        then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
    }

    @DisplayName("test pobierania pracownika po id (pozytywny scenariusz)")
    @Test
    void givenEmployeeId_whenFindById_thenReturnEmployee() throws Exception {
//        given

        Employee employee = Employee.builder().id(1L).firstName("Adam").lastName("Małysz").email("adam@gmail.com").build();
        long employeeId = employee.getId();
        given(employeeService.findById(employeeId)).willReturn(Optional.of(employee));
//        when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));
//        then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())))
                .andDo(print());
    }

    @DisplayName("test pobierania pracownika po id (negatywny scenariusz)")
    @Test
    void givenInvalidEmployeeId_whenFindById_thenReturnNotFound() throws Exception {
//        given
        given(employeeService.findById(anyLong())).willReturn(Optional.empty());
//        when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", 1L));
//        then
        response.andExpect(status().isNotFound()).andDo(print());
    }

    @DisplayName("test ukatualniania pracownika (pozytywny scenariusz")
    @Test
    void givenEmployeeId_whenUpdated_thenReturnEmployee() throws Exception {
//        given
        Employee employee = Employee.builder().id(1L).firstName("Adam").lastName("Małysz").email("adam@gmail.com").build();
        given(employeeService.updateEmployee(eq(employee.getId()), any(Employee.class))).willReturn(Optional.of(employee));
//        when
        ResultActions response  = mockMvc.perform(put("/api/employees/{id}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

//        then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())))
                .andDo(print());
    }

    @DisplayName("test ukatualniania pracownika (negatywny scenariusz")
    @Test
    void givenInvalidEmployeeId_whenUpdated_thenReturnEmployee() throws Exception {
//        given
        Employee employee = Employee.builder().id(1L).firstName("Adam").lastName("Małysz").email("adam@gmail.com").build();
        given(employeeService.updateEmployee(eq(999L), any(Employee.class))).willReturn(Optional.empty());
//        when
        ResultActions response  = mockMvc.perform(put("/api/employees/{id}", employee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
        );

//        then
        response.andExpect(status().isNotFound());

    }

    @DisplayName("test usuwania pracownika")
    @Test
    void givenEmployeeId_whenDeleted_thenReturn200() throws Exception {
//        given
        long employeeId = 1L;
        willDoNothing().given(employeeService).deleteById(employeeId);

//        when
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employeeId));
//        then
        response.andExpect(status().isOk())
                .andDo(print());
    }
}





















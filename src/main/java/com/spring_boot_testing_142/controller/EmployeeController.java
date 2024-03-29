package com.spring_boot_testing_142.controller;

import com.spring_boot_testing_142.entity.Employee;
import com.spring_boot_testing_142.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {//TODO try to separate DB entities and models returned from API - DTO objects should be used
    private final EmployeeService  employeeService;

    //TODO add validations to your model like @NotEmpty etc.
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);//TODO this throws an exception, error handler is missing
    }
    @GetMapping
    public List<Employee> getEmployees() {
        return employeeService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long employeeId) {
        employeeService.deleteById(employeeId);
        return new ResponseEntity<>("Employee deleted successfull", HttpStatus.OK);
    }

}

package com.bogdan.crudddemo.rest;

import com.bogdan.crudddemo.entity.Employee;
import com.bogdan.crudddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

   private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    private List<Employee> findAll() {

        return employeeService.findAll();
    }

    //add mapping for GET /employees/{employeeId}
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {

        Employee employee = employeeService.findById(employeeId);

        if(employee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        return employee;
    }

    //add mapping for POST /employee -  add a new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {

        //also just in case they pass an id in JSON set it to 0
        //this is to force a save to a new item instead of update

        employee.setId(0);

        employeeService.save(employee);

        return employee;

    }

    //add mapping for PUT /employee - update existing employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {

        employeeService.save(employee);

        return employee;

    }

    //delete mapping for /employee/{employeeId} - delete employee
    @DeleteMapping("employees/{employeeId}")
    public String deleteEmployeeById(@PathVariable int employeeId) {

        Employee tempEmployee = employeeService.findById(employeeId);

        //throw exception if null
        if(tempEmployee == null) {
            throw  new RuntimeException("Employee does not exit - " + employeeId);
        }

        employeeService.deleteById(employeeId);

        return "Deleted employee id " + employeeId;

    }



}

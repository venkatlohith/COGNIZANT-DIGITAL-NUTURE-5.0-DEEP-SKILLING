package com.cognizant.springlearn.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.Employee;
import com.cognizant.springlearn.service.EmployeeService;
import com.cognizant.springlearn.service.exception.EmployeeNotFoundException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    // GET /employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        LOGGER.info("START");
        List<Employee> employees = employeeService.getAllEmployees();
        LOGGER.debug("Employees: {}", employees);
        LOGGER.info("END");
        return employees;
    }

    // PUT /employees
    // @Valid triggers validation based on annotations in Employee, Department, Skill
    // @RequestBody maps JSON payload to Employee bean
    // Postman: PUT http://localhost:8083/employees
    // Body (raw JSON):
    // {
    //   "id": 1, "name": "John Updated", "salary": 55000.0, "permanent": true,
    //   "dateOfBirth": "01/01/1990",
    //   "department": { "id": 1, "name": "Engineering" },
    //   "skills": [{ "id": 1, "name": "Java" }]
    // }
    @PutMapping
    public void updateEmployee(@RequestBody @Valid Employee employee)
            throws EmployeeNotFoundException {
        LOGGER.info("START");
        LOGGER.debug("Employee : {}", employee);
        employeeService.updateEmployee(employee);
        LOGGER.info("END");
    }
}

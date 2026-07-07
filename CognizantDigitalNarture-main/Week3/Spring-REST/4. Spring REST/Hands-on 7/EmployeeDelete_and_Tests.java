// ===== EmployeeDao.java - deleteEmployee() =====

package com.cognizant.springlearn.dao;

// Add deleteEmployee() to the existing EmployeeDao class

public void deleteEmployee(int id) throws EmployeeNotFoundException {
    LOGGER.info("START");

    Employee toRemove = null;
    for (Employee emp : EMPLOYEE_LIST) {
        if (emp.getId() == id) {
            toRemove = emp;
            break;
        }
    }

    if (toRemove == null) {
        throw new EmployeeNotFoundException("Employee not found for id: " + id);
    }

    EMPLOYEE_LIST.remove(toRemove);

    LOGGER.info("END");
}


// ===== EmployeeService.java - deleteEmployee() =====

package com.cognizant.springlearn.service;

// Add deleteEmployee() to the existing EmployeeService class

public void deleteEmployee(int id) throws EmployeeNotFoundException {
    LOGGER.info("START");
    employeeDao.deleteEmployee(id);
    LOGGER.info("END");
}


// ===== EmployeeController.java - DELETE /employees/{id} =====

package com.cognizant.springlearn.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Add deleteEmployee() to the existing EmployeeController class

// DELETE /employees/{id}
// Deletes employee by id
// Postman: DELETE http://localhost:8083/employees/1
// Response: 200 OK (empty body)
// Error: 404 Not Found if id does not exist
@DeleteMapping("/{id}")
public void deleteEmployee(@PathVariable int id) throws EmployeeNotFoundException {
    LOGGER.info("START");
    employeeService.deleteEmployee(id);
    LOGGER.info("END");
}


// ===== SpringLearnApplicationTests.java - MockMvc test for DELETE exception =====

package com.cognizant.springlearn;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringLearnApplicationTests {

    @Autowired
    private MockMvc mvc;

    // Test: Delete employee with an id that does not exist -> expect 404
    @Test
    public void testDeleteEmployeeException() throws Exception {
        // Use an id that doesn't exist in employee.xml (e.g. 999)
        ResultActions actions = mvc.perform(delete("/employees/999"));

        // Expect 404 Not Found
        actions.andExpect(status().isNotFound());

        // Expect reason "Employee not found"
        actions.andExpect(status().reason("Employee not found"));
    }

    // Test: Delete employee with valid id -> expect 200 OK
    @Test
    public void testDeleteEmployee() throws Exception {
        ResultActions actions = mvc.perform(delete("/employees/1"));
        actions.andExpect(status().isOk());
    }
}

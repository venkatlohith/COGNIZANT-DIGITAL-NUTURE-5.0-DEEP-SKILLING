package com.cognizant.ormlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.ormlearn.model.Department;
import com.cognizant.ormlearn.service.DepartmentService;

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static DepartmentService departmentService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        departmentService = context.getBean(DepartmentService.class);

        testGetDepartment();
    }

    // OneToMany: Get department with all its employees
    // NOTE: Without FetchType.EAGER this will throw LazyInitializationException.
    // Fix: add fetch = FetchType.EAGER to @OneToMany in Department.java
    private static void testGetDepartment() {
        LOGGER.info("Start");

        // Use a department id that has more than one employee
        Department department = departmentService.get(1);
        LOGGER.debug("Department:{}", department);
        LOGGER.debug("Employees:{}", department.getEmployeeList());

        LOGGER.info("End");
    }
}

package com.cognizant.ormlearn;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.model.Skill;
import com.cognizant.ormlearn.service.EmployeeService;
import com.cognizant.ormlearn.service.SkillService;

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static EmployeeService employeeService;
    private static SkillService skillService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);

        employeeService = context.getBean(EmployeeService.class);
        skillService    = context.getBean(SkillService.class);

        testGetEmployee();
        // testAddSkillToEmployee();
    }

    // Get employee with department and skills (ManyToMany - set FetchType.EAGER)
    private static void testGetEmployee() {
        LOGGER.info("Start");

        Employee employee = employeeService.get(1);
        LOGGER.debug("Employee:{}", employee);
        LOGGER.debug("Department:{}", employee.getDepartment());
        LOGGER.debug("Skills:{}", employee.getSkillList());

        LOGGER.info("End");
    }

    // Add a skill to an employee (ManyToMany insert into employee_skill table)
    private static void testAddSkillToEmployee() {
        LOGGER.info("Start");

        // Use an employee id and skill id that don't already have a relationship
        Employee employee = employeeService.get(1);
        Skill skill = skillService.get(3);

        Set<Skill> skillList = employee.getSkillList();
        skillList.add(skill);

        employeeService.save(employee);

        LOGGER.info("End");
    }
}

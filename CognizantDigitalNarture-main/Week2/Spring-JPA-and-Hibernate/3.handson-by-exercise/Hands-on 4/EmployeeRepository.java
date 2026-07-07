package com.cognizant.ormlearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Step 1: Get average salary across all employees
    // @Query(value = "SELECT AVG(e.salary) FROM Employee e")
    // double getAverageSalary();

    // Step 2: Get average salary for a specific department
    // NOTE:
    //   - department.id is accessed via 'e.department.id' (HQL uses Java field names)
    //   - ':id' is a named parameter bound using @Param
    //   - Similar aggregate functions: SUM(), MIN(), MAX(), COUNT() can also be used
    @Query(value = "SELECT AVG(e.salary) FROM Employee e where e.department.id = :id")
    double getAverageSalary(@Param("id") int id);
}

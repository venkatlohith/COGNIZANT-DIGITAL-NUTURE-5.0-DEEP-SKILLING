package com.cognizant.ormlearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Native Query: uses actual SQL (table/column names), not HQL (class/field names)
    // nativeQuery = true tells Spring Data JPA to send the query directly to the DB
    // NOTE: Avoid native queries where possible - they reduce DB portability
    @Query(value = "SELECT * FROM employee", nativeQuery = true)
    List<Employee> getAllEmployeesNative();
}

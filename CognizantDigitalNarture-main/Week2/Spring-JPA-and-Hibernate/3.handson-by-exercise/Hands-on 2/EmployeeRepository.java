package com.cognizant.ormlearn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Step 1: Basic HQL - get all permanent employees
    // NOTE: HQL uses Java class name 'Employee' and field name 'permanent', not table/column names
    // @Query(value = "SELECT e FROM Employee e WHERE e.permanent = 1")
    // List<Employee> getAllPermanentEmployees();

    // Step 2: HQL with join (no fetch) - still needs EAGER fetch on beans to load relations
    // @Query(value = "SELECT e FROM Employee e left join e.department d left join e.skillList WHERE e.permanent = 1")
    // List<Employee> getAllPermanentEmployees();

    // Step 3: OPTIMIZED HQL using 'fetch' - single query, populates department and skillList
    // 'join' links the table; 'fetch' ensures the associated bean is also populated
    @Query(value = "SELECT e FROM Employee e left join fetch e.department d left join fetch e.skillList WHERE e.permanent = 1")
    List<Employee> getAllPermanentEmployees();
}

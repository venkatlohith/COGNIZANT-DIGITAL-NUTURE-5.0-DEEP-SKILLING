package com.example.EmployeeManagementSystem.repository;

import com.example.EmployeeManagementSystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Derived query method using method name keywords
    List<Employee> findByNameContaining(String keyword);

    List<Employee> findByDepartmentName(String departmentName);

    // Custom query using @Query annotation (JPQL)
    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    Employee findByEmailQuery(@Param("email") String email);

    // Custom query: find all employees in a department by department id
    @Query("SELECT e FROM Employee e WHERE e.department.id = :deptId")
    List<Employee> findByDepartmentId(@Param("deptId") Long deptId);

    // Named Query (defined on the Entity using @NamedQuery)
    List<Employee> findByNamedQueryByName(@Param("name") String name);
}

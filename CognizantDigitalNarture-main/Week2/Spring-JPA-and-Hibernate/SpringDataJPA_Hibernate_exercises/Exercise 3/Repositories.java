// ===== EmployeeRepository.java =====
package com.example.EmployeeManagementSystem.repository;

import com.example.EmployeeManagementSystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Derived query: find employees by name
    List<Employee> findByName(String name);

    // Derived query: find employees by department name
    List<Employee> findByDepartmentName(String departmentName);

    // Derived query: find employee by email
    Employee findByEmail(String email);
}


// ===== DepartmentRepository.java =====
package com.example.EmployeeManagementSystem.repository;

import com.example.EmployeeManagementSystem.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Derived query: find department by name
    Department findByName(String name);
}

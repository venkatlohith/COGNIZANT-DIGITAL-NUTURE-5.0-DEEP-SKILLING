// ===== application.properties (Hibernate dialect and batch processing) =====

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

# Batch processing: group INSERT/UPDATE statements into batches of 50
spring.jpa.properties.hibernate.jdbc.batch_size=50
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true
spring.jpa.properties.hibernate.jdbc.batch_versioned_data=true


// ===== Employee.java (Hibernate-specific annotations) =====

package com.example.EmployeeManagementSystem.entity;

import lombok.Data;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@Table(name = "employee")
@DynamicUpdate   // Hibernate: only update columns that have changed (not all columns)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)  // Second-level cache
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}


// ===== BatchInsertService.java (Batch processing with Hibernate) =====

package com.example.EmployeeManagementSystem.service;

import com.example.EmployeeManagementSystem.entity.Employee;
import com.example.EmployeeManagementSystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchInsertService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final int BATCH_SIZE = 50;

    // Bulk insert employees in batches
    @Transactional
    public void batchInsertEmployees(List<Employee> employees) {
        List<Employee> batch = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            batch.add(employees.get(i));

            if (batch.size() == BATCH_SIZE) {
                employeeRepository.saveAll(batch);
                batch.clear();
            }
        }

        // Save remaining records
        if (!batch.isEmpty()) {
            employeeRepository.saveAll(batch);
        }
    }
}

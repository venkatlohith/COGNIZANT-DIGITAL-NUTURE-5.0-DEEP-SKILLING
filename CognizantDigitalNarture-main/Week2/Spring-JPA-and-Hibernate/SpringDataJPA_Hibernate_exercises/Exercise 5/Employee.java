package com.example.EmployeeManagementSystem.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "employee")
// Named queries are defined at class level
@NamedQueries({
    @NamedQuery(
        name = "Employee.findByNamedQueryByName",
        query = "SELECT e FROM Employee e WHERE e.name = :name"
    )
})
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

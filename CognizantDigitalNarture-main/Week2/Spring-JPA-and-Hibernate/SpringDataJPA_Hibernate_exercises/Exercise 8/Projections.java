// ===== EmployeeNameProjection.java (Interface-based projection) =====
// Fetches only id and name - no need to load the entire Employee entity

package com.example.EmployeeManagementSystem.projection;

public interface EmployeeNameProjection {
    Long getId();
    String getName();
}


// ===== EmployeeEmailProjection.java (Interface-based with @Value expression) =====

package com.example.EmployeeManagementSystem.projection;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeEmailProjection {
    Long getId();
    String getEmail();

    // SpEL expression to combine fields
    @Value("#{target.name + ' (' + target.email + ')'}")
    String getNameWithEmail();
}


// ===== EmployeeSummaryDTO.java (Class-based projection) =====

package com.example.EmployeeManagementSystem.projection;

public class EmployeeSummaryDTO {

    private Long id;
    private String name;
    private String departmentName;

    // Constructor expression used in JPQL query
    public EmployeeSummaryDTO(Long id, String name, String departmentName) {
        this.id = id;
        this.name = name;
        this.departmentName = departmentName;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDepartmentName() { return departmentName; }
}


// ===== EmployeeRepository.java (projection queries) =====

package com.example.EmployeeManagementSystem.repository;

import com.example.EmployeeManagementSystem.entity.Employee;
import com.example.EmployeeManagementSystem.projection.EmployeeEmailProjection;
import com.example.EmployeeManagementSystem.projection.EmployeeNameProjection;
import com.example.EmployeeManagementSystem.projection.EmployeeSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Interface-based projection
    List<EmployeeNameProjection> findAllProjectedBy();

    // Interface-based projection with @Value
    List<EmployeeEmailProjection> findAllEmailsBy();

    // Class-based projection using constructor expression in JPQL
    @Query("SELECT new com.example.EmployeeManagementSystem.projection.EmployeeSummaryDTO" +
           "(e.id, e.name, e.department.name) FROM Employee e")
    List<EmployeeSummaryDTO> findAllEmployeeSummaries();
}

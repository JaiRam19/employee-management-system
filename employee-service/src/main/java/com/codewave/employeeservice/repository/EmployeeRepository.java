package com.codewave.employeeservice.repository;

import com.codewave.employeeservice.entity.Employee;
import com.codewave.employeeservice.repository.projections.DepartmentHeadcount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    @Query("SELECT e.departmentCode AS departmentCode, COUNT(e) AS count FROM Employee e WHERE e.employmentStatus = 'ACTIVE' AND e.departmentCode IN :departmentCodes GROUP BY e.departmentCode")
    List<DepartmentHeadcount> countByDepartmentCodeIn(@Param("departmentCodes") List<String> departmentCodes);
}

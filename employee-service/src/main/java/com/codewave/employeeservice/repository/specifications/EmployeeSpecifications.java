package com.codewave.employeeservice.repository.specifications;

import com.codewave.employeeservice.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class EmployeeSpecifications {
    public static Specification<Employee> hasDepartmentCode(String deptCode) {
        return (root, query, cb) -> cb.equal(root.get("departmentCode"), deptCode);
    }

    public static Specification<Employee> isOnBench() {
        return (root, query, cb) -> cb.equal(root.get("employmentStatus"), "ON_BENCH");
    }

    public static Specification<Employee> hasStatusIn(List<String> statuses) {
        return (root, query, cb) -> root.get("employmentStatus").in(statuses);
    }

    public static Specification<Employee> experienceBetween(double min, double max) {
        return (root, query, cb) -> cb.between(root.get("experienceYears"), min, max);
    }

    public static Specification<Employee> hasDesignationIn(List<String> designations) {
        return (root, query, cb) -> root.get("designation").in(designations);
    }
}

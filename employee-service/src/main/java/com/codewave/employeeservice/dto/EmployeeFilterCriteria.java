package com.codewave.employeeservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeFilterCriteria {
    private String departmentCode;
    private List<String> employmentStatuses;  // e.g. ["ON_BENCH", "ACTIVE"]
    private Double minExperience;
    private Double maxExperience;
    private List<String> designations;
}

package com.codewave.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    private Long departmentId;
    private String departmentName;
    private String departmentDescription;
    private String departmentCode;
    private Long departmentHeadId;
    private Integer capacity;
    private String location;
}

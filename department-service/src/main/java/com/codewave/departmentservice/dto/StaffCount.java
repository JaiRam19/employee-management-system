package com.codewave.departmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StaffCount {
    private Long activeEmployeeCount;
    private DepartmentDto departmentDto;
}

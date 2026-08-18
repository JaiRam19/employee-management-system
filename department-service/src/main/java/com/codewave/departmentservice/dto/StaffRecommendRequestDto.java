package com.codewave.departmentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffRecommendRequestDto {
    private DepartmentInfoDto departmentInfoDto;
    private EmployeeFilterCriteria filterCriteria;
}

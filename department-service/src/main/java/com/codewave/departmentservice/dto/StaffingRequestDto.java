package com.codewave.departmentservice.dto;

import java.util.List;

public record StaffingRequestDto(
        DepartmentInfoDto overstaffedDept,
        List<EmployeeCandidateDto> candidateEmployees,
        List<DepartmentInfoDto> understaffedDepartments
) {}

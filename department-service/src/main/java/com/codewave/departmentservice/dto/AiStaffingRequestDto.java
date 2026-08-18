package com.codewave.departmentservice.dto;

import java.util.List;

public record AiStaffingRequestDto(
        DepartmentInfoDto overstaffedDept,
        List<EmployeeCandidateDto> candidateEmployees,
        List<DepartmentInfoDto> understaffedDepartments
) {}

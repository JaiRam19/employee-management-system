package com.codewave.departmentservice.service;

import com.codewave.departmentservice.dto.*;

import java.util.List;

public interface DepartmentService {
    DepartmentDto saveDepartment(DepartmentDto departmentDto);
    DepartmentDto getDepartmentByCode(String departmentCode);
    List<DepartmentDto> getAllDepartments();
    List<StaffCount> getOverStaffedDepartments();
    List<StaffCount> getUnderStaffedDepartments();
    AiStaffingResponseDto getAiStaffRecommendations(StaffRecommendRequestDto filterCriteria);
}
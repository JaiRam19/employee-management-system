package com.codewave.departmentservice.service;

import com.codewave.departmentservice.dto.DepartmentDto;
import com.codewave.departmentservice.dto.StaffCount;

import java.util.List;

public interface DepartmentService {
    DepartmentDto saveDepartment(DepartmentDto departmentDto);
    DepartmentDto getDepartmentByCode(String departmentCode);
    List<DepartmentDto> getAllDepartments();
    List<StaffCount> getOverStaffedDepartments();
    List<StaffCount> getUnderStaffedDepartments();

}
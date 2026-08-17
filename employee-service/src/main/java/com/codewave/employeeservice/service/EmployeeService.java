package com.codewave.employeeservice.service;

import com.codewave.employeeservice.dto.APIResponseDto;
import com.codewave.employeeservice.dto.EmployeeCandidateDto;
import com.codewave.employeeservice.dto.EmployeeDto;
import com.codewave.employeeservice.dto.EmployeeFilterCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EmployeeService {
    EmployeeDto addEmployee(EmployeeDto employeeDto);
    APIResponseDto getEmployeeById(Long employeeId);
    Map<String, Long> getEmployeeCountByDepartment(List<String> departmentCodes);
    List<EmployeeCandidateDto> getMovementCandidates(EmployeeFilterCriteria criteria);
}

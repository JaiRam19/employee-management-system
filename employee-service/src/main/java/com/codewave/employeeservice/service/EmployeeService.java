package com.codewave.employeeservice.service;

import com.codewave.employeeservice.dto.APIResponseDto;
import com.codewave.employeeservice.dto.EmployeeDto;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    EmployeeDto addEmployee(EmployeeDto employeeDto);
    APIResponseDto getEmployeeById(Long employeeId);

}

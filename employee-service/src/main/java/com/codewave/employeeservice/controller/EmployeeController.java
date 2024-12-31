package com.codewave.employeeservice.controller;

import com.codewave.employeeservice.dto.APIResponseDto;
import com.codewave.employeeservice.dto.EmployeeDto;
import com.codewave.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    //add employee
    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeService.addEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    //get employee by id
    @GetMapping("{employee-id}")
    public ResponseEntity<APIResponseDto> getEmployeeById(@PathVariable("employee-id") Long employeeId){
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }
}

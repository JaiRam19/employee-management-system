package com.codewave.employeeservice.controller;

import com.codewave.employeeservice.dto.APIResponseDto;
import com.codewave.employeeservice.dto.EmployeeCandidateDto;
import com.codewave.employeeservice.dto.EmployeeDto;
import com.codewave.employeeservice.dto.EmployeeFilterCriteria;
import com.codewave.employeeservice.service.AIService;
import com.codewave.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    private AIService aiService;
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

    @GetMapping("ai/summary/{employee-id}")
    public ResponseEntity<String> generateSummary(@PathVariable("employee-id") Long employeeId) {
        String summary = aiService.generateSummary(employeeId);
        return ResponseEntity.ok(summary);
    }

    @PostMapping("/count-by-department")
    public ResponseEntity<Map<String, Long>> getEmployeeCountByDepartment(@RequestBody List<String> departmentCodes) {
        Map<String, Long> employeeCountMap = employeeService.getEmployeeCountByDepartment(departmentCodes);
        return ResponseEntity.ok(employeeCountMap);
    }

    @PostMapping("/candidates")
    public ResponseEntity<List<EmployeeCandidateDto>> getMovementCandidates(@RequestBody EmployeeFilterCriteria criteria){
        List<EmployeeCandidateDto> movementCandidates = employeeService.getMovementCandidates(criteria);
        return ResponseEntity.ok(movementCandidates);
    }
}

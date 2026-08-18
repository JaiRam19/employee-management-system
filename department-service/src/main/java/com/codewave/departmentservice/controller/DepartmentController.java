package com.codewave.departmentservice.controller;

import com.codewave.departmentservice.dto.AiStaffingResponseDto;
import com.codewave.departmentservice.dto.DepartmentDto;
import com.codewave.departmentservice.dto.StaffCount;
import com.codewave.departmentservice.dto.StaffRecommendRequestDto;
import com.codewave.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/departments")
public class DepartmentController {

    private DepartmentService departmentService;

    //save department REST API
    @PostMapping()
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto){
        DepartmentDto savedDepartment = departmentService.saveDepartment(departmentDto);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    //get department by department code
    @GetMapping("{department-code}")
    public ResponseEntity<DepartmentDto> getDepartmentByCode(@PathVariable("department-code") String departmentCode){
        return ResponseEntity.ok(departmentService.getDepartmentByCode(departmentCode));
    }

    @GetMapping()
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/over-staffed")
    public ResponseEntity<List<StaffCount>> getOverStaffedDepartments() {
        List<StaffCount> overStaffedDepartments = departmentService.getOverStaffedDepartments();
        return ResponseEntity.ok(overStaffedDepartments);
    }

    @GetMapping("/under-staffed")
    public ResponseEntity<List<StaffCount>> getUnderStaffedDepartments() {
        List<StaffCount> underStaffedDepartments = departmentService.getUnderStaffedDepartments();
        return ResponseEntity.ok(underStaffedDepartments);
    }

    @PostMapping("/ai/staff-recommend")
    public ResponseEntity<AiStaffingResponseDto> getAllAiStaffing(@RequestBody StaffRecommendRequestDto staffRecommendRequestDto) {
        AiStaffingResponseDto aiStaffRecommendations = departmentService.getAiStaffRecommendations(staffRecommendRequestDto);
        return ResponseEntity.ok(aiStaffRecommendations);
    }
}

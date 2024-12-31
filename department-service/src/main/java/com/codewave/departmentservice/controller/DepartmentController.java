package com.codewave.departmentservice.controller;

import com.codewave.departmentservice.dto.DepartmentDto;
import com.codewave.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

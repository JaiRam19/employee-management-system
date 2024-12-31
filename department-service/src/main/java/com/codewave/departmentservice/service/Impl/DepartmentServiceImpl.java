package com.codewave.departmentservice.service.Impl;

import com.codewave.departmentservice.dto.DepartmentDto;
import com.codewave.departmentservice.entity.Department;
import com.codewave.departmentservice.repository.DepartmentRepository;
import com.codewave.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentRepository departmentRepository;
    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

        //convert Department Dto to Department JPA entity
        Department department = new Department(
                departmentDto.getDepartmentId(),
                departmentDto.getDepartmentName(),
                departmentDto.getDepartmentDescription(),
                departmentDto.getDepartmentCode()

        );

        Department savedDepartment = departmentRepository.save(department);
        DepartmentDto savedDepartmentDto = new DepartmentDto(
                savedDepartment.getDepartmentId(),
                savedDepartment.getDepartmentName(),
                savedDepartment.getDepartmentDescription(),
                savedDepartment.getDepartmentCode()
        );

        return savedDepartmentDto;
    }

    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {
        Department department = departmentRepository.findByDepartmentCode(departmentCode);
        DepartmentDto departmentDto = new DepartmentDto(
                department.getDepartmentId(),
                department.getDepartmentName(),
                department.getDepartmentDescription(),
                department.getDepartmentCode()
        );
        return departmentDto;
    }
}

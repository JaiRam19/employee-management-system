package com.codewave.departmentservice.service.Impl;

import com.codewave.departmentservice.dto.DepartmentDto;
import com.codewave.departmentservice.dto.StaffCount;
import com.codewave.departmentservice.entity.Department;
import com.codewave.departmentservice.repository.DepartmentRepository;
import com.codewave.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

        //convert Department Dto to Department JPA entity
        Department department = new Department(
                departmentDto.getDepartmentId(),
                departmentDto.getDepartmentName(),
                departmentDto.getDepartmentDescription(),
                departmentDto.getDepartmentCode(),
                departmentDto.getDepartmentHeadId(),
                departmentDto.getCapacity(),
                departmentDto.getLocation()

        );

        Department savedDepartment = departmentRepository.save(department);
        return new DepartmentDto(
                savedDepartment.getDepartmentId(),
                savedDepartment.getDepartmentName(),
                savedDepartment.getDepartmentDescription(),
                savedDepartment.getDepartmentCode(),
                savedDepartment.getDepartmentHeadId(),
                savedDepartment.getCapacity(),
                savedDepartment.getLocation()
        );
    }

    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {
        Department department = departmentRepository.findByDepartmentCode(departmentCode);
        return new DepartmentDto(
                department.getDepartmentId(),
                department.getDepartmentName(),
                department.getDepartmentDescription(),
                department.getDepartmentCode(),
                department.getDepartmentHeadId(),
                department.getCapacity(),
                department.getLocation()
        );
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(department -> new DepartmentDto(
                department.getDepartmentId(),
                department.getDepartmentName(),
                department.getDepartmentDescription(),
                department.getDepartmentCode(),
                department.getDepartmentHeadId(),
                department.getCapacity(),
                department.getLocation()
        )).sorted((d1, d2) -> d1.getDepartmentId().compareTo(d2.getDepartmentId()))
                .toList();
    }

    @Override
    public List<StaffCount> getOverStaffedDepartments() {
        return getOverUnderStaffedDepts(true);
    }

    @Override
    public List<StaffCount> getUnderStaffedDepartments() {
        return getOverUnderStaffedDepts(false);
    }

    private List<StaffCount> getOverUnderStaffedDepts(boolean isOverStaffed) {
        //get all departments from the database
        List<DepartmentDto> allDepartments = getAllDepartments();

        //get the headcount of employees in each department from the employee service
        Map<String, Long> headCountByDepartmentMap = getHeadCountByDepart(allDepartments);

        //create a list of departments that are overstaffed or understaffed based on the headcount and capacity
        List<StaffCount> result = new ArrayList<>();

        //iterate through all departments and check if they are overstaffed or understaffed
        for (DepartmentDto department : allDepartments) {
            if(headCountByDepartmentMap != null && headCountByDepartmentMap.containsKey(department.getDepartmentCode())) {
                Long employeeCount = headCountByDepartmentMap.get(department.getDepartmentCode().trim());
                if (employeeCount != null && employeeCount > 0) {
                    if (!isOverStaffed && employeeCount < department.getCapacity()) {
                        result.add(new StaffCount(employeeCount, department));
                    }
                    if (isOverStaffed && employeeCount > department.getCapacity()) {
                        result.add(new StaffCount(employeeCount, department));
                    }
                }
            }
        }
        return result;
    }

    private Map<String, Long> getHeadCountByDepart(List<DepartmentDto> allDepartments) {
        //extract all department codes from the list of departments
        List<String> departmentCodes = allDepartments.stream()
                .map(dept -> dept.getDepartmentCode().trim())
                .toList();

        //call employee service to get the count of employees in each department using RestClient
        return RestClient.create("http://localhost:8081")
                .post()
                .uri("/api/employees/count-by-department")
                .body(departmentCodes)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, resp) -> {
                    throw new RuntimeException("Bad request to employee service: " + resp.getStatusCode());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, resp) -> {
                    throw new RuntimeException("Employee service error: " + resp.getStatusCode());
                })
                .body(new ParameterizedTypeReference<Map<String, Long>>() {});
    }
}

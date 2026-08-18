package com.codewave.employeeservice.service.Impl;

import com.codewave.employeeservice.dto.*;
import com.codewave.employeeservice.entity.Employee;
import com.codewave.employeeservice.repository.EmployeeRepository;
import com.codewave.employeeservice.repository.projections.DepartmentHeadcount;
import com.codewave.employeeservice.repository.specifications.EmployeeSpecifications;
import com.codewave.employeeservice.service.APIClient;
import com.codewave.employeeservice.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    /*private RestTemplate restTemplate;*/
    private WebClient webClient;

    private APIClient apiClient;

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee savedEmployee = employeeRepository.save(mapToEntity(employeeDto));
        return mapToDto(savedEmployee);
    }

    //@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        LOGGER.info("Inside getEmployeeByid() method");
        APIResponseDto apiResponseDto = null;
        if(employee.isPresent()) {
            //Get department details for the employee using apiclient(Feign client)
            DepartmentDto departmentDto = apiClient.getDepartmentByCode(employee.get().getDepartmentCode().trim());

            //Get organization details for the employee using Webclient
            OrganizationDto organizationDto = webClient.get()
                    .uri("http://localhost:8083/api/organizations/" + employee.get().getOrganizationCode().trim())
                    .retrieve()
                    .bodyToMono(OrganizationDto.class)
                    .block();

            //create APIResponse dto object to send both employee and department information to the client
            apiResponseDto = new APIResponseDto();
            apiResponseDto.setEmployeeDto(mapToDto(employee.get()));
            apiResponseDto.setDepartmentDto(departmentDto);
            apiResponseDto.setOrganizationDto(organizationDto);
        }
        return apiResponseDto;
    }

    @Override
    public Map<String, Long> getEmployeeCountByDepartment(List<String> departmentCodes) {
        try {
            return employeeRepository.countByDepartmentCodeIn(departmentCodes).stream()
                    .collect(Collectors.toMap(
                            DepartmentHeadcount::getDepartmentCode,
                            DepartmentHeadcount::getCount
                    ));
        }catch (Exception e){
            LOGGER.error("Error occurred while fetching employee count by department codes: {}", e.getMessage());
            throw new RuntimeException("Error occurred while fetching employee count by department codes: " + e.getMessage());
        }
    }

    @Override
    public List<EmployeeCandidateDto> getMovementCandidates(EmployeeFilterCriteria criteria) {
        Specification<Employee> spec = buildSpecification(criteria);
        List<Employee> candidates = employeeRepository.findAll(spec);
        return candidates
                .stream()
                .map(dto -> new EmployeeCandidateDto(
                        dto.getEmployeeId(),
                        dto.getDesignation(),
                        dto.getExperienceYears().doubleValue(),
                        dto.getEmploymentStatus(),
                        dto.getSkills()
                        ))
                .toList();
    }

    public APIResponseDto getDefaultDepartment(Long employeeId, Throwable throwable) {
        LOGGER.info("Inside fall back getDefaultDepartment() method");
        Employee employee = employeeRepository.findById(employeeId).get();

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentId(1122L);
        departmentDto.setDepartmentName("R&D Department");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Research and development");


        //create APIResponse dto object to send both employee and department information to the client
        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(mapToDto(employee));
        apiResponseDto.setDepartmentDto(departmentDto);

        return apiResponseDto;
    }

    private EmployeeDto mapToDto(Employee employee) {
        return objectMapper.convertValue(employee, EmployeeDto.class);
    }

    private Employee mapToEntity(EmployeeDto employeeDto) {
        return objectMapper.convertValue(employeeDto, Employee.class);
    }

    private Specification<Employee> buildSpecification(EmployeeFilterCriteria criteria) {
        Specification<Employee> spec = Specification.where(
                EmployeeSpecifications.hasDepartmentCode(criteria.getDepartmentCode())
        );

        if (criteria.getEmploymentStatuses() != null && !criteria.getEmploymentStatuses().isEmpty()) {
            spec = spec.and(EmployeeSpecifications.hasStatusIn(criteria.getEmploymentStatuses()));
        }
        if (criteria.getMinExperience() != null && criteria.getMaxExperience() != null) {
            spec = spec.and(EmployeeSpecifications.experienceBetween(
                    criteria.getMinExperience(), criteria.getMaxExperience()));
        }
        if (criteria.getDesignations() != null && !criteria.getDesignations().isEmpty()) {
            spec = spec.and(EmployeeSpecifications.hasDesignationIn(criteria.getDesignations()));
        }
        return spec;
    }

}

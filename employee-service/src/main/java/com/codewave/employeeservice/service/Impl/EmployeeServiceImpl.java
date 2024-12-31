package com.codewave.employeeservice.service.Impl;

import com.codewave.employeeservice.dto.APIResponseDto;
import com.codewave.employeeservice.dto.DepartmentDto;
import com.codewave.employeeservice.dto.EmployeeDto;
import com.codewave.employeeservice.dto.OrganizationDto;
import com.codewave.employeeservice.entity.Employee;
import com.codewave.employeeservice.repository.EmployeeRepository;
import com.codewave.employeeservice.service.APIClient;
import com.codewave.employeeservice.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

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
        Employee employee = employeeRepository.findById(employeeId).get();
        LOGGER.info("Inside getEmployeeByid() method");
        //this response entity contains department dto object in it's body
        /*ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/"+employee.getDepartmentCode(),
                DepartmentDto.class);*/

        //get the department dto object from above response entity
        /*DepartmentDto departmentDto = responseEntity.getBody();*/

        //Get the department details for the employee using webClient object
       /* DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/api/departments/"+employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();*/

        //Get department details for the employee using apiclient(Feign client)
        DepartmentDto departmentDto = apiClient.getDepartmentByCode(employee.getDepartmentCode());
        OrganizationDto organizationDto =webClient.get()
                .uri("http://localhost:8083/api/organizations/"+employee.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();

        //create APIResponse dto object to send both employee and department information to the client
        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(mapToDto(employee));
        apiResponseDto.setDepartmentDto(departmentDto);
        apiResponseDto.setOrganizationDto(organizationDto);

        return apiResponseDto;
    }

    public APIResponseDto getDefaultDepartment(Long employeeId, Throwable throwable) {
        LOGGER.info("Inside fall back getDefaultDepartment() method");
        Employee employee = employeeRepository.findById(employeeId).get();

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentId(Long.valueOf(1122));
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

}

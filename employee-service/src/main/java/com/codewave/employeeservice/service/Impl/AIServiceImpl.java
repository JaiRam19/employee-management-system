package com.codewave.employeeservice.service.Impl;

import com.codewave.employeeservice.dto.APIResponseDto;
import com.codewave.employeeservice.dto.EmployeeSummaryResponse;
import com.codewave.employeeservice.service.AIService;
import com.codewave.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIServiceImpl implements AIService {

    private final EmployeeService employeeService;

    @Override
    public String generateSummary(Long employeeId) {

        try{
            APIResponseDto apiResponseDto = employeeService.getEmployeeById(employeeId);
            if(apiResponseDto != null) {
                RestClient restClient = RestClient.create("http://localhost:8000");
                EmployeeSummaryResponse response = restClient
                        .post()
                        .uri("/api/ai/employee/generate-summary")
                        .body(apiResponseDto)
                        .retrieve()
                        .body(EmployeeSummaryResponse.class);
                log.info("AI Service response: {}", response);
                if(response != null) {
                    return response.getSummary();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to while retrieve employee details and generate summary for employeeId: " + employeeId, e);
        }

        return "Summary not available.";
    }
}

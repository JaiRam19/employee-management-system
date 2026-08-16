package com.codewave.employeeservice.service;

import com.codewave.employeeservice.dto.APIResponseDto;

public interface AIService {
    String generateSummary(Long employeeId);
}

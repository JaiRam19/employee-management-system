package com.codewave.departmentservice.dto;

import java.util.List;

public record AiStaffingResponseDto(
        List<RecommendationDto> recommendations,
        List<Long> unmatchedEmployeeIds
) {}

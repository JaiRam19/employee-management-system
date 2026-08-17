package com.codewave.departmentservice.dto;

import java.util.List;

public record StaffingResponseDto(
        List<RecommendationDto> recommendations,
        List<Long> unmatchedEmployeeIds
) {}

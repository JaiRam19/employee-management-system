package com.codewave.departmentservice.dto;

public record RecommendationDto(
        Long employeeId,
        String fromDepartment,
        String toDepartment,
        String reason,
        String confidence
) {}
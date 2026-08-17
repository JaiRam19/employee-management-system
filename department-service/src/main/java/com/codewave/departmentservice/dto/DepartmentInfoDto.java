package com.codewave.departmentservice.dto;

public record DepartmentInfoDto(
        String departmentCode,
        String departmentName,
        Integer capacity,
        Integer currentHeadcount
) {}

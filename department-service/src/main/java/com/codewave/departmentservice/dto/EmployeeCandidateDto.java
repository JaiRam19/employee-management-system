package com.codewave.departmentservice.dto;

public record EmployeeCandidateDto(
        Long employeeId,
        String designation,
        Double experienceYears,
        String employmentStatus,
        String skills
) {}
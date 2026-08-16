package com.codewave.employeeservice.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class EmployeeSummaryResponse {
    private Integer employeeId;
    private String summary;
}

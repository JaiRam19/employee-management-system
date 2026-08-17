package com.codewave.organizationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {
    private Long organizationId;
    private String organizationName;
    private String organizationDescription;
    private String organizationCode;
    private LocalDateTime createdDate;
    private String industryType;
    private String headquartersLocation;
    private Integer employeeCount;
}

package com.codewave.employeeservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdDate;
    private String industryType;
    private String headquartersLocation;
    private Integer employeeCount;
}

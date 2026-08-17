package com.codewave.employeeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String departmentCode;
    private String organizationCode;
    private String skills;
    private String designation;
    private LocalDate joiningDate;
    private String employmentStatus;
    private BigDecimal experienceYears;
    private Long managerId;
    private String employmentType;
}

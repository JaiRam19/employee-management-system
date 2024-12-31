package com.codewave.organizationservice.service;

import com.codewave.organizationservice.dto.OrganizationDto;
import org.springframework.stereotype.Service;

@Service
public interface OrganizationService {
    OrganizationDto saveOrganization(OrganizationDto organizationDto);
    OrganizationDto getOrganizationByCode(String code);
}

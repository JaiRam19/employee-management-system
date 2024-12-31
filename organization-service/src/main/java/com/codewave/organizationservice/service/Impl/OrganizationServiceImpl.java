package com.codewave.organizationservice.service.Impl;

import com.codewave.organizationservice.dto.OrganizationDto;
import com.codewave.organizationservice.entity.Organization;
import com.codewave.organizationservice.mapper.OrganizationMapper;
import com.codewave.organizationservice.repository.OrganizationRepository;
import com.codewave.organizationservice.service.OrganizationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
        Organization organization = OrganizationMapper.mapToEntity(organizationDto);
        Organization savedOrganization = organizationRepository.save(organization);
        return OrganizationMapper.mapToDto(savedOrganization);
    }

    @Override
    public OrganizationDto getOrganizationByCode(String code) {
        Organization organization = organizationRepository.findByOrganizationCode(code);
        return OrganizationMapper.mapToDto(organization);
    }


}

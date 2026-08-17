package com.codewave.organizationservice.mapper;

import com.codewave.organizationservice.dto.OrganizationDto;
import com.codewave.organizationservice.entity.Organization;

public class OrganizationMapper {

    public static Organization mapToEntity(OrganizationDto organizationDto){
        Organization organization = new Organization();
        organization.setOrganizationId(organization.getOrganizationId());
        organization.setOrganizationName(organizationDto.getOrganizationName());
        organization.setOrganizationDescription(organizationDto.getOrganizationDescription());
        organization.setOrganizationCode(organizationDto.getOrganizationCode());
        organization.setCreatedDate(organizationDto.getCreatedDate());
        organization.setIndustryType(organizationDto.getIndustryType());
        organization.setHeadquartersLocation(organizationDto.getHeadquartersLocation());
        organization.setEmployeeCount(organizationDto.getEmployeeCount());
        return organization;
    }

    public static OrganizationDto mapToDto(Organization organization){
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setOrganizationId(organization.getOrganizationId());
        organizationDto.setOrganizationName(organization.getOrganizationName());
        organizationDto.setOrganizationDescription(organization.getOrganizationDescription());
        organizationDto.setOrganizationCode(organization.getOrganizationCode());
        organizationDto.setCreatedDate(organization.getCreatedDate());
        organizationDto.setIndustryType(organization.getIndustryType());
        organizationDto.setHeadquartersLocation(organization.getHeadquartersLocation());
        organizationDto.setEmployeeCount(organization.getEmployeeCount());
        return organizationDto;
    }


}

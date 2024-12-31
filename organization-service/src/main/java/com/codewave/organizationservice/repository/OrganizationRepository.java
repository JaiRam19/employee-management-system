package com.codewave.organizationservice.repository;

import com.codewave.organizationservice.dto.OrganizationDto;
import com.codewave.organizationservice.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Organization findByOrganizationCode(String code);
}

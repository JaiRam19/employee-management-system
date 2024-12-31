package com.codewave.organizationservice.controller;

import com.codewave.organizationservice.dto.OrganizationDto;
import com.codewave.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/organizations")
@AllArgsConstructor
public class OrganizationController {

    private OrganizationService organizationService;

    //save organization REST API
    @PostMapping()
    public ResponseEntity<OrganizationDto> saveOrganization(@RequestBody OrganizationDto organizationDto) {
        OrganizationDto savedOrganization = organizationService.saveOrganization(organizationDto);
        return new ResponseEntity<>(savedOrganization, HttpStatus.CREATED);
    }

    //get organization by code
    @GetMapping("/{code}")
    public ResponseEntity<OrganizationDto> getOrganizationByCode(@PathVariable("code") String code){
        return ResponseEntity.ok(organizationService.getOrganizationByCode(code));
    }
}

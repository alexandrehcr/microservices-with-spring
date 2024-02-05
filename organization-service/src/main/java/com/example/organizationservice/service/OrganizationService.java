package com.example.organizationservice.service;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.entity.Organization;
import com.example.organizationservice.exception.ResourceNotFoundException;
import com.example.organizationservice.repository.OrganizationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final ModelMapper mapper;

    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
        Organization organization = mapper.map(organizationDto, Organization.class);
        Organization saved = organizationRepository.save(organization);
        return mapper.map(saved, OrganizationDto.class);
    }

    public OrganizationDto getOrganizationByCode(String code) {
        Organization organization = organizationRepository.findByCode(code).orElseThrow(
                () -> new ResourceNotFoundException("Organization with code '" + code + "' not found"));
        return mapper.map(organization, OrganizationDto.class);
    }
}

package com.example.organizationservice.controller;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/organizations")
@AllArgsConstructor
public class OrganizationController {

    private OrganizationService organizationService;

    @Operation(summary = "Get an organization by its code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the organization"),
            @ApiResponse(responseCode = "400", description = "Invalid organization code supplied"),
            @ApiResponse(responseCode = "404", description = "Organization not found")})
    @GetMapping("/{organizationCode}")
    public ResponseEntity<OrganizationDto> getOrganizationByCode(@PathVariable String organizationCode) {
        return ResponseEntity.ok(organizationService.getOrganizationByCode(organizationCode));
    }

    @Operation(summary = "Save Organization REST API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saved employee"),
            @ApiResponse(responseCode = "400", description = "Invalid request body supplied"),
            @ApiResponse(responseCode = "409", description = "Given employee email is already taken")})
    @PostMapping
    public ResponseEntity<OrganizationDto> saveOrganization(@RequestBody OrganizationDto organizationDto) {
        OrganizationDto saved = organizationService.saveOrganization(organizationDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

}

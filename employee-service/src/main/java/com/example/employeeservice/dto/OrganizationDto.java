package com.example.employeeservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public record OrganizationDto(
    Long id,
    String name,
    String code,
    String description,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss O", timezone = "America/Sao_Paulo")
    String creationDate) {
}

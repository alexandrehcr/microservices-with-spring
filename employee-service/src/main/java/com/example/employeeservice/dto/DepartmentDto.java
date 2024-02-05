package com.example.employeeservice.dto;

public record DepartmentDto(
        Long id,
        String name,
        String description,
        String code) {
}

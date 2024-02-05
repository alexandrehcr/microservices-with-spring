package com.example.employeeservice.dto;

public record ApiResponseDto(
        EmployeeDto employeeDto,
        DepartmentDto departmentDto,
        OrganizationDto organizationDto) {
}

package com.example.employeeservice.service.clients;

import com.example.employeeservice.dto.DepartmentDto;
import com.example.employeeservice.dto.OrganizationDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@AllArgsConstructor
@Service
public class DepartmentClient {

    private final RestClient.Builder restClient;

    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getFallbackDepartment")
    public DepartmentDto getDepartmentByDepartmentCode(String departmentCode) {
        return restClient.build().get()
                .uri("http://DEPARTMENT-SERVICE/api/v1/departments/" + departmentCode)
                .retrieve()
                .body(DepartmentDto.class);
    }

    public DepartmentDto getFallbackDepartment(String departmentCode, Throwable throwable) {
        return new DepartmentDto(null, null, "Service unavailable", null);
    }
}

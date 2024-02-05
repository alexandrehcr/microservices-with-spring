package com.example.employeeservice.service.clients;

import com.example.employeeservice.dto.OrganizationDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@AllArgsConstructor
@Service
public class OrganizationClient {

    private final RestClient.Builder restClient;

    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getFallbackOrganization")
    public OrganizationDto getOrganizationByOrganizationCode(String organizationCode) {
        return restClient.build().get()
                .uri("http://ORGANIZATION-SERVICE/api/v1/organizations/" + organizationCode)
                .retrieve()
                .body(OrganizationDto.class);
    }

    public OrganizationDto getFallbackOrganization(String organizationCode, Throwable throwable) {
        return new OrganizationDto(null, null, null, "Service unavailable", null);
    }
}

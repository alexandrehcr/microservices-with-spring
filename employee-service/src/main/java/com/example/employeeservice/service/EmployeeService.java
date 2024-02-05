package com.example.employeeservice.service;

import com.example.employeeservice.dto.ApiResponseDto;
import com.example.employeeservice.dto.DepartmentDto;
import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.dto.OrganizationDto;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.exception.ResourceNotFoundException;
import com.example.employeeservice.repository.EmployeeRepository;
import com.example.employeeservice.service.clients.DepartmentClient;
import com.example.employeeservice.service.clients.OrganizationClient;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final ModelMapper mapper;
    private final EmployeeRepository employeeRepository;
    private final OrganizationClient organizationClient;
    private final DepartmentClient departmentClient;

    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = mapper.map(employeeDto, Employee.class);

        if(employeeRepository.existsByEmail(employee.getEmail())){
            throw new DataIntegrityViolationException(
                    "An employee is already registered with email %s".formatted(employee.getEmail()));
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return mapper.map(savedEmployee, EmployeeDto.class);
    }

    public ApiResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No employee with id %d found.".formatted(id)));

        EmployeeDto employeeDto = mapper.map(employee, EmployeeDto.class);

        DepartmentDto departmentDto = departmentClient.getDepartmentByDepartmentCode(employee.getDepartmentCode());
        OrganizationDto organizationDto = organizationClient.getOrganizationByOrganizationCode(employee.getOrganizationCode());

        return new ApiResponseDto(employeeDto, departmentDto, organizationDto);
    }
}

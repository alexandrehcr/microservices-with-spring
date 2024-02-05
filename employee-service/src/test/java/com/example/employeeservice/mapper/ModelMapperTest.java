package com.example.employeeservice.mapper;

import com.example.employeeservice.EmployeeServiceApplication;
import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.entity.Employee;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelMapperTest {

    ModelMapper mapper = new EmployeeServiceApplication().modelMapper();

    @Test
    void mapToDto() {
        // Setup
        Employee employee = new Employee(1L, "John", "Doe", "johndoe@email.com", "DC001", "OC001");

        // Action
        EmployeeDto employeeDto = mapper.map(employee, EmployeeDto.class);

        // Assertions
        assertEquals(employee.getId(), employeeDto.getId());
        assertEquals(employee.getFirstName(), employeeDto.getFirstName());
        assertEquals(employee.getLastName(), employeeDto.getLastName());
        assertEquals(employee.getEmail(), employeeDto.getEmail());
        assertEquals(employee.getDepartmentCode(), employeeDto.getDepartmentCode());
        assertEquals(employee.getOrganizationCode(), employeeDto.getOrganizationCode());
    }

    @Test
    void mapToEntity() {
        EmployeeDto employeeDto = new EmployeeDto(1234L, "John", "Doe", "JOHNDOE@EMAIL.COM", "DC01", "OC001");
        String expectedEmail = employeeDto.getEmail().toLowerCase();

        Employee employee = mapper.map(employeeDto, Employee.class);

        assertThat(employee.getId()).isNull();
        assertEquals(expectedEmail, employee.getEmail());
        assertThat(employee)
                .extracting("firstName", "lastName", "departmentCode", "organizationCode")
                .containsExactly(employee.getFirstName(), employee.getLastName(), employee.getDepartmentCode(), employee.getOrganizationCode());
    }
}

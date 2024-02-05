package com.example.employeeservice.controller;

import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EmployeeRepository employeeRepository;


    @Test
    @Transactional
    void givenEmployeeDtoWithId_whenSaveEmployee_thenSaveIgnoringDtoIdAndLowerCasingEmail() throws Exception {
        // Arrange
        EmployeeDto dto = new EmployeeDto(123L, "John", "Doe", "JOHNDOE@EMAIL.COM", "DC001", "OC01");
        String jsonOfEmployeeDto = new ObjectMapper().writeValueAsString(dto);

        // Action
        MockHttpServletRequestBuilder request = post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOfEmployeeDto);

        // Assertion
        mockMvc.perform(request)
                .andDo(print())
                .andExpectAll(status().isCreated(),
                        jsonPath("$.id", not(dto.getId()), Long.class),
                        jsonPath("$.firstName", is(dto.getFirstName())),
                        jsonPath("$.lastName", is(dto.getLastName())),
                        jsonPath("$.email", is(dto.getEmail().toLowerCase())),
                        jsonPath("$.departmentCode", is(dto.getDepartmentCode())),
                        jsonPath("$.organizationCode", is(dto.getOrganizationCode())));
    }

    @Test
    @Transactional
    void givenEmployeeDtoWithExistingEmail_whenSaveEmployee_thenConflict() throws Exception {
        // Arrange
        EmployeeDto employeeDto = new EmployeeDto(null, "Johnny", "Doeby", "johndoe@gmail.com", "HR001", "OC01");
        Employee employee = new ModelMapper().map(employeeDto, Employee.class);
        employeeRepository.save(employee);
        String jsonOfEmployeeDto = new ObjectMapper().writeValueAsString(employee);

        // Action
        MockHttpServletRequestBuilder request = post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOfEmployeeDto);

        // Assertion
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void givenNonExistentEmployeeId_whenGetEmployee_thenThrowResourceNotFoundException() throws Exception {
        mockMvc.perform(get("/api/v1/employees/9999")).andDo(print())
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.timestamp", notNullValue()),
                        jsonPath("$.messages[*]", containsInAnyOrder("No employee with id 9999 found.")));
    }

    // todo: how do I test it since I cannot inject the Department and Organization repository here?
    /*
    @Sql(scripts = "/scripts/data.sql", executionPhase = BEFORE_TEST_METHOD)
    @Test
    void givenExistentEmployeeId_whenGetEmployee_thenReturnApiResponse() throws Exception {
        mockMvc.perform(get("/api/v1/employees/1995")).andDo(print())
                .andExpectAll(status().isOk());
    }
 */
}

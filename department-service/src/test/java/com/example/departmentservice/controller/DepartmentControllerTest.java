package com.example.departmentservice.controller;

import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class DepartmentControllerTest {

    MockMvc mockMvc;
    DepartmentService departmentService;

    @Autowired
    public DepartmentControllerTest(MockMvc mockMvc, DepartmentService departmentService) {
        this.mockMvc = mockMvc;
        this.departmentService = departmentService;
    }

    @Test
    @Transactional
    void givenDepartmentDtoWithId_whenSaveDepartment_thenIgnoreDtoId() throws Exception {
        // Setup
        DepartmentDto dto = new DepartmentDto(123L, "Qux", "Baz", "QB01");
        String jsonOfDepartmentDto = new ObjectMapper().writeValueAsString(dto);

        // Action
        MockHttpServletRequestBuilder request = post("/api/v1/departments")
                .contentType(APPLICATION_JSON)
                .content(jsonOfDepartmentDto);

        // Assertions
        mockMvc
                .perform(request)
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id", not(dto.getId())), // ID must be set by the DB
                        jsonPath("$.departmentName", is(dto.getName())),
                        jsonPath("$.departmentDescription", is(dto.getDescription())),
                        jsonPath("$.departmentCode", is(dto.getCode())));
    }

    @Test
    @Transactional
    void givenDepartmentDtoWithExistingId_whenSaveDepartment_thenConflict() throws Exception {
        // Setup
        DepartmentDto dto = new DepartmentDto(123L, "Qux", "Baz", "QB01");
        departmentService.saveDepartment(dto);
        String jsonOfDepartmentDto = new ObjectMapper().writeValueAsString(dto);

        // Action
        MockHttpServletRequestBuilder request = post("/api/v1/departments")
                .contentType(APPLICATION_JSON)
                .content(jsonOfDepartmentDto);

        mockMvc
                .perform(request)
                .andDo(print())
                .andExpect(status().isConflict());
    }


    @Test
    @Transactional
    void getDepartment() throws Exception {
        DepartmentDto departmentDto = new DepartmentDto(null, "Foo", "Bar", "FB01");
        departmentService.saveDepartment(departmentDto);

        DepartmentDto found = departmentService.getDepartmentByCode("FB01");

        mockMvc
                .perform(get("/api/v1/departments/FB01"))
                .andDo(print())
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.id", notNullValue()),
                        jsonPath("$.departmentCode", is(found.getCode())),
                        jsonPath("$.departmentName", is(found.getName())),
                        jsonPath("$.departmentDescription", is(found.getDescription())));
    }
}

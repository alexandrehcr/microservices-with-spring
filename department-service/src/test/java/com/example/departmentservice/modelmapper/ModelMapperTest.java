package com.example.departmentservice.modelmapper;

import com.example.departmentservice.DepartmentServiceApplication;
import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.entity.Department;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelMapperTest {

    // It might be a better way to test the defined Model Mapper
    // behavior without tightly coupling. I just didn't figure it out yet.
    ModelMapper mapper = new DepartmentServiceApplication().modelMapper();

    @Test
    void mapToDto() {
        // Setup
        Department department = new Department(1L, "Foo", "Bar", "FB01");

        // Action
        DepartmentDto dto = mapper.map(department, DepartmentDto.class);

        // Assertions
        assertEquals(dto.getId(), department.getId());
        assertEquals(dto.getName(), department.getName());
        assertEquals(dto.getDescription(), department.getDescription());
        assertEquals(dto.getCode(), department.getCode());
    }


    @Test
    void mapToEntity() {
        // A request with a DTO defining an ID should not change the entity ID
        DepartmentDto departmentDto = new DepartmentDto(200L, "Foo", "Bar", "FB01");

        Department department = mapper.map(departmentDto, Department.class);

        assertThat(department.getId()).isNull();
        assertEquals(departmentDto.getName(), department.getName());
        assertEquals(departmentDto.getDescription(), department.getDescription());
        assertEquals(departmentDto.getCode(), department.getCode());
    }
}

package com.example.departmentservice.service;

import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.entity.Department;
import com.example.departmentservice.exception.ResourceNotFoundException;
import com.example.departmentservice.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper mapper;

    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        String departmentCode = departmentDto.getCode();
        if (departmentRepository.existsByCode(departmentCode)){
            throw new DataIntegrityViolationException(
                    "A department is already registered with code %s".formatted(departmentCode));
        }
        Department department = mapper.map(departmentDto, Department.class);
        return mapper.map(departmentRepository.save(department), DepartmentDto.class);
    }

    public DepartmentDto getDepartmentByCode(String departmentCode) {
        Department department = departmentRepository.findByCode(departmentCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No department with code %s was found.".formatted(departmentCode)));
        return mapper.map(department, DepartmentDto.class);
    }
}

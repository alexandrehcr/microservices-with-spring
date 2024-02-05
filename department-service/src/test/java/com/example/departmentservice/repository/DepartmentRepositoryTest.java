package com.example.departmentservice.repository;

import com.example.departmentservice.entity.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class DepartmentRepositoryTest {

    DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentRepositoryTest(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @BeforeEach
    public void setup() {
        departmentRepository.deleteAll();

        Department d1 = new Department();
        d1.setName("Foo");
        d1.setDescription("Bar");
        d1.setCode("FB01");

        Department d2 = new Department();
        d2.setName("Qux");
        d2.setDescription("Baz");
        d2.setCode("QB01");

        departmentRepository.saveAll(List.of(d1, d2));
    }


    @Test
    void findAll() {
        List<Department> allDepartments = departmentRepository.findAll();
        assertEquals(2, allDepartments.size());
    }

    @Test
    void findByDepartmentCode() {
        // Setup
        String expectedDepartmentCode = "QB01";
        String expectedDepartmentName = "Qux";
        String expectedDepartmentDescription = "Baz";

        // Action
        Optional<Department> optionalDepartment = departmentRepository.findByCode(expectedDepartmentCode);

        // Assertions
        Department found = optionalDepartment.orElseThrow(
                () -> new NoSuchElementException(String.format(
                        "No department with code %s found.", expectedDepartmentCode)));
        assertEquals(expectedDepartmentCode, found.getCode());
        assertEquals(expectedDepartmentName, found.getName());
        assertEquals(expectedDepartmentDescription, found.getDescription());
    }
}

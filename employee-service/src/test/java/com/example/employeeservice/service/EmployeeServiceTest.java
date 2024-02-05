package com.example.employeeservice.service;

import com.example.employeeservice.EmployeeServiceApplication;
import com.example.employeeservice.dto.ApiResponseDto;
import com.example.employeeservice.dto.DepartmentDto;
import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.exception.ResourceNotFoundException;
import com.example.employeeservice.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @Spy
    ModelMapper modelMapper = new EmployeeServiceApplication().modelMapper();

//    @Mock
//    RestTemplate restTemplate;

    @InjectMocks
    EmployeeService employeeService;


    @Test
    void givenEmployeeDto_whenEmailIsNotTaken_thenSaveEntityLowerCasingEmail() {
        // Given
        EmployeeDto requestDto = new EmployeeDto(null, "John", "Doe", "JOHNDOE@EMAIL.COM", "DC001", "OC001");
        given(employeeRepository.existsByEmail(anyString()))
                .willReturn(false);
        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
        given(employeeRepository.save(employeeCaptor.capture()))
                .willAnswer(invocation -> invocation.getArgument(0));

        final int expectedNumberOfSaveInvocations = 1;

        // When
        EmployeeDto savedEmployeeDto = employeeService.saveEmployee(requestDto);


        // Then
        assertThat(savedEmployeeDto).isNotNull();
        assertThat(savedEmployeeDto.getFirstName()).isEqualTo(requestDto.getFirstName());
        assertThat(employeeCaptor.getValue().getEmail()).isEqualTo(requestDto.getEmail().toLowerCase());
        assertThat(savedEmployeeDto.getDepartmentCode()).isEqualTo(requestDto.getDepartmentCode());
        verify(employeeRepository, times(expectedNumberOfSaveInvocations)).save(any(Employee.class));
    }

    @Test
    void givenEmployeeDto_whenEmailIsTaken_thenThrowException() {
        // Given
        EmployeeDto requestDto = new EmployeeDto(1L, "John", "Doe", "johndoe@email.com", "DC001", "OC001");
        given(employeeRepository.existsByEmail(anyString()))
                .willReturn(true);

        // When
        assertThrows(DataIntegrityViolationException.class,
                () -> employeeService.saveEmployee(requestDto));

        // Then
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void givenNoEmployeeExistsWithGivenId_whenGetEmployeeById_thenThrowException() {
        given(employeeRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.getEmployeeById(anyLong()));
    }

//    @Test
//    void givenEmployeeExists_whenGetEmployeeById_thenReturnApiResponse() {
//        DepartmentDto departmentDto = new DepartmentDto(1L, "Department Name", "Department Description", "DC001");
//        ResponseEntity<DepartmentDto> response = ResponseEntity.ok(departmentDto);
//        Employee employee = new Employee(1L, "John", "Doe", "johndoe@email.com", "DC001");
//        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
//
//        given(restTemplate.getForEntity(anyString(), eq(DepartmentDto.class), eq(employee.getDepartmentCode())))
//                .willReturn(response);
//        given(employeeRepository.findById((anyLong())))
//                .willReturn(Optional.of(employee));
//
//        // When
//        ApiResponseDto apiResponse = employeeService.getEmployeeById(anyLong());
//
//        assertThat(apiResponse.departmentDto()).isEqualTo(departmentDto);
//        assertThat(apiResponse.employeeDto()).isEqualTo(employeeDto);
//    }

}

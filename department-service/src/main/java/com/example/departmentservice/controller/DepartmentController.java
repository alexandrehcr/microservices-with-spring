package com.example.departmentservice.controller;

import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Department Service Controller",
        description = "REST APIs for Department Service")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;


    @Operation(summary = "Get a department by its code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the department"),
            @ApiResponse(responseCode = "400", description = "Invalid department code supplied"),
            @ApiResponse(responseCode = "404", description = "Department not found")})
    @GetMapping("/{department-code}")
    public ResponseEntity<DepartmentDto> getDepartmentByCode(@PathVariable(value = "department-code") String departmentCode) {
        return ResponseEntity.ok(departmentService.getDepartmentByCode(departmentCode));
    }


    @Operation(summary = "Save Department REST API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saved department"),
            @ApiResponse(responseCode = "400", description = "Invalid request body supplied"),
            @ApiResponse(responseCode = "409", description = "Given department code is already taken")})
    @PostMapping
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto) {
        return new ResponseEntity<>(departmentService.saveDepartment(departmentDto), HttpStatus.CREATED);
    }
}

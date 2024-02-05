package com.example.departmentservice;

import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.entity.Department;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@OpenAPIDefinition(info = @Info(
        title = "Department Service REST APIs",
        description = "Department Service REST API documentation",
        version = "v1.0",
        contact = @Contact(
                name = "Alexandre Rocha",
                email = "alexandrehc.rocha@gmail.com"),
        license = @License(name = "Apache 2.0")))
@SpringBootApplication
public class DepartmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DepartmentServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        mapper.typeMap(DepartmentDto.class, Department.class)
                .addMappings(map -> {
                    Converter<String, String> upperCaseConverter =
                            ctx -> ctx.getSource().toUpperCase();
                    map.using(upperCaseConverter)
                            .map(DepartmentDto::getCode, Department::setCode);
                    map.skip(Department::setId);
                });
        return mapper;
    }
}

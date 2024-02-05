package com.example.employeeservice;

import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.entity.Employee;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@OpenAPIDefinition(info = @Info(
		title = "Employee Service REST APIs",
		description = "Employee Service REST API documentation",
		version = "v1.0",
		contact = @Contact(
				name = "Alexandre Rocha",
				email = "alexandrehc.rocha@gmail.com"),
		license = @License(name = "Apache 2.0")))
@SpringBootApplication
public class EmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestClient.Builder restClient(){
		return RestClient.builder();
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled(true);
		mapper.typeMap(EmployeeDto.class, Employee.class)
				.addMappings(map -> {
					Converter<String, String> lowerCaseConverter = ctx -> ctx.getSource().toLowerCase();
					map.using(lowerCaseConverter)
							.map(EmployeeDto::getEmail, Employee::setEmail);
					map.skip(Employee::setId);
				});
		return mapper;
	}
}

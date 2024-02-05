package com.example.organizationservice;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.entity.Organization;
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
		title = "Organization Service REST APIs",
		description = "Organization Service REST API documentation",
		version = "v1.0",
		contact = @Contact(
				name = "Alexandre Rocha",
				email = "alexandrehc.rocha@gmail.com"),
		license = @License(name = "Apache 2.0")))
@SpringBootApplication
public class OrganizationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganizationServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled(true);
		mapper.typeMap(OrganizationDto.class, Organization.class)
				.addMappings(map -> {
					Converter<String, String> upperCaseConverter =
							ctx -> ctx.getSource().toUpperCase();
					map.using(upperCaseConverter)
							.map(OrganizationDto::getCode, Organization::setCode);
					map.skip(Organization::setId);
				});
		return mapper;
	}
}

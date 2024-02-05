package com.example.employeeservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ApplicationErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss O", timezone = "America/Sao_Paulo")
    final ZonedDateTime timestamp;
    List<String> messages;

    public ApplicationErrorResponse(List<String> messages) {
        this.messages = messages;
        timestamp = ZonedDateTime.now();
    }

    public ApplicationErrorResponse(String message){
        this(Arrays.asList(message));
    }

}

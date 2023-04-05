package com.example.socksstyorageapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class SocksStyorageAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocksStyorageAppApplication.class, args);
    }

}

package com.openclassrooms.mddapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Classe principale de l'application.
 */
@SpringBootApplication
@EntityScan("com.openclassrooms.mddapi.models")
public class MddApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MddApiApplication.class, args);
    }
}

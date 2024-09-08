package com.openclassrooms.mddapi.payload.request;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * La classe RegisterRequest est utilisée pour stocker les informations d'inscription
 */
@Schema(name = "RegisterRequest", description = "La classe RegisterRequest est utilisée pour stocker les informations d'inscription")
public class RegisterRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public String getName() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRole() {
        return this.role;
    }
}
package com.openclassrooms.mddapi.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * La classe LoginRequest est utilisée pour stocker les informations de connexion
 */
@Schema(name = "LoginRequest", description = "La classe LoginRequest est utilisée pour stocker les informations de connexion")
public class LoginRequest {
    @NotBlank
    private String emailOrUsername;

    @NotBlank
    private String password;

    public String getEmailOrUsername() {
        return emailOrUsername;
    }

    public String getPassword() {
        return password;
    }
}

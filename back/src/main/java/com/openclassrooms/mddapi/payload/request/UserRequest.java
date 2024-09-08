package com.openclassrooms.mddapi.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * La classe UserRequest est utilisée pour stocker les informations de l'utilisateur
 */
@Schema(name = "UserRequest", description = "La classe UserRequest est utilisée pour stocker les informations de l'utilisateur")
public class UserRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

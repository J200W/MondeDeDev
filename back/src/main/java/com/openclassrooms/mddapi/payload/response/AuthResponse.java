package com.openclassrooms.mddapi.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * La classe AuthResponse est un objet de transfert de données pour les réponses
 * d'authentification.
 */
@Schema(name = "AuthResponse", description = "La classe AuthResponse est un objet de transfert de données pour les réponses d'authentification")
@AllArgsConstructor
public class AuthResponse {
    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private String email;
    @Getter
    private final List<String> roles;
    @Getter
    @Setter
    private Integer statusCode;
}
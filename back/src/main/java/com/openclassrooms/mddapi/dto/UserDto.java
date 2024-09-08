package com.openclassrooms.mddapi.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La classe UserDto représente un utilisateur lors de la communication avec
 * l'API (réception ou envoi de données).
 */
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserDto", description = "La classe UserDto représente un utilisateur lors de la communication avec l'API (réception ou envoi de données)")
 public class UserDto {

    /**
     * L'adresse email de l'utilisateur
     */
    @Getter
    @Setter
    public String email;

    /**
     * Le nom d'utilisateur de l'utilisateur
     */
    @Getter
    @Setter
    public String username;

    /**
     * Le rôle de l'utilisateur
     */
    @Getter
    @Setter
    public List<String> role = List.of();
}

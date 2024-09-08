package com.openclassrooms.mddapi.dto;

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
 public class UserNoRoleDto {
    @Getter
    @Setter
    public String email;

    @Getter
    @Setter
    public String username;
}

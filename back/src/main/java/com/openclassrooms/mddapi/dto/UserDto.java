package com.openclassrooms.mddapi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La classe UserDto représente un utilisateur lors de la communication avec
 * l'API
 * (réception ou envoi de données).
 * Elle est caractérisée par :
 * 
 * @email
 *        L'adresse email de l'utilisateur
 * @username
 *           Le nom d'utilisateur de l'utilisateur
 * @role
 *       Le rôle de l'utilisateur
 */
@AllArgsConstructor
@NoArgsConstructor
 public class UserDto {
    @Getter
    @Setter
    public String email;

    @Getter
    @Setter
    public String username;

    @Getter
    @Setter
    public List<String> role = List.of();
}

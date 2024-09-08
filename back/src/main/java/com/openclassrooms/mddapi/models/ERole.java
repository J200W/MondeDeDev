package com.openclassrooms.mddapi.models;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Les rôles des utilisateurs
 */
@Schema(name = "ERole", description = "Les rôles des utilisateurs")
public enum ERole {
    ROLE_USER,
    ROLE_ADMIN
}

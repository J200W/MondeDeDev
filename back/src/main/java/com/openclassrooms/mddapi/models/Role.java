package com.openclassrooms.mddapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

/**
 * La classe Role est utilisée pour stocker les rôles des utilisateurs
 */

@Data
@Entity
@Table(name = "role")
public class Role {

    /**
     * L'identifiant du rôle
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Le nom du rôle
     */

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    /**
     * Le constructeur du rôle
     */

    public Role() {}

    /**
     * Le constructeur du rôle avec le nom du rôle
     * @param name Le nom du rôle
     */
    public Role(ERole name) {
        this.name = name;
    }

}

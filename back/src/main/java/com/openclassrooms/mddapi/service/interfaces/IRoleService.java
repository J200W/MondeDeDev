package com.openclassrooms.mddapi.service.interfaces;

import java.util.Optional;

import com.openclassrooms.mddapi.models.ERole;
import com.openclassrooms.mddapi.models.Role;

/**
 * L'interface IRoleService est le service pour les rôles.
 */
public interface IRoleService {

    /**
     * Trouver un rôle par son nom
     * @param name
     * @return - Optional<Role>
     */
    Optional<Role> findByName(ERole name);
}

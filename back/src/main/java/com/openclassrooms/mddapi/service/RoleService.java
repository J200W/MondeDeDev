package com.openclassrooms.mddapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.models.ERole;
import com.openclassrooms.mddapi.models.Role;
import com.openclassrooms.mddapi.repository.RoleRepository;
import com.openclassrooms.mddapi.service.interfaces.IRoleService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
/**
 * La classe RoleService est l'implémentation du service pour les rôles.
 */
public class RoleService implements IRoleService {
   
    /**
     * Le repository RoleRepository est utilisé pour accéder aux roles de la base de données
     */
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }
}

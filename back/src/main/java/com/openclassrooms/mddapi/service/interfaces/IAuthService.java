package com.openclassrooms.mddapi.service.interfaces;

import java.util.List;

import com.openclassrooms.mddapi.security.service.UserDetailsImpl;

import jakarta.servlet.http.Cookie;

/**
 * L'interface IAuthService est le service pour l'authentification.
 */
public interface IAuthService {
    
    /**
     * Créer un cookie JWT HttpOnly.
     *
     * @param name le nom du cookie
     * @param value la valeur du cookie (le JWT)
     * @param maxAge la durée de vie du cookie en secondes
     * @return Cookie
     */
    Cookie createCookie(String name, String value, int maxAge);

    /**
     * Récupérer les rôles d'un utilisateur.
     *
     * @param userDetails l'objet contenant les détails de l'utilisateur
     * @return List<String>
     */
    List<String> getRoles(UserDetailsImpl userDetails);

    /**
     * Récupérer l'identifiant d'un utilisateur.
     *
     * @param userDetails l'objet contenant les détails de l'utilisateur
     * @return Integer
     */
    Integer getUserId(UserDetailsImpl userDetails);


}

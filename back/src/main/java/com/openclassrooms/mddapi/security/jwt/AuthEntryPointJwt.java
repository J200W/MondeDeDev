package com.openclassrooms.mddapi.security.jwt;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/*
 * AuthEntryPointJwt est une classe qui implémente l'interface AuthenticationEntryPoint
 * pour personnaliser la réponse pour les erreurs d'authentification
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    // Le logger est utilisé pour afficher des informations sur l'exécution du
    // programme
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    // Cette méthode est appelée chaque fois qu'une exception est lancée en raison
    // d'un accès non autorisé
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
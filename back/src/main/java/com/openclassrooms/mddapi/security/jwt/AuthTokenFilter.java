package com.openclassrooms.mddapi.security.jwt;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.openclassrooms.mddapi.security.service.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
    /*
     * JwtUtils est utilisé pour valider et extraire les informations du jeton JWT
     */
    @Autowired
    private JwtUtils jwtUtils;

    /*
     * UserDetailsServiceImpl est utilisé pour charger les informations de l'utilisateur
     * à partir de la base de données
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /*
     * Le logger est utilisé pour afficher des informations sur l'exécution du programme
     */
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /*
     * Cette méthode est appelée pour chaque requête entrante
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Essayer de valider le jeton JWT
        try {
            // Extraire le jeton JWT de l'en-tête de la requête
            String jwt = parseJwt(request);

            // Si le jeton JWT est valide, extraire les informations de l'utilisateur
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

                // Extraire le nom d'utilisateur de l'utilisateur
                String name = jwtUtils.getUserNameFromJwtToken(jwt);

                // Charger les informations de l'utilisateur à partir de la base de données
                UserDetails userDetails = userDetailsService.loadUserByUsername(name);

                // Créer une instance de UsernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                // Mettre à jour les détails de l'authentification
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Définir l'authentification dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    /*
     * Cette méthode est utilisée pour extraire le jeton JWT de l'en-tête de la requête
     */
    private String parseJwt(HttpServletRequest request) {
        // Extraire l'en-tête Authorization de la requête
        String headerAuth = request.getHeader("Authorization");

        // Si l'en-tête Authorization contient un jeton JWT, le retourner
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}

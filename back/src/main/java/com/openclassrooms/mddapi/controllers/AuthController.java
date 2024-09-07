package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.exception.AlreadyInUseException;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.models.ERole;
import com.openclassrooms.mddapi.models.Role;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;
import com.openclassrooms.mddapi.payload.request.UserRequest;
import com.openclassrooms.mddapi.payload.response.JwtResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.repository.RoleRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.security.service.UserDetailsImpl;
import jakarta.validation.Valid;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * La classe AuthController est utilisée pour gérer l'authentification de
 * l'utilisateur
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    /**
     * AuthenticationManager est utilisé pour l'authentification de l'utilisateur
     */
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * UserRepository est utilisé pour accéder aux utilisateur de la base de données
     */
    @Autowired
    UserRepository userRepository;

    /**
     * RoleRepository est utilisé pour accéder aux roles de la base de données
     */
    @Autowired
    RoleRepository roleRepository;

    /**
     * PasswordEncoder est utilisé pour encoder le mot de passe de l'utilisateur
     */
    @Autowired
    PasswordEncoder encoder;

    /**
     * JwtUtils est utilisé pour générer un token JWT
     */
    @Autowired
    JwtUtils jwtUtils;

    /**
     * Cette méthode est utilisée pour authentifier un utilisateur
     * avec son email ou son nom d'utilisateur et son mot de passe
     */
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/login")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        // Authentification de l'utilisateur avec l'email et le mot de passe
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrUsername(), loginRequest.getPassword()));

        // Mettre l'authentification dans le contexte de sécurité de Spring
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Générer un token JWT
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Créer un cookie HttpOnly contenant le JWT
        Cookie jwtCookie = new Cookie("jwtToken", jwt);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);

        // Ajouter le cookie à la réponse
        response.addCookie(jwtCookie);

        // Récupérer les détails de l'utilisateur authentifié
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Récupérer les rôles de l'utilisateur
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Retourner la réponse contenant le token JWT et les détails de l'utilisateur
        return new JwtResponse(jwt, userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    /**
     * Cette méthode est utilisée pour obtenir les informations de l'utilsateur
     * courant
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/me")
    public User getCurrentUser() {
        try {
            // Récupérer les informations de l'utilisateur courant
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();

            // Récupérer les roles de l'utilisateur
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // Retourner les informations de l'utilisateur courant
            return new User(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des informations de l'utilisateur !");
        }
    }

    /**
     * Cette méthode est utilisée pour mettre à jour les informations de
     * l'utilisateur
     *
     * @param userRequest
     * @return
     */
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/me")
    public MessageResponse updateCurrentUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            // Récupérer les informations de l'utilisateur courant
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();

            Integer userId = userDetails.getId();

            // Vérifier si l'utilisateur existe dans la base de données
            if (!userRepository.existsById(userId)) {
                throw new ResourceNotFoundException("Erreur: Utilisateur non trouvé!");
            }

            // Vérifier si l'email est déjà utilisé par un autre utilisateur
            if (userRepository.findByEmail(userRequest.getEmail()).isPresent() &&
                    !userRepository.findByEmail(userRequest.getEmail()).get().getId().equals(userId)) {
                throw new AlreadyInUseException("Erreur: Email déjà utilisé!");
            }

            // Vérifier si le nom d'utilisateur est déjà utilisé par un autre utilisateur
            if (userRepository.findByUsername(userRequest.getUsername()).isPresent() &&
                    !userRepository.findByUsername(userRequest.getUsername()).get().getId().equals(userId)) {
                throw new RuntimeException("Erreur: Nom d'utilisateur déjà utilisé!");
            }

            // Récupérer l'utilisateur à partir de la base de données
            User user = userRepository.findById(userId).get();

            // Mettre à jour les informations de l'utilisateur
            user.setEmail(userRequest.getEmail());
            user.setUsername(userRequest.getUsername());
            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                user.setPassword(encoder.encode(userRequest.getPassword()));
            }

            // Enregistrer les modifications dans la base de données
            userRepository.save(user);

            // Retourner un message de succès
            return new MessageResponse("Utilisateur mis à jour avec succès!");
        } catch (AlreadyInUseException e) {
            throw new AlreadyInUseException(e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (Exception e) {
            return new MessageResponse(e.getMessage());
        }
    }

    /**
     * Cette méthode est utilisée pour enregistrer un nouvel utilisateur
     */
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/register")
    public JwtResponse registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        try {
            // Vérifier si l'email est déjà utilisé
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                throw new AlreadyInUseException("Erreur: Email déjà utilisé!");
            }

            // Vérifier si le nom d'utilisateur est déjà utilisé
            if (userRepository.existsByUsername(signUpRequest.getName())) {
                throw new AlreadyInUseException("Erreur: Nom d'utilisateur déjà utilisé!");
            }

            // Créer un nouvel utilisateur
            User user = new User(signUpRequest.getName(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));

            // Récupérer les roles de l'utilisateur et les ajouter à l'utilisateur
            Set<String> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet<>();

            // Si les roles ne sont pas spécifiés, attribuer le role USER par défaut
            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new ResourceNotFoundException("Erreur: Le role n'est pas trouvé."));
                roles.add(userRole);
            }

            // Sinon, attribuer les roles spécifiés
            else {
                strRoles.forEach(role -> {
                    if (role.equals("admin")) {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new ResourceNotFoundException("Erreur: Le role n'est pas trouvé."));
                        roles.add(adminRole);
                    } else {
                        if (roleRepository.findByName(ERole.ROLE_USER).isPresent()) {
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
                            roles.add(userRole);
                        }
                    }
                });
            }

            // Enregistrer l'utilisateur dans la base de données avec les roles
            user.setRole(roles);
            userRepository.save(user);

            // Authentification de l'utilisateur avec l'email et le mot de passe
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));

            // Mettre l'authentification dans le contexte de sécurité de Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Récupérer les détails de l'utilisateur authentifié
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Retourner un message de succès
            return new JwtResponse(jwt,
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles.stream().map(item -> item.getName().toString()).collect(Collectors.toList()));

        } catch (AlreadyInUseException e) {
            throw new AlreadyInUseException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Erreur rencontrée lors de l'enregistrement de l'utilisateur !");
        }
    }

    /**
     * Cette méthode est utilisée pour check si l'utilisateur est connecté
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/isLogged")
    public boolean isLogged() {
        try {
            // Récupérer les informations de l'utilisateur courant
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();

            System.out.println(userDetails.getUsername());
            System.out.println(userDetails.getEmail());
            System.out.println(userDetails != null);
            return userDetails != null;
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erreur: Utilisateur non connecté !");
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Erreur rencontrée lors de la vérification de l'utilisateur !");
        }
    }

    /**
     * Cette méthode est utilisée pour déconnecter l'utilisateur
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/logout")
    public MessageResponse logout(HttpServletResponse response) {
        try {
            // Créer un cookie HttpOnly contenant le JWT
            Cookie jwtCookie = new Cookie("jwtToken", null);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(0);

            // Ajouter le cookie à la réponse
            response.addCookie(jwtCookie);

            System.out.println(jwtCookie);

            // Retourner un message de succès
            return new MessageResponse("Déconnexion réussie !");
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Erreur rencontrée lors de la déconnexion de l'utilisateur !");
        }
    }
}

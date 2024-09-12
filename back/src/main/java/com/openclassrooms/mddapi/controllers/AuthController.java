package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.exception.AlreadyInUseException;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.models.ERole;
import com.openclassrooms.mddapi.models.Role;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.LoginRequest;
import com.openclassrooms.mddapi.payload.request.RegisterRequest;
import com.openclassrooms.mddapi.payload.request.UserRequest;
import com.openclassrooms.mddapi.payload.response.AuthResponse;
import com.openclassrooms.mddapi.payload.response.MessageResponse;

import com.openclassrooms.mddapi.security.jwt.JwtUtils;
import com.openclassrooms.mddapi.security.service.UserDetailsImpl;
import com.openclassrooms.mddapi.service.interfaces.IAuthService;
import com.openclassrooms.mddapi.service.interfaces.IRoleService;
import com.openclassrooms.mddapi.service.interfaces.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
     * IAuthService
     */
    @Autowired
    IAuthService authService;

    /**
     * IUserService
     */
    @Autowired
    IUserService userService;

    /**
     * IRoleService
     */
    @Autowired
    IRoleService roleService;

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

    /*
     * Nom du token JWT
     */
    private final String jwtTokenName = "jwtToken";

    /**
     * Cette méthode est utilisée pour authentifier un utilisateur
     * avec son email ou son nom d'utilisateur et son mot de passe
     */
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/login")
    @Operation(summary = "Authentifier un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Le couple identifiant/mot de passe est incorrect", content = {
                    @Content(mediaType = "application/json") }),
    })
    public AuthResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        // Authentification de l'utilisateur avec l'email et le mot de passe
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrUsername(), loginRequest.getPassword()));

        // Mettre l'authentification dans le contexte de sécurité de Spring
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Créer un cookie HttpOnly contenant le JWT
        response.addCookie(authService.createCookie(
                jwtTokenName,
                jwtUtils.generateJwtToken(authentication),
                24 * 60 * 60));

        // Récupérer les détails de l'utilisateur authentifié
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Récupérer les rôles de l'utilisateur
        List<String> roles = authService.getRoles(userDetails);

        // Retourner la réponse contenant le token JWT et les détails de l'utilisateur
        return new AuthResponse(userDetails.getUsername(), userDetails.getEmail(), roles, HttpStatus.OK.value());
    }

    /**
     * Cette méthode est utilisée pour obtenir les informations de l'utilsateur
     * courant
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/me")
    @Operation(summary = "Obtenir les informations de l'utilisateur courant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Erreur lors de la récupération des informations de l'utilisateur !", content = {
                    @Content(mediaType = "application/json") }),
    })
    public UserDto getCurrentUser() {
        try {
            // Récupérer les informations de l'utilisateur courant
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();

            // Récupérer les roles de l'utilisateur
            List<String> roles = authService.getRoles(userDetails);

            // Retourner les informations de l'utilisateur courant
            return new UserDto(userDetails.getEmail(), userDetails.getUsername(), roles);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erreur lors de la récupération des informations de l'utilisateur !");
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
    @Operation(summary = "Mettre à jour les informations de l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Erreur: Email déjà utilisé ou Nom d'utilisateur déjà utilisé !", content = {
                    @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Erreur: Utilisateur non trouvé !", content = {
                    @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Erreur lors de la mise à jour des informations de l'utilisateur !", content = {
                    @Content(mediaType = "application/json") }),
    })
    public MessageResponse updateCurrentUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            // Récupérer les informations de l'utilisateur courant
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();

            Integer userId = userDetails.getId();

            // Vérifier si l'utilisateur existe dans la base de données
            if (!userService.existsById(userId)) {
                throw new ResourceNotFoundException("Erreur: Utilisateur non trouvé!");
            }

            // Vérifier si l'email est déjà utilisé par un autre utilisateur
            if (userService.findByEmail(userRequest.getEmail()).isPresent() &&
                    !userService.findByEmail(userRequest.getEmail()).get().getId().equals(userId)) {
                throw new AlreadyInUseException("Erreur: Email déjà utilisé!");
            }

            // Vérifier si le nom d'utilisateur est déjà utilisé par un autre utilisateur
            if (userService.findByUsername(userRequest.getUsername()).isPresent() &&
                    !userService.findByUsername(userRequest.getUsername()).get().getId().equals(userId)) {
                throw new RuntimeException("Erreur: Nom d'utilisateur déjà utilisé!");
            }

            // Récupérer l'utilisateur à partir de la base de données
            User user = userService.findById(userId).get();

            // Mettre à jour les informations de l'utilisateur
            user.setEmail(userRequest.getEmail());
            user.setUsername(userRequest.getUsername());
            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                user.setPassword(encoder.encode(userRequest.getPassword()));
            }

            // Enregistrer les modifications dans la base de données
            userService.save(user);

            // Retourner un message de succès
            return new MessageResponse("Utilisateur mis à jour avec succès!", HttpStatus.OK.value());
        } catch (AlreadyInUseException e) {
            throw new AlreadyInUseException(e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (Exception e) {
            return new MessageResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * Cette méthode est utilisée pour enregistrer un nouvel utilisateur
     */
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/register")
    @Operation(summary = "Enregistrer un nouvel utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Erreur: Email déjà utilisé ou Nom d'utilisateur déjà utilisé !", content = {
                    @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Erreur lors de l'enregistrement de l'utilisateur !", content = {
                    @Content(mediaType = "application/json") }),
    })
    public AuthResponse registerUser(
            @Valid @RequestBody RegisterRequest signUpRequest,
            HttpServletResponse response) {
        try {
            // Vérifier si l'email est déjà utilisé
            if (userService.existsByEmail(signUpRequest.getEmail())) {
                throw new AlreadyInUseException("Erreur: Email déjà utilisé!");
            }

            // Vérifier si le nom d'utilisateur est déjà utilisé
            if (userService.existsByUsername(signUpRequest.getName())) {
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
                Role userRole = roleService.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new ResourceNotFoundException("Erreur: Le role n'est pas trouvé."));
                roles.add(userRole);
            }

            // Sinon, attribuer les roles spécifiés
            else {
                strRoles.forEach(role -> {
                    if (role.equals("admin")) {
                        Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new ResourceNotFoundException("Erreur: Le role n'est pas trouvé."));
                        roles.add(adminRole);
                    } else {
                        if (roleService.findByName(ERole.ROLE_USER).isPresent()) {
                            Role userRole = roleService.findByName(ERole.ROLE_USER).get();
                            roles.add(userRole);
                        }
                    }
                });
            }

            // Enregistrer l'utilisateur dans la base de données avec les roles
            user.setRole(roles);
            userService.save(user);

            // Authentification de l'utilisateur avec l'email et le mot de passe
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));

            // Mettre l'authentification dans le contexte de sécurité de Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Créer un cookie HttpOnly contenant le JWT
            response.addCookie(authService.createCookie(jwtTokenName, jwt, 24 * 60 * 60));

            // Récupérer les détails de l'utilisateur authentifié
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Retourner un message de succès
            return new AuthResponse(
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles.stream().map(item -> item.getName().toString()).collect(Collectors.toList()),
                    HttpStatus.CREATED.value());

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
    @GetMapping("/is-logged")
    @Operation(summary = "Vérifier si l'utilisateur est connecté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Erreur: Utilisateur non connecté !", content = {
                    @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Erreur: Erreur rencontrée lors de la vérification de l'utilisateur !", content = {
                    @Content(mediaType = "application/json") }),
    })
    public boolean isLogged(HttpServletResponse response) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            return userDetails != null;
        } catch (ResourceNotFoundException e) {
            response.addCookie(authService.createCookie(jwtTokenName, null, 0));
            throw new ResourceNotFoundException("Erreur: Utilisateur non connecté !");
        } catch (Exception e) {
            response.addCookie(authService.createCookie(jwtTokenName, null, 0));
            throw new RuntimeException("Erreur: Erreur rencontrée lors de la vérification de l'utilisateur !");
        }
    }

    /**
     * Cette méthode est utilisée pour déconnecter l'utilisateur
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/logout")
    @Operation(summary = "Déconnecter l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Erreur: Erreur rencontrée lors de la déconnexion de l'utilisateur !", content = {
                    @Content(mediaType = "application/json") }),
    })
    public MessageResponse logout(HttpServletResponse response) {
        try {
            // Créer un cookie HttpOnly contenant le JWT
            response.addCookie(authService.createCookie(jwtTokenName, null, 0));

            // Retourner un message de succès
            return new MessageResponse("Déconnexion réussie !", HttpStatus.OK.value());
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Erreur rencontrée lors de la déconnexion de l'utilisateur !");
        }
    }
}

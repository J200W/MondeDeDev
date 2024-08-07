package com.openclassrooms.mddapi.controllers;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
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
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {

            // Authentification de l'utilisateur avec l'email et le mot de passe
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmailOrUsername(), loginRequest.getPassword()));

            // Mettre l'authentification dans le contexte de sécurité de Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            // Récupérer les détails de l'utilisateur authentifié
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal(); // cast ?

            // Récupérer les roles de l'utilisateur
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            // Retourner le token JWT et les détails de l'utilisateur
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erreur lors de l'authentification de l'utilisateur ! : " + e.getMessage()));
        }
    }

    /**
     * Cette méthode est utilisée pour obtenir les informations de l'utilsateur
     * courant
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            // Récupérer les informations de l'utilisateur courant
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // Récupérer les roles de l'utilisateur
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

            // Retourner les informations de l'utilisateur courant
            return ResponseEntity.ok(new JwtResponse(null, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erreur lors de la récupération des informations de l'utilisateur !"));
        }
    }

    /**
     * Cette méthode est utilisée pour mettre à jour les informations de l'utilisateur
     *
     * @param userRequest
     * @return
     */
    @PutMapping("/me")
    public ResponseEntity<?> updateCurrentUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            // Récupérer les informations de l'utilisateur courant
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // Vérifier si l'utilisateur existe dans la base de données
            if (!userRepository.existsById(userRequest.getId())) {
                throw new RuntimeException("Erreur: Utilisateur non trouvé!");
            }

            // Vérifier si l'utilisateur courant est autorisé à mettre à jour l'utilisateur
            if (!userDetails.getId().equals(userRequest.getId())) {
                throw new RuntimeException("Erreur: Vous n'êtes pas autorisé à mettre à jour cet utilisateur!");
            }

            // Vérifier si l'email est déjà utilisé par un autre utilisateur
            if (userRepository.findByEmail(userRequest.getEmail()).isPresent() &&
                    !userRepository.findByEmail(userRequest.getEmail()).get().getId().equals(userRequest.getId())) {
                throw new RuntimeException("Erreur: Email déjà utilisé!");
            }

            // Récupérer l'utilisateur à partir de la base de données
            User user = userRepository.findById(userRequest.getId()).get();

            // Mettre à jour les informations de l'utilisateur
            user.setEmail(userRequest.getEmail());
            user.setUsername(userRequest.getUsername());
            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                user.setPassword(encoder.encode(userRequest.getPassword()));
            }

            // Enregistrer les modifications dans la base de données
            userRepository.save(user);

            // Retourner un message de succès
            return ResponseEntity.ok(new MessageResponse("Utilisateur mis à jour avec succès!"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Cette méthode est utilisée pour enregistrer un nouvel utilisateur
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {

        try {
            // Vérifier si l'email est déjà utilisé
            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already in use!"));
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
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }

            // Sinon, attribuer les roles spécifiés
            else {
                strRoles.forEach(role -> {
                    if (role.equals("admin")) {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    } else {
                        if (roleRepository.findByName(ERole.ROLE_USER).isPresent()) { // COMMENT
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
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles.stream().map(item -> item.getName().toString()).collect(Collectors.toList())));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur lors de l'enregistrement de l'utilisateur : " + e.getMessage()));
        }
    }
}

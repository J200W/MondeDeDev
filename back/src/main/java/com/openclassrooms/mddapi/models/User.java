package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * La classe User représente un utilisateur.
 */
@Entity
@Data
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Schema(name = "User", description = "La classe User est utilisée pour stocker les informations de l'utilisateur")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "email")
    public String email;

    @Column(name = "username")
    @NonNull
    public String username;

    @Column(name = "password")
    @NonNull
    public String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    @Builder.Default
    private Set<Role> role = new HashSet<>();

    /**
     * Constructeur avec paramètres
     * @param username
     * @param email
     * @param password
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

        /**
     * Constructeur avec paramètres, avec l'identifiant
     * @param id
     * @param username
     * @param email
     */
    public User(Integer id, String username, String email, List<String> role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = new HashSet<>();
        for (String r : role) {
            this.role.add(new Role(ERole.valueOf(r)));
        }
    }
}




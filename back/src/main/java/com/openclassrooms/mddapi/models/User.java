package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * La classe User représente un utilisateur.
 * Elle est caractérisée par :
 * @id
 * L'identifiant de l'utilisateur
 * @email
 * L'adresse email de l'utilisateur
 * @username
 * Le nom d'utilisateur de l'utilisateur
 * @password
 * Le mot de passe de l'utilisateur
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
}




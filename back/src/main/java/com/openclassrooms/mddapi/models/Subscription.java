package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * La classe Subscription représente l'abonnement d'un utilisateur à un sujet de discussion.
 * Elle est caractérisée par :
 * @user
 * L'utilisateur abonné
 * @topic
 * Le sujet de discussion suivi
 */
@Entity
@Table(name = "subscription")
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_topic")
    private Topic topic;
}

package com.openclassrooms.mddapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * La classe Subscription représente l'abonnement d'un utilisateur à un sujet de discussion.
 */
@Entity
@Table(name = "subscription")
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(name = "Subscription", description = "La classe Subscription est utilisée pour stocker les informations de l'abonnement")
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

    public Subscription(User user, Topic topic) {
        this.user = user;
        this.topic = topic;
    }

    public Subscription(Integer userId, Integer topic) {
        this.user = new User();
        this.user.setId(userId);
        this.topic = new Topic();
        this.topic.setId(topic);
    }
}

package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La classe SubscriptionDto est un objet de transfert de données pour les abonnements.
 */
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "SubscriptionDto", description = "La classe SubscriptionDto est un objet de transfert de données pour les abonnements")
public class SubscriptionDto {
    /**
     * L'identifiant de l'abonnement
     */
    @Getter
    @Setter
    private UserNoRoleDto user;

    /**
     * Le thème de l'abonnement
     */
    @Getter
    @Setter
    private TopicDto topic;
}

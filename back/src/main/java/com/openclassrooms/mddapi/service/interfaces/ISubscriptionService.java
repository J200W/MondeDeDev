package com.openclassrooms.mddapi.service.interfaces;

import com.openclassrooms.mddapi.models.Subscription;

import java.util.List;
import java.util.Optional;

/**
 * L'interface ISubscriptionService est le service pour les abonnements.
 */
public interface ISubscriptionService {

    /**
     * Récupérer tous les abonnements par utilisateur
     * @param userId
     * @return - Optional<List<Subscription>>
     */
    Optional<List<Subscription>> findAllSubscriptionByUser(Integer userId);

    /**
     * Récupérer un abonnement par son id
     * @param id
     * @return - Subscription
     */
    Subscription findSubscriptionByUserIdAndPostUrl(Integer userId, String postUrl);

    /**
     * Supprimer un abonnement par son id
     * @param id
     */
    void delete(Integer id);

    /**
     * Supprimer un abonnement par l'id du sujet
     * @param topicId
     */
    void deleteByTopicId(Integer topicId);

    /**
     * Mettre à jour un abonnement
     * @param id
     * @param subscription
     * @return - Subscription
     */
    Subscription update(Integer id, Subscription subscription);

    /**
     * Créer un abonnement
     * @param subscription
     */
    void create(Subscription subscription);

    /**
     * Vérifier si un abonnement existe par l'url du sujet et l'id de l'utilisateur
     * @param topicUrl
     * @param userId
     * @return - Boolean
     */
    Boolean existsByTopicUrlAndUserId(String topicUrl, Integer userId);
}
package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Le repository SubscriptionRepository permet de gérer les abonnements en base de données.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Optional<List<Subscription>> findAllByUser_IdOrderByIdDesc(Integer id);
    void deleteByTopicId(Integer topicId);
    Optional<Subscription> findSubscriptionById(Integer id);
    Boolean existsByTopicIdAndUserId(Integer topicId, Integer userId);
}

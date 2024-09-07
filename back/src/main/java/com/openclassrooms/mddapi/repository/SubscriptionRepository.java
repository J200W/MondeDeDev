package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Optional<List<Subscription>> findAllByUser_IdOrderByIdDesc(Integer id);
    Optional<Subscription> findAllByTopic_id(Integer id);
    void deleteByTopicId(Integer topicId);
    Boolean existsByTopicIdAndUserId(Integer topicId, Integer userId);
}

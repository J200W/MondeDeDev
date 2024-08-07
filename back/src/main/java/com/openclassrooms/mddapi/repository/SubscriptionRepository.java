package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Optional<List<Subscription>> findAllByUser_Id(Integer id);
    Optional<Subscription> findAllByTopic_id(Integer id);
    void deleteByTopicId(Integer topicId);
    Optional<Subscription> findByTopicIdAndUserId(Integer topicId, Integer userId);
}

package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Find all subscriptions of a user
     *
     * @param id
     * @return
     */
    public Optional<List<Subscription>> findAllSubscriptionByUser(Integer id) {
        return this.subscriptionRepository.findAllByUser_IdOrderByIdDesc(id);
    }

    /**
     * Find all subscriptions of a topic
     *
     * @param id
     * @return
     */
    public Subscription findSubscriptionById(Integer id) {
        return this.subscriptionRepository.findAllByTopic_id(id).orElse(null);
    }

    public void delete(Integer id) {
        this.subscriptionRepository.deleteById(id);
    }


    public void deleteByTopicId(Integer topicId) {
        this.subscriptionRepository.deleteByTopicId(topicId);
    }

    public Subscription update(Integer id, Subscription subscription) {
        subscription.setId(id);
        return this.subscriptionRepository.save(subscription);
    }

    public Subscription create(Subscription subscription) {
        return this.subscriptionRepository.save(subscription);
    }

    public Boolean existsByTopicIdAndUserId(Integer topicId, Integer userId) {
        return this.subscriptionRepository.existsByTopicIdAndUserId(topicId, userId);
    }


}
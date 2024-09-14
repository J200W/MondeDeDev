package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.service.interfaces.ISubscriptionService;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
/**
 * La classe SubscriptionService est le service pour les abonnements.
 * @see ISubscriptionService
 */
public class SubscriptionService implements ISubscriptionService {

    /**
     * Le repository SubscriptionRepository
     */
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Optional<List<Subscription>> findAllSubscriptionByUser(Integer userId) {
        return this.subscriptionRepository.findAllByUser_IdOrderByIdDesc(userId);
    }

    @Override
    public Subscription findSubscriptionByUserIdAndPostUrl(Integer userId, String id) {
        return this.subscriptionRepository.findByUser_IdAndTopic_Url(userId, id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        this.subscriptionRepository.deleteById(id);
    }

    @Override
    public void deleteByTopicId(Integer topicId) {
        this.subscriptionRepository.deleteByTopicId(topicId);
    }

    @Override
    public Subscription update(Integer id, Subscription subscription) {
        subscription.setId(id);
        return this.subscriptionRepository.save(subscription);
    }

    @Override
    public void create(Subscription subscription) {
        this.subscriptionRepository.save(subscription);
    }

    @Override
    public Boolean existsByTopicUrlAndUserId(String topicUrl, Integer userId) {
        return this.subscriptionRepository.existsByTopicUrlAndUserId(topicUrl, userId);
    }
}

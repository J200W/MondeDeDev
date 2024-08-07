package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    private final SubscriptionRepository subscriptionRepository;

    public TopicService(TopicRepository topicRepository, SubscriptionRepository subscriptionRepository) {
        this.topicRepository = topicRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public void delete(Integer id) {
        this.topicRepository.deleteById(id);
    }

    public List<Topic> findAll() {
        return this.topicRepository.findAll();
    }

    public Topic findById(Integer id) {
        return this.topicRepository.findById(id).orElse(null);
    }

    public Topic update(Integer id, Topic topic) {
        topic.setId(id);
        return this.topicRepository.save(topic);
    }

    public Topic create(Topic topic) {
        return this.topicRepository.save(topic);
    }

}

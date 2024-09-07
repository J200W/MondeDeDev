package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public void delete(Integer id) {
        this.topicRepository.deleteById(id);
    }

    public List<Topic> findAll() {
        return this.topicRepository.findAllByOrderByIdDesc();
    }

    public Topic findById(Integer id) {
        return this.topicRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Thème non trouvé avec l'id " + id));
    }

    public Topic update(Integer id, Topic topic) {
        topic.setId(id);
        return this.topicRepository.save(topic);
    }

    public Topic create(Topic topic) {
        return this.topicRepository.save(topic);
    }

}

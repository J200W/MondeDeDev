package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.service.interfaces.ITopicService;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Data
/**
 * La classe TopicService est le service pour les thèmes.
 * @see ITopicService
 */
public class TopicService implements ITopicService {

    /**
     * Le repository TopicRepository
     */
    private final TopicRepository topicRepository;

    @Override
    public void delete(Integer id) {
        this.topicRepository.deleteById(id);
    }

    @Override
    public List<Topic> findAll() {
        return this.topicRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Topic findById(Integer id) {
        return this.topicRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Thème non trouvé avec l'id " + id));
    }

    @Override
    public Topic findByTitle(String title) {
        return this.topicRepository.findByTitle(title).orElseThrow(
                () -> new ResourceNotFoundException("Thème non trouvé avec le titre " + title));
    }

    @Override
    public Topic findByUrl(String url) {
        return this.topicRepository.findByUrl(url).orElseThrow(
                () -> new ResourceNotFoundException("Thème non trouvé avec l'url " + url));
    }

    @Override
    public Topic update(Integer id, Topic topic) {
        topic.setId(id);
        return this.topicRepository.save(topic);
    }

    @Override
    public Topic create(Topic topic) {
        return this.topicRepository.save(topic);
    }
}

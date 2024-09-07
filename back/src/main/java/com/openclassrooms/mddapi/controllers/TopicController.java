package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.SubscriptionService;
import com.openclassrooms.mddapi.service.TopicService;
import com.openclassrooms.mddapi.utils.ModelMapperService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * La classe TopicController est utilisée pour gérer les sujets de discussion
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/topic")
@Transactional
public class TopicController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapperService modelMapperService;

    /**
     * Récupérer tous les sujets de discussion
     *
     * @return - List<Topic>
     */
    @GetMapping("/all")
    public List<Topic> getAllTopics() {
        try {
            List<Topic> topics = topicService.findAll();
            return topics;
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de récupérer les sujets de discussion");
        }
    }

    /**
     * Récupérer les sujets non souscrit par un utilisateur
     * 
     * @return - List<Topic>
     */
    @GetMapping("/not-subscribed")
    public List<Topic> getNotFollowedTopics() {
        try {
            List<Topic> topics = topicService.findAll();
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Integer userId = userRepository.findByUsername(userDetails.getUsername()).get().getId();
            topics.removeIf(topic -> subscriptionService.existsByTopicIdAndUserId(topic.getId(), userId));
            return topics;
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de récupérer les sujets de discussion");
        }
    }

    /**
     * Récupérer un sujet de discussion par son identifiant
     *
     * @param id - L'identifiant du sujet de discussion
     * @return - TopicDto
     */
    @GetMapping("/{id}")
    public TopicDto getTopicById(@PathVariable Integer id) {
        try {
            Topic topic = topicService.findById(id);
            TopicDto topicDto = modelMapperService.getModelMapper().map(topic, TopicDto.class);
            return topicDto;
        }
        catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
        catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de récupérer le sujet de discussion");
        }
    }
}

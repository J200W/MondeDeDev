package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.SubscriptionService;
import com.openclassrooms.mddapi.service.interfaces.ITopicService;
import com.openclassrooms.mddapi.utils.ModelMapperService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    /**
     * Injection de ITopicService
     */
    @Autowired
    private ITopicService topicService;

    /**
     * Injection de SubscriptionService
     */
    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Injection de UserRepository
     */
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Injection de ModelMapperService
     */
    @Autowired
    private ModelMapperService modelMapperService;

    /**
     * Récupérer tous les sujets de discussion
     *
     * @return - List<Topic>
     */
    @GetMapping("/all")
    @Operation(summary = "Récupérer tous les sujets de discussion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Les sujets de discussion ont été récupérés"),
            @ApiResponse(responseCode = "400", description = "Impossible de récupérer les sujets de discussion")
    })
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
    @Operation(summary = "Récupérer les sujets non souscrit par un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Les sujets non souscrit ont été récupérés"),
            @ApiResponse(responseCode = "400", description = "Impossible de récupérer les sujets non souscrit")
    })
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
     * @param id - L'identifiant du sujet de discussion
     * @return - TopicDto
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un sujet de discussion par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le sujet de discussion a été récupéré"),
            @ApiResponse(responseCode = "400", description = "Impossible de récupérer le sujet de discussion")
    })
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

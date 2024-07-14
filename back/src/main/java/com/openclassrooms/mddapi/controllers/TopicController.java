package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * La classe TopicController est utilisée pour gérer les sujets de discussion
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /**
     * Récupérer tous les sujets de discussion
     *
     * @return - ResponseEntity
     */
    @GetMapping("/topics")
    public ResponseEntity<List<Topic>> getAllTopics() {
        try {
            List<Topic> topics = topicService.findAll();
            return ResponseEntity.ok(topics);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Récupérer un sujet de discussion par son identifiant
     *
     * @param id - L'identifiant du sujet de discussion
     * @return - ResponseEntity
     */
    @GetMapping("/topics/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Integer id) {
        try {
            Topic topic = topicService.findById(id);
            return ResponseEntity.ok(topic);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Créer un sujet de discussion
     *
     * @param topic - Le sujet de discussion à créer
     * @return - ResponseEntity
     */
    @PostMapping("/topics")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        try {
            Topic newTopic = topicService.create(topic);
            return ResponseEntity.ok(newTopic);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Mettre à jour un sujet de discussion
     *
     * @param id - L'identifiant du sujet de discussion
     * @param topic - Le sujet de discussion à mettre à jour
     * @return - ResponseEntity
     */

    @PutMapping("/topics/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Integer id, @RequestBody Topic topic) {
        try {
            Topic updatedTopic = topicService.update(id, topic);
            return ResponseEntity.ok(updatedTopic);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Supprimer un sujet de discussion
     *
     * @param id - L'identifiant du sujet de discussion
     * @return - ResponseEntity
     */
    @DeleteMapping("/topics/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Integer id) {
        try {
            topicService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * La classe SubscriptionController est utilisée pour gérer les souscriptions
 * des utilisateurs aux sujets de discussion
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);
    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Récupérer toutes les souscriptions d'un utilisateur
     *
     * @return - ResponseEntity
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Subscription>> getAllSubscriptionsByUser(@PathVariable Integer userId) {
        try {
            List<Subscription> subscriptions = subscriptionService.findAllSubscriptionByUser(userId).get();
            return ResponseEntity.ok(subscriptions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Récupérer une souscription par son identifiant
     *
     * @param id - L'identifiant de la souscription
     * @return - ResponseEntity
     */
    @GetMapping("/sub/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Integer id) {
        try {
            Subscription subscription = subscriptionService.findSubscriptionById(id);
            return ResponseEntity.ok(subscription);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Créer une souscription
     *
     * @param subscription - La souscription à créer
     * @return - ResponseEntity
     */
    @PostMapping("/sub")
    public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription subscription) {
        try {
            log.info("Creating subscription: " + subscription);
            Subscription newSubscription = subscriptionService.create(subscription);
            return ResponseEntity.ok(newSubscription);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Supprimer une souscription
     *
     * @param id - L'identifiant de la souscription
     * @return - ResponseEntity
     */
    @DeleteMapping("/sub/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Integer id) {
        try {
            subscriptionService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.exception.AlreadyInUseException;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.payload.response.MessageResponse;
import com.openclassrooms.mddapi.security.service.UserDetailsImpl;
import com.openclassrooms.mddapi.service.SubscriptionService;
import com.openclassrooms.mddapi.service.interfaces.ITopicService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

/**
 * La classe SubscriptionController est utilisée pour gérer les souscriptions
 * des utilisateurs aux sujets de discussion
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    /**
     * Injection de SubscriptionService
     */
    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Injection de ITopicService
     */
    @Autowired
    private ITopicService topicService;

    /**
     * Récupérer toutes les souscriptions d'un utilisateur
     *
     * @return - List<Subscription>
     */
    @GetMapping("/me")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Récupérer tous les abonnements de l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Impossible de récupérer les abonnements")
    })
    public List<Subscription> getAllSubscriptionsByUser() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Integer userId = userDetails.getId();
            List<Subscription> subscriptions = subscriptionService.findAllSubscriptionByUser(userId).get();

            return subscriptions;
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de récupérer les abonnements");
        }
    }

    /**
     * Créer une souscription
     *
     * @param subscription - La souscription à créer
     * @return - MessageResponse
     */
    @PostMapping("/sub/{topic}")
    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "Créer un abonnement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'abonnement a été créé"),
            @ApiResponse(responseCode = "400", description = "Impossible de créer l'abonnement"),
            @ApiResponse(responseCode = "409", description = "L'abonnement existe déjà")
    })
    public MessageResponse createSubscription(@PathVariable Integer topic) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Integer userId = userDetails.getId();
            if (subscriptionService.existsByTopicIdAndUserId(topic, userId)) {
                throw new AlreadyInUseException("Erreur: L'abonnement existe déjà");
            }
            Subscription subscription = new Subscription(
                    userId,
                    topic);
            Topic topicEntity = topicService.findById(topic);
            subscriptionService.create(subscription);
            return new MessageResponse("Vous êtes abonné au sujet: " + topicEntity.getTitle(),
                    HttpStatus.CREATED.value());

        } catch (AlreadyInUseException e) {
            throw new AlreadyInUseException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de créer l'abonnement");
        }
    }

    /**
     * Supprimer une souscription
     * 
     * @param id - L'identifiant de la souscription
     * @return - MessageResponse
     */
    @DeleteMapping("/sub/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Supprimer un abonnement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'abonnement a été supprimé"),
            @ApiResponse(responseCode = "400", description = "Impossible de supprimer l'abonnement"),
            @ApiResponse(responseCode = "404", description = "L'abonnement n'existe pas")
    })
    public MessageResponse deleteSubscription(@PathVariable Integer id) {
        try {
            if (subscriptionService.findSubscriptionById(id) == null) {
                throw new ResourceNotFoundException("Erreur: L'abonnement n'existe pas");
            }
            subscriptionService.delete(id);
            return new MessageResponse("Vous êtes désabonné du sujet", HttpStatus.OK.value());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de supprimer l'abonnement");
        }
    }
}

package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.exception.AlreadyInUseException;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.security.service.UserDetailsImpl;
import com.openclassrooms.mddapi.service.SubscriptionService;
import com.openclassrooms.mddapi.utils.ModelMapperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * La classe SubscriptionController est utilisée pour gérer les souscriptions
 * des utilisateurs aux sujets de discussion
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ModelMapperService modelMapperService;

    /**
     * Récupérer toutes les souscriptions d'un utilisateur
     *
     * @return - List<SubscriptionDto>
     */
    @GetMapping("/me")
    public List<SubscriptionDto> getAllSubscriptionsByUser() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Integer userId = userDetails.getId();
            List<Subscription> subscriptions = subscriptionService.findAllSubscriptionByUser(userId).get();
            List<SubscriptionDto> subscriptionDtos = modelMapperService.convertSubsToSubDtos(subscriptions);

            return subscriptionDtos;
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de récupérer les abonnements");
        }
    }

    /**
     * Récupérer une souscription par son identifiant
     *
     * @param id - L'identifiant de la souscription
     * @return - SubscriptionDto
     */
    @GetMapping("/sub/{id}")
    public SubscriptionDto getSubscriptionById(@PathVariable Integer id) {
        try {
            Subscription subscription = subscriptionService.findSubscriptionById(id);
            SubscriptionDto subscriptionDto = modelMapperService.getModelMapper().map(subscription,
                    SubscriptionDto.class);
            return subscriptionDto;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erreur: Impossible de récupérer l'abonnement");
        }
    }

    /**
     * Créer une souscription
     *
     * @param subscription - La souscription à créer
     * @return - SubscriptionDto
     */
    @PostMapping("/sub/{topic}")
    public SubscriptionDto createSubscription(@PathVariable Integer topic) {
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
            Subscription newSubscription = subscriptionService.create(subscription);
            SubscriptionDto newSubscriptionDto = modelMapperService.getModelMapper().map(newSubscription,
                    SubscriptionDto.class);
            return newSubscriptionDto;
        }
        catch (AlreadyInUseException e) {
            throw new AlreadyInUseException(e.getMessage());
        }
        catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de créer l'abonnement");
        }
    }

    /**
     * Supprimer une souscription
     *
     * @param id - L'identifiant de la souscription
     * @return - void
     */
    @DeleteMapping("/sub/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteSubscription(@PathVariable Integer id) {
        try {
            if (subscriptionService.findSubscriptionById(id) == null) {
                throw new ResourceNotFoundException("Erreur: L'abonnement n'existe pas");
            }
            subscriptionService.delete(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de supprimer l'abonnement");
        }
    }
}

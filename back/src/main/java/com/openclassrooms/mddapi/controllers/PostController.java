package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.exception.AlreadyInUseException;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.security.service.UserDetailsImpl;
import com.openclassrooms.mddapi.service.interfaces.IAuthService;
import com.openclassrooms.mddapi.service.interfaces.IPostService;
import com.openclassrooms.mddapi.service.interfaces.ISubscriptionService;
import com.openclassrooms.mddapi.service.interfaces.ITopicService;
import com.openclassrooms.mddapi.utils.ModelMapperService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * La classe PostController est l'API REST pour les articles.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
@Data
public class PostController {

    /**
     * Injection de IPostService.
     */
    @Autowired
    private IPostService postService;

    /**
     * Injection de ISubscription.
     */
    @Autowired
    private ISubscriptionService subscriptionService;

    /**
     * Injection de IAuthService.
     */
    @Autowired
    private IAuthService authService;

    /*
     * Injection de ITopicService.
     */
    @Autowired
    private ITopicService topicService;

    /**
     * Injection de ModelMapperService.
     */
    @Autowired
    private ModelMapperService modelMapperService;

    /**
     * Récupérer tous les posts correspondants au thème sélectionné en abonnement
     * 
     * @return - List<PostDto>
     */
    @GetMapping("/all")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Récupérer tous les articles correspondants au thème sélectionné en abonnement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Les articles ont été récupérés"),
            @ApiResponse(responseCode = "400", description = "Impossible de récupérer les articles")
    })
    public List<PostDto> getAllPosts() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            Integer userId = authService.getUserId(userDetails);
            List<Post> posts = postService.findAllSubscribedPosts(userId);
            List<PostDto> postsDtos = modelMapperService.convertPostsToPostDto(posts);
            return postsDtos;
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de récupérer les articles");
        }
    }

    /**
     * Récupérer un post par son url
     * 
     * @param url - L'url du post
     * @return - PostDto
     */
    @GetMapping("/{url}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Récupérer un article par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'article a été récupéré"),
            @ApiResponse(responseCode = "400", description = "Impossible de récupérer l'article")
    })
    public PostDto getPostByUrl(@PathVariable String url) {
        try {
            Post post = postService.getByUrl(url);
            PostDto postDto = modelMapperService.getModelMapper().map(post, PostDto.class);
            postDto.setUser(modelMapperService.convertUserToUserDto(post.getUser()));
            return postDto;
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de récupérer l'article");
        }
    }

    /**
     * Créer un post
     * 
     * @param post - Le post à créer
     * @return - PostDto
     */
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/create")
    @Operation(summary = "Créer un article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'article a été créé"),
            @ApiResponse(responseCode = "400", description = "Impossible de créer l'article")
    })
    public PostDto createPost(@Valid @RequestBody Post post) {
        try {
            if (postService.findByTitle(post.getTitle())) {
                throw new AlreadyInUseException("Erreur: Un article avec ce titre existe déjà");
            }
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            post.getUser().setId(authService.getUserId(userDetails));
            post.getTopic().setId(topicService.findByUrl(post.getTopic().getTitle()).getId());
            Post newPost = postService.create(post);
            PostDto newPostDto = modelMapperService.getModelMapper().map(newPost, PostDto.class);
            return newPostDto;
        } catch (AlreadyInUseException e) {
            throw new AlreadyInUseException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de créer l'article");
        }
    }
}

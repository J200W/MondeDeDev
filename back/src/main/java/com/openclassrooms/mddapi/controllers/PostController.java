package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.exception.AlreadyInUseException;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.utils.ModelMapperService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapperService modelMapperService;

    /**
     * Récupérer tous les posts
     * @return - List<PostDto>
     */
    @GetMapping("/all")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Post> getAllPosts() {
        try {
            List<Post> posts = postService.findAll();
            return posts;
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de récupérer les articles");
        }
    }

    /**
     * Récupérer un post par son identifiant
     * @param id - L'identifiant du post
     * @return - PostDto
     */
    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostDto getPostById(@PathVariable Integer id) {
        try {
            Post post = postService.getById(id);
            PostDto postDto = modelMapperService.getModelMapper().map(post, PostDto.class);
            postDto.setUser(modelMapperService.convertUserToUserDto(post.getUser()));
            return postDto;
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de récupérer l'article");
        }
    }

    /**
     * Créer un post
     * @param post - Le post à créer
     * @return - PostDto
     */
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/create")
    public PostDto createPost(@Valid @RequestBody Post post) {
        try {
            if(postService.findByTitle(post.getTitle())) {
                throw new AlreadyInUseException("Erreur: Un article avec ce titre existe déjà");
            }
            Post newPost = postService.create(post);
            PostDto newPostDto = modelMapperService.getModelMapper().map(newPost, PostDto.class);
            return newPostDto;
        }
        catch (AlreadyInUseException e) {
            throw new AlreadyInUseException(e.getMessage());
        } 
        catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible de créer l'article");
        }
    }
}

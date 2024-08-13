package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Récupérer tous les posts
     * @return - ResponseEntity
     */
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        try {
            List<Post> posts = postService.findAll();
            // return only the posts
            posts.forEach(post -> {
                post.getUser().setEmail("");
                post.getUser().setPassword("");
                post.getUser().setRole(null);
            });
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Récupérer un post par son identifiant
     * @param id - L'identifiant du post
     * @return - ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Integer id) {
        try {
            Post post = postService.getById(id);
            post.getUser().setEmail("");
            post.getUser().setPassword("");
            post.getUser().setRole(null);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Créer un post
     * @param post - Le post à créer
     * @return - ResponseEntity
     */
    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        try {
            Post newPost = postService.create(post);
            return ResponseEntity.ok(newPost);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Mettre à jour un post
     * @param post - Le post à mettre à jour
     * @return - ResponseEntity
     */
    @PutMapping("/update")
    public ResponseEntity<Post> updatePost(@RequestBody Post post) {
        try {
            Post updatedPost = postService.update(post.id, post);
            updatedPost.getUser().setEmail("");
            updatedPost.getUser().setPassword("");
            updatedPost.getUser().setRole(null);
            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

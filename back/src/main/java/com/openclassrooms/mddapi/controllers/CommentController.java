package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * La classe CommentController est utilisée pour gérer les commentaires
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * Récupérer tous les commentaires d'un article
     *
     * @param postId - L'identifiant de l'article
     * @return - ResponseEntity
     */
    @GetMapping("/all/{postId}")
    public ResponseEntity<List<Comment>> getAllCommentsByArticle(@PathVariable Integer postId) {
        try {
            List<Comment> comments = commentService.findAllCommentsByArticle(postId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Ajouter un commentaire
     *
     * @param comment - Le commentaire à ajouter
     * @return - ResponseEntity
     */
    @PostMapping("/add")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        try {
            Comment newComment = commentService.create(comment);
            return ResponseEntity.ok(newComment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Supprimer un commentaire
     *
     * @param commentId - L'identifiant du commentaire
     *                  à supprimer
     * @return - ResponseEntity
     */
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId) {
        try {
            commentService.delete(commentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.UserNoRoleDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.service.CommentService;
import com.openclassrooms.mddapi.utils.ModelMapperService;

import jakarta.validation.Valid;

import com.openclassrooms.mddapi.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * La classe CommentController est utilisée pour gérer les commentaires
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapperService modelMapperService;

    /**
     * Récupérer tous les commentaires d'un article
     *
     * @param postId - L'identifiant de l'article
     * @return - List<CommentDto>
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/all/{postId}")
    public List<CommentDto> getAllCommentsByArticle(@PathVariable Integer postId) {
        try {
            List<Comment> comments = commentService.findAllCommentsByArticle(postId);
            List<CommentDto> commentDtos = comments.stream().map(comment -> {
                CommentDto dto = new CommentDto();
                dto.setUser(modelMapperService.getModelMapper().map(comment.getUser(), UserNoRoleDto.class));
                dto.setContent(comment.getContent());
                return dto;
            }).collect(Collectors.toList());

            return commentDtos;
        } catch (Exception e) {
            throw new BadRequestException("Erreur: Impossible de récupérer les commentaires");
        }
    }

    /**
     * Ajouter un commentaire
     *
     * @param comment - Le commentaire à ajouter
     * @return - CommentDto
     */
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/add")
    public CommentDto addComment(@Valid @RequestBody Comment comment) {
        try {
            Comment newComment = commentService.create(comment);
            CommentDto commentDto = modelMapperService.getModelMapper().map(newComment, CommentDto.class);
            return commentDto;
        } catch (Exception e) {
            throw new RuntimeException("Erreur: Impossible d'ajouter le commentaire");
        }
    }

}

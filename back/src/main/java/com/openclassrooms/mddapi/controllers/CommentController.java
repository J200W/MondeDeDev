package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.dto.UserNoRoleDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.service.interfaces.ICommentService;
import com.openclassrooms.mddapi.utils.ModelMapperService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import com.openclassrooms.mddapi.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * La classe CommentController est l'API REST pour les commentaires.
 */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    /**
     * Injection de ICommentService.
     */
    @Autowired
    private ICommentService commentService;

    /**
     * Injection de ModelMapperService.
     */
    @Autowired
    private ModelMapperService modelMapperService;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/all/{postId}")
    @Operation(summary = "Récupérer tous les commentaires d'un article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Les commentaires ont été récupérés"),
            @ApiResponse(responseCode = "400", description = "Impossible de récupérer les commentaires")
    })
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

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/add")
    @Operation(summary = "Ajouter un commentaire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Le commentaire a été ajouté"),
            @ApiResponse(responseCode = "400", description = "Impossible d'ajouter le commentaire")
    })
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

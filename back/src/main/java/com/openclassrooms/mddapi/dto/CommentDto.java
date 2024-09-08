package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.openclassrooms.mddapi.models.Comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La classe CommentDto est un objet de transfert de données pour les commentaires.
 */
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CommentDto", description = "La classe CommentDto est un objet de transfert de données pour les commentaires")
public class CommentDto {
    
    /**
     * L'identifiant du commentaire
     */
    @Getter
    @Setter
    private UserNoRoleDto user;

    /**
     * Le contenu du commentaire
     */
    @Getter
    @Setter
    private String content;

    public LocalDateTime date = LocalDateTime.now();

    public static List<CommentDto> convertToDto(List<Comment> comments, ModelMapper modelMapper) {
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }
}

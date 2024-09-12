package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La classe PostDto est un objet de transfert de données pour les publications.
 */
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "PostDto", description = "La classe PostDto est un objet de transfert de données pour les publications")
public class PostDto {

    /**
     * Le titre de la publication
     */
    @Getter
    @Setter
    public String title;

    /**
     * L'url de la publication
     */
    @Getter
    @Setter
    public String url;

    /**
     * Le contenu de la publication
     */
    @Getter
    @Setter
    public String content;

    /**
     * La date de la publication
     */
    @Getter
    @Setter
    public LocalDateTime date = LocalDateTime.now();

    /**
     * L'utilisateur de la publication
     */
    @Getter
    @Setter
    public UserDto user;

    /**
     * Le thème de la publication
     */
    @Getter
    @Setter
    public TopicDto topic;
}

package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * La classe TopicDto est un objet de transfert de données pour les thèmes.
 */
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "TopicDto", description = "La classe TopicDto est un objet de transfert de données pour les thèmes")
public class TopicDto {

    /**
     * Le titre du thème
     */
    @Getter
    @Setter
    public String title;

    /**
     * L'url du thème
     */
    @Getter
    @Setter
    public String url;

    /**
     * La description du thème
     */
    @Getter
    @Setter
    public String description;
}

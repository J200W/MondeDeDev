package com.openclassrooms.mddapi.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * La classe MessageResponse est un objet de transfert de données pour les messages de réponse.
 */
@Schema(name = "MessageResponse", description = "La classe MessageResponse est un objet de transfert de données pour les messages de réponse")
@AllArgsConstructor
public class MessageResponse {
    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private Integer statusCode;
}
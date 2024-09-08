package com.openclassrooms.mddapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * La classe Topic représente un sujet de discussion.
 */
@Entity
@Table(name = "topic")
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Schema(name = "Topic", description = "La classe Topic est utilisée pour stocker les informations du sujet")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "title")
    @NonNull
    public String title;

    @Column(name = "description")
    @NonNull
    public String description;

}

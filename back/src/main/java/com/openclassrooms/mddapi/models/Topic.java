package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * La classe Topic représente un sujet de discussion.
 * Elle est caractérisée par :
 *
 * @id L'identifiant du sujet
 * @title Le titre du sujet
 * @description La description du sujet
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

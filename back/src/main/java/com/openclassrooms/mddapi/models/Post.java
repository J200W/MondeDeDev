package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * La classe Post représente un post sur un sujet de discussion.
 * Elle est caractérisée par :
 * @id
 * L'identifiant du post
 * @title
 * Le titre du post
 * @content
 * Le contenu du post
 * @date
 * La date de création du post
 * @user
 * L'utilisateur qui a créé le post
 * @topic
 * Le sujet de discussion auquel le post est rattaché
 */
@Entity
@Table(name = "post")
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "title")
    @NonNull
    public String title;

    @Column(name = "content")
    @NonNull
    public String content;

    @Column(name = "date")
    @NonNull
    public LocalDateTime date;

    @ManyToOne
    public User user;

    @ManyToOne
    public Topic topic;
}

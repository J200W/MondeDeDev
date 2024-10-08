package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * La classe Post représente un post sur un sujet de discussion.
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
@Schema(name = "Post", description = "La classe Post est utilisée pour stocker les informations du post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "title")
    @NonNull
    public String title;

    @Column(name = "url")
    @NonNull
    public String url;

    @Column(name = "content")
    @NonNull
    public String content;

    @Column(name = "date")
    @Builder.Default
    public LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_user")
    public User user;

    @ManyToOne
    @JoinColumn(name = "id_topic")
    public Topic topic;
}

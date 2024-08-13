package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_post")
    private Post post;

    @Column(name = "content")
    @NonNull
    private String content;

    @Column(name = "date")
    @Builder.Default
    public LocalDateTime date = LocalDateTime.now();
}

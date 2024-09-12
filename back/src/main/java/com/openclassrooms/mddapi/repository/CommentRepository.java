package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
 * Le repository CommentRepository permet de gérer les commentaires en base de données.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Optional<List<Comment>> findAllByPost_url(String postUrl);
}

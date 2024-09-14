package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Le repository PostRepository permet de gérer les posts en base de données.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByOrderByDateDesc();
    void deleteByTopicId(Integer topicId);
    Optional<Post> findByTitle(String title);
    Optional<Post> findByUrl(String url);
    List<Post> findAllByTopicIdInOrderByIdDesc(List<Integer> topicIds);
}

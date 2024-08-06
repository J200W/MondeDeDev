package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findByTitle(String title);
    List<Post> findAllByOrderByDateDesc();
    Boolean existsByTitle(String title);
    void deleteByTopicId(Integer topicId);
}

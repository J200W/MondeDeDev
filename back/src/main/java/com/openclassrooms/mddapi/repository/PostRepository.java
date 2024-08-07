package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByOrderByDateDesc();
    void deleteByTopicId(Integer topicId);
}

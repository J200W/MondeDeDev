package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Le repository TopicRepository permet de gérer les topics en base de données.
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    List<Topic> findAllByOrderByIdDesc();
}

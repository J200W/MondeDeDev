package com.openclassrooms.mddapi.service.interfaces;

import com.openclassrooms.mddapi.models.Post;
import java.util.List;

/**
 * L'interface IPostService est le service pour les articles.
 */
public interface IPostService {

    /**
     * Récupérer tous les posts
     * @return - List<Post>
     */
    List<Post> findAll();

    /**
     * Récupérer un post par son id
     * @param id
     * @return - Post
     */
    Post getById(Integer id);

    /**
     * Mettre à jour un post
     * @param id
     * @param post
     * @return - Post
     */
    Post update(Integer id, Post post);

    /**
     * Créer un post
     * @param post
     * @return - Post
     */
    Post create(Post post);

    /**
     * Supprimer un post par son id
     * @param topicId
     */
    void deleteByTopicId(Integer topicId);

    /**
     * Vérifier si un post existe par son titre
     * @param title
     * @return - boolean
     */
    boolean findByTitle(String title);
}
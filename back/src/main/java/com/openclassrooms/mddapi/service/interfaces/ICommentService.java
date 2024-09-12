package com.openclassrooms.mddapi.service.interfaces;

import com.openclassrooms.mddapi.models.Comment;

import java.util.List;

/**
 * L'interface ICommentService est le service pour les commentaires.
 */
public interface ICommentService {

    /**
     * Récupérer tous les commentaires par article
     * @param id
     */
    void delete(Integer id);

    /**
     * Récupérer tous les commentaires par article
     * @param postUrl
     * @return - List<Comment>
     */
    List<Comment> findAllCommentsByArticle(String postUrl);

    /**
     * Récupérer un commentaire par son id
     * @param id
     * @return - Comment
     */
    Comment getById(Integer id);

    /**
     * Mettre à jour un commentaire
     * @param comment
     * @param content
     * @return - Comment
     */
    Comment update(Comment comment, String content);

    /**
     * Créer un commentaire
     * @param comment
     * @return - Comment
     */
    Comment create(Comment comment);
}

package com.openclassrooms.mddapi.service.interfaces;

import com.openclassrooms.mddapi.models.Topic;
import java.util.List;

/**
 * L'interface ITopicService est le service pour les thèmes.
 */
public interface ITopicService {

    /**
     * Supprimer un thème par son id
     * @param id
     */
    void delete(Integer id);

    /**
     * Récupérer tous les thèmes
     * @return - List<Topic>
     */
    List<Topic> findAll();

    /**
     * Récupérer un thème par son id
     * @param id
     * @return - Topic
     */
    Topic findById(Integer id);

    /**
     * Trouver un thème par son titre
     * @param title
     * @return - Topic
     */

     Topic findByTitle(String title);

     /**
      * Trouver un thème par son url
      * @param url
      * @return
      */
     Topic findByUrl(String url);

     /**
     * Mettre à jour un thème
     * @param id
     * @param topic
     * @return - Topic
     */
    Topic update(Integer id, Topic topic);

    /**
     * Créer un thème
     * @param topic
     * @return - Topic
     */
    Topic create(Topic topic);
}

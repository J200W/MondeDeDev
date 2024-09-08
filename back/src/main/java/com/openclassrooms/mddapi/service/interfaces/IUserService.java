package com.openclassrooms.mddapi.service.interfaces;

import com.openclassrooms.mddapi.models.User;

import java.util.List;

/**
 * L'interface IUserService est le service pour les utilisateurs.
 */
public interface IUserService {

    /**
     * Supprimer un utilisateur par son id
     * @param id
     */
    void delete(Integer id);

    /**
     * Récupérer tous les utilisateurs
     * @return - List<User>
     */
    List<User> findAll();

    /**
     * Récupérer un utilisateur par son id
     * @param id
     * @return - User
     */
    User getById(Integer id);

    /**
     * Mettre à jour un utilisateur
     * @param id
     * @param user
     * @return - User
     */
    User update(Integer id, User user);

    /**
     * Créer un utilisateur
     * @param user
     * @return - User
     */
    User create(User user);

    /**
     * Récupérer un utilisateur par son email
     * @param email
     * @return - User
     */
    User getByEmail(String email);
}

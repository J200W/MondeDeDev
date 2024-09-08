package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.interfaces.IUserService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
/**
 * La classe UserService est le service pour les utilisateurs.
 * @see IUserService
 */
public class UserService implements IUserService {

    /**
     * Le repository UserRepository
     */
    private final UserRepository userRepository;

    @Override
    public void delete(Integer id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User getById(Integer id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User update(Integer id, User user) {
        user.setId(id);
        return this.userRepository.save(user);
    }

    @Override
    public User create(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }
}

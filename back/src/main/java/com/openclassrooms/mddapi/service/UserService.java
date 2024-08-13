package com.openclassrooms.mddapi.service;


import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void delete(Integer id) {
        this.userRepository.deleteById(id);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User getById(Integer id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public User update(Integer id, User user) {
        user.setId(id);
        return this.userRepository.save(user);
    }

    public User create(User user) {
        return this.userRepository.save(user);
    }

    public User getByEmail(String email) {
        return this.userRepository.findByEmail(email).get();
    }
}

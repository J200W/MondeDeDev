package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void delete(Integer id) {
        this.postRepository.deleteById(id);
    }

    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    public Post getById(Integer id){
        return this.postRepository.findById(id).orElse(null);
    }

    public Post update(Integer id, Post post) {
        post.setId(id);
        return this.postRepository.save(post);
    }

    public Post create(Post post) {
        return this.postRepository.save(post);
    }
}

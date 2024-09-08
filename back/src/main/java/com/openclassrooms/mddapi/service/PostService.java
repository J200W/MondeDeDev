package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.service.interfaces.IPostService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
/**
 * La classe PostService est le service pour les posts.
 * @see IPostService
 */
public class PostService implements IPostService {

    /**
     * Le repository PostRepository
     */
    private final PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return this.postRepository.findAllByOrderByDateDesc();
    }

    @Override
    public Post getById(Integer id) {
        return this.postRepository.findById(id).orElse(null);
    }

    @Override
    public Post update(Integer id, Post post) {
        post.setId(id);
        return this.postRepository.save(post);
    }

    @Override
    public Post create(Post post) {
        return this.postRepository.save(post);
    }

    @Override
    public void deleteByTopicId(Integer topicId) {
        this.postRepository.deleteByTopicId(topicId);
    }

    @Override
    public boolean findByTitle(String title) {
        return this.postRepository.findByTitle(title).isPresent();
    }
}

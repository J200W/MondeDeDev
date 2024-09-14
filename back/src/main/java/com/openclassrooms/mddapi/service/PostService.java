package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.service.interfaces.IPostService;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
/**
 * La classe PostService est le service pour les posts.
 * 
 * @see IPostService
 */
public class PostService implements IPostService {

    /**
     * Le repository PostRepository
     */
    private final PostRepository postRepository;

    /**
     * Le repository SubscriptionRepository
     */
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public List<Post> findAllSubscribedPosts(Integer userId) {
        Optional<List<Subscription>> subscriptionList = this.subscriptionRepository
                .findAllByUser_IdOrderByIdDesc(userId);
        List<Integer> topicIds = subscriptionList.get().stream().map(Subscription::getTopic).map(topic -> topic.getId())
                .toList();
        return this.postRepository.findAllByTopicIdInOrderByIdDesc(topicIds);
    }

    @Override
    public Post getByUrl(String id) {
        return this.postRepository.findByUrl(id).orElse(null);
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

package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.service.interfaces.ICommentService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
/**
 * La classe CommentService est le service pour les commentaires.
 * @see ICommentService
 */
public class CommentService implements ICommentService {
    
    /**
     * Le repository CommentRepository
     */
    private final CommentRepository commentRepository;

    @Override
    public void delete(Integer id) {
        this.commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> findAllCommentsByArticle(String postUrl) {
        return this.commentRepository.findAllByPost_url(postUrl).get();
    }

    @Override
    public Comment getById(Integer id) {
        return this.commentRepository.findById(id).orElse(null);
    }

    @Override
    public Comment update(Comment comment, String content) {
        comment.setContent(content);
        return this.commentRepository.save(comment);
    }

    @Override
    public Comment create(Comment comment) {
        return this.commentRepository.save(comment);
    }
}

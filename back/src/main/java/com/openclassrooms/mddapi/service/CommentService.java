package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void delete(Integer id) {

        this.commentRepository.deleteById(id);
    }

    public List<Comment> findAllCommentsByArticle(Integer postId) {
        return this.commentRepository.findAllByPost_id(postId).get();
    }

    public Comment getById(Integer id) {
        return this.commentRepository.findById(id).orElse(null);
    }

    public Comment update(Comment comment, String content) {
        comment.setContent(content);
        return this.commentRepository.save(comment);
    }

    public Comment create(Comment comment) {
        return this.commentRepository.save(comment);
    }
}

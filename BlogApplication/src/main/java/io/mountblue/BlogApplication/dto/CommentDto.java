package io.mountblue.BlogApplication.dto;

import io.mountblue.BlogApplication.entity.Comment;
import io.mountblue.BlogApplication.entity.Post;
import io.mountblue.BlogApplication.repositery.CommentRepository;
import io.mountblue.BlogApplication.repositery.PostRepositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Component
public class CommentDto {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepositery postRepositery;


    public Comment addComment(Long postId, Comment comment) {
        Optional<Post> postOpt = postRepositery.findById(postId);

        if (postOpt.isEmpty()) {
            return null;
        }

        Post post = postOpt.get();
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    // List all comments for a post
    public Set<Comment> getListComments(Long postId) {
        Optional<Post> postOpt = postRepositery.findById(postId);

        if (postOpt.isEmpty()) {
            return null;
        }

        Post post = postOpt.get();
        return post.getComments();
    }

    public Comment updateComment(Long id, Comment comment) {
        if (id == null || comment == null) {
            return null;
        }

        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty()) {
            return null;
        }

        Comment existingComment = commentOpt.get();

        existingComment.setName(
                comment.getName() != null ? comment.getName() : existingComment.getName()
        );
        existingComment.setEmail(
                comment.getEmail() != null ? comment.getEmail() : existingComment.getEmail()
        );
        existingComment.setComment(
                comment.getComment() != null ? comment.getComment() : existingComment.getComment()
        );

        existingComment.setUpdatedAt(LocalDateTime.now());

        return commentRepository.save(existingComment);
    }


    public Comment deleteComment(Long id) {
        if(id == null){
            return null;
        }

        Optional<Comment> optionalComment = commentRepository.findById(id);

        if(optionalComment.isEmpty()){
            return null;
        }

        Comment delComment = optionalComment.get();
        commentRepository.delete(delComment);

        return delComment;
    }
}

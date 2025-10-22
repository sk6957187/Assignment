package io.mountblue.BlogApplication.service;

import io.mountblue.BlogApplication.dao.CommentDao;
import io.mountblue.BlogApplication.entity.Comment;
import io.mountblue.BlogApplication.entity.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    public ResponseEntity<ResponseStructure<Set<Comment>>> getListComments(Long postId) {
        ResponseStructure<Set<Comment>> resp = new ResponseStructure<>();
        Set<Comment> comments = commentDao.getListComments(postId);

        if (comments == null || comments.isEmpty()) {
            resp.setStatus(HttpStatus.NOT_FOUND.value());
            resp.setMessage("No comments found for this post.");
            resp.setData(null);
        } else {
            resp.setStatus(HttpStatus.OK.value());
            resp.setData(comments);
            resp.setMessage("Comments is founded.");
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Comment>> addComment(Long postId, Comment comment) {
        ResponseStructure<Comment> resp = new ResponseStructure<>();

        Comment savedComment = commentDao.addComment(postId, comment);
        if (savedComment == null) {
            resp.setStatus(HttpStatus.NOT_FOUND.value());
            resp.setMessage("Post not found.");
            resp.setData(null);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }

        resp.setStatus(HttpStatus.CREATED.value());
        resp.setMessage("Comment added successfully.");
        resp.setData(savedComment);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Comment>> updateComment(Long id, Comment comment) {
        ResponseStructure<Comment> resp = new ResponseStructure<>();
        Comment updatedComment = commentDao.updateComment(id, comment);

        if(updatedComment == null){
            resp.setMessage("Comment is not found.");
            resp.setData(null);
            resp.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            resp.setStatus(HttpStatus.CREATED.value());
            resp.setData(updatedComment);
            resp.setMessage("Comment is updated.");
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<Comment>> deleteComment(Long id) {
        ResponseStructure<Comment> resp = new ResponseStructure<>();
        Comment delComment = commentDao.deleteComment(id);

        if(delComment == null){
            resp.setMessage("Comment not found.");
            resp.setData(null);
            resp.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            resp.setStatus(HttpStatus.OK.value());
            resp.setMessage("Comment success fully deleted.");
            resp.setData(delComment);
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}



package io.mountblue.BlogApplication.controller;

import io.mountblue.BlogApplication.entity.Comment;
import io.mountblue.BlogApplication.entity.ResponseStructure;
import io.mountblue.BlogApplication.service.CommentService;
import io.mountblue.BlogApplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<ResponseStructure<Set<Comment>>> listComments(@PathVariable Long postId) {
        return commentService.getListComments(postId);
    }

    @PostMapping("/post/add/{postId}")
    public ResponseEntity<ResponseStructure<Comment>> addComment(@PathVariable Long postId, @RequestBody Comment comment) {
        return commentService.addComment(postId, comment);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseStructure<Comment>> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        return commentService.updateComment(id, comment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseStructure<Comment>> deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }
}


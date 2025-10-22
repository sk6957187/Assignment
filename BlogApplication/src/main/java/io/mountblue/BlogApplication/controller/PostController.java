package io.mountblue.BlogApplication.controller;

import io.mountblue.BlogApplication.entity.Post;
import io.mountblue.BlogApplication.entity.ResponseStructure;
import io.mountblue.BlogApplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService service;

    @PostMapping
    public ResponseEntity<ResponseStructure<Post>> savePost(@RequestBody Post post){
        System.err.println(post);
        return service.savePost(post);
    }

    @PutMapping
    public ResponseEntity<ResponseStructure<Post>> updatePost(@RequestBody Post post){
        return service.updatePost(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<Post>> postDeleted(@PathVariable Long id){
        return service.deletePost(id);
    }

}

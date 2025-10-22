package io.mountblue.BlogApplication.service;

import io.mountblue.BlogApplication.dao.PostDao;
import io.mountblue.BlogApplication.entity.Post;
import io.mountblue.BlogApplication.entity.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostDao postDao;

    public ResponseEntity<ResponseStructure<List<Post>>> findAll() {
        ResponseStructure<List<Post>> resp = new ResponseStructure<>();
        List<Post> postList = postDao.getAllPost();

        if(postList == null || postList.isEmpty()){
            resp.setData(null);
            resp.setStatus(HttpStatus.BAD_REQUEST.value());
            resp.setMessage("Data not found.");
        } else {
            resp.setStatus(HttpStatus.OK.value());
            resp.setMessage("Data found.");
            resp.setData(postList);
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    // Pagination support
    public ResponseEntity<ResponseStructure<List<Post>>> getPostsWithPagination(int start, int limit) {
        ResponseStructure<List<Post>> resp = new ResponseStructure<>();
        List<Post> allPosts = postDao.getAllPost();;
        int total = allPosts.size();

        if (allPosts.isEmpty() || start > total) {
            resp.setData(null);
            resp.setStatus(HttpStatus.NO_CONTENT.value());
            resp.setMessage("No posts available.");
            return new ResponseEntity<>(resp,HttpStatus.OK);
        }

        int fromIndex = Math.max(start - 1, 0);
        int toIndex = Math.min(fromIndex + limit, total);
        List<Post> pagePosts = allPosts.subList(fromIndex, toIndex);


        resp.setData(pagePosts);
        resp.setStatus(HttpStatus.OK.value());
        resp.setMessage("Posts retrieved successfully.");
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    // Save a post
    public ResponseEntity<ResponseStructure<Post>> savePost(Post post) {
        ResponseStructure<Post> resp = new ResponseStructure<>();
        post = postDao.savePost(post);

        if (post == null) {
            resp.setData(null);
            resp.setStatus(HttpStatus.BAD_REQUEST.value());
            resp.setMessage("Post not created.");
        } else {
            resp.setData(post);
            resp.setStatus(HttpStatus.OK.value());
            resp.setMessage("Post created successfully.");
        }
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    // Update a post
    public ResponseEntity<ResponseStructure<Post>> updatePost(Post post) {
        ResponseStructure<Post> resp = new ResponseStructure<>();
        post = postDao.updatePost(post);

        if (post == null) {
            resp.setData(null);
            resp.setStatus(HttpStatus.BAD_REQUEST.value());
            resp.setMessage("Post not updated.");
        } else {
            resp.setData(post);
            resp.setStatus(HttpStatus.OK.value());
            resp.setMessage("Post updated successfully.");
        }
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    // Delete a post
    public ResponseEntity<ResponseStructure<Post>> deletePost(Long id) {
        ResponseStructure<Post> resp = new ResponseStructure<>();
        Post deletedPost = postDao.deletePost(id);

        if (deletedPost == null) {
            resp.setData(null);
            resp.setStatus(HttpStatus.BAD_REQUEST.value());
            resp.setMessage("Post not available.");
        } else {
            resp.setData(deletedPost);
            resp.setStatus(HttpStatus.OK.value());
            resp.setMessage("Post deleted successfully.");
        }
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    // Get post by ID
    public ResponseEntity<ResponseStructure<Post>> getPostById(Long id) {
        ResponseStructure<Post> resp = new ResponseStructure<>();
        Post post = postDao.getPostById(id);

        if (post == null) {
            resp.setData(null);
            resp.setStatus(HttpStatus.BAD_REQUEST.value());
            resp.setMessage("Post not available.");
        } else {
            resp.setData(post);
            resp.setStatus(HttpStatus.OK.value());
            resp.setMessage("Post retrieved successfully.");
        }
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }
}

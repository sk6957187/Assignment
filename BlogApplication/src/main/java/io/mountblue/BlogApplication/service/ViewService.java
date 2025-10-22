package io.mountblue.BlogApplication.service;

import io.mountblue.BlogApplication.dao.PostDao;
import io.mountblue.BlogApplication.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

@Service
public class ViewService {

    @Autowired
    private PostDao postDao;

    public Post getPostById(Long id) {
        return postDao.getPostById(id);
    }

    public void updatePost(Post updatedPost) {
        postDao.updatePost(updatedPost);
    }
}

package io.mountblue.BlogApplication.dao;

import io.mountblue.BlogApplication.entity.Post;
import io.mountblue.BlogApplication.entity.Tag;
import io.mountblue.BlogApplication.repositery.PostRepositery;
import io.mountblue.BlogApplication.repositery.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class PostDao {

    @Autowired
    private PostRepositery postRepositery;

    @Autowired
    private TagRepository tagRepository;


    public List<Post> getAllPost() {
        return postRepositery.findAll();
    }

    public Post savePost(Post post) {
        if (post == null) {
            return null;
        }

        Set<Tag> tagSet = new HashSet<>();
        for (Tag t : post.getTags()) {
            String tName = t.getName().trim();

            Optional<Tag> tagOpt = tagRepository.findByNameIgnoreCase(tName);

            Tag tag;
            if (tagOpt.isPresent()) {
                tag = tagOpt.get();
            } else {
                tag = new Tag(tName);
                tag = tagRepository.save(tag);
            }

            tagSet.add(tag);
        }
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setTags(tagSet);

        return postRepositery.save(post);
    }


    public Post updatePost(Post post) {
        if (post.getId() == null) {
            return null;
        }

        Post existingPost = postRepositery.findById(post.getId()).orElse(null);
        if (existingPost == null) {
            return null;
        }

        if (post.getTitle() != null) {
            existingPost.setTitle(post.getTitle());
        }
        if (post.getExcerpt() != null){
            existingPost.setExcerpt(post.getExcerpt());
        }
        if (post.getContent() != null){
            existingPost.setContent(post.getContent());
        }
        if (post.getAuthor() != null){
            existingPost.setAuthor(post.getAuthor());
        }
        if (post.getPublishedAt() != null) {
            existingPost.setPublishedAt(post.getPublishedAt());
        }
        existingPost.setPublished(post.isPublished());
        existingPost.setUpdatedAt(LocalDateTime.now());

        if (post.getTags() != null && !post.getTags().isEmpty()) {
            Set<Tag> tagSet = new HashSet<>();
            for (Tag t : post.getTags()) {
                String tName = t.getName().trim();
                Tag tag = tagRepository.findByNameIgnoreCase(tName)
                        .orElseGet(() -> tagRepository.save(new Tag(tName)));
                tagSet.add(tag);
            }
            existingPost.setTags(tagSet);
        }

        return postRepositery.save(existingPost);
    }


    public Post deletePost(Long id) {
        Optional<Post> postOpt = postRepositery.findById(id);

        if (postOpt.isEmpty()) {
            return null;
        }

        Post post = postOpt.get();
        postRepositery.delete(post);

        Post deletedPost = new Post();
        deletedPost.setId(post.getId());
        deletedPost.setTitle(post.getTitle());
        deletedPost.setExcerpt(post.getExcerpt());
        return deletedPost;
    }


    public Post getPostById(Long id) {
        Optional<Post> post = postRepositery.findById(id);
        return post.orElse(null);
    }

    public List<Post> sortPosts(List<Post> posts, String sortField, String order) {
        Comparator<Post> comparator;

        switch (sortField) {
            case "publishedAt" -> comparator = Comparator.comparing(Post::getPublishedAt);
            case "title" -> comparator = Comparator.comparing(Post::getTitle, String.CASE_INSENSITIVE_ORDER);
            case "author" -> comparator = Comparator.comparing(p -> p.getAuthor() != null ? p.getAuthor().getName() : "");
            default -> comparator = Comparator.comparing(Post::getId);
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        posts.sort(comparator);
        return posts;
    }
}

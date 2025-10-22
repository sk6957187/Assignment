package io.mountblue.BlogApplication.controller;

import io.mountblue.BlogApplication.dao.PostDao;
import io.mountblue.BlogApplication.entity.Post;
import io.mountblue.BlogApplication.entity.Tag;
import io.mountblue.BlogApplication.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/")
public class ViewController {

    @Autowired
    private PostDao postDao;

    @Autowired
    private ViewService viewService;

    @GetMapping()
    public String getPosts(
            @RequestParam(defaultValue = "1") int start,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String sortField,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(required = false) String search,
            Model model
    ) {

        List<Post> allPosts = postDao.getAllPost();
        List<Post> filteredPosts = new ArrayList<>();

        // Prepare tag list for filtering
        List<String> normalizedTags = new ArrayList<>();
        if (tagName != null && !tagName.trim().isEmpty()) {
            for (String t : tagName.split(",")) {
                normalizedTags.add(t.trim().toLowerCase());
            }
        }

        for (Post p : allPosts) {
            boolean authorMatch = true;
            boolean tagMatch = true;
            boolean searchMatch = true;

            // Author filter
            if (authorId != null) {
                authorMatch = p.getAuthor() != null && authorId.equals(p.getAuthor().getId());
            }

            // Tag filter (ignore case)
            if (!normalizedTags.isEmpty()) {
                tagMatch = false;
                if (p.getTags() != null) {
                    for (Tag t : p.getTags()) {
                        if (t.getName() != null && normalizedTags.contains(t.getName().trim().toLowerCase())) {
                            tagMatch = true;
                            break;
                        }
                    }
                }
            }

            // Search filter (ignore case)
            if (search != null && !search.trim().isEmpty()) {
                String lowerSearch = search.trim().toLowerCase();
                searchMatch = (p.getTitle() != null && p.getTitle().toLowerCase().contains(lowerSearch)) ||
                        (p.getContent() != null && p.getContent().toLowerCase().contains(lowerSearch)) ||
                        (p.getExcerpt() != null && p.getExcerpt().toLowerCase().contains(lowerSearch)) ||
                        (p.getAuthor() != null && p.getAuthor().getName() != null &&
                                p.getAuthor().getName().toLowerCase().contains(lowerSearch)) ||
                        (p.getTags() != null && p.getTags().stream().anyMatch(tag ->
                                tag.getName() != null && tag.getName().toLowerCase().contains(lowerSearch)));
            }

            if (authorMatch && tagMatch && searchMatch) {
                filteredPosts.add(p);
            }
        }

        // Sorting
        if (sortField != null && !sortField.isEmpty()) {
            postDao.sortPosts(filteredPosts, sortField, order);
        }

        // Pagination
        int total = filteredPosts.size();
        int fromIndex = Math.max(start - 1, 0);
        int toIndex = Math.min(fromIndex + limit, total);
        List<Post> pagePosts = (fromIndex < total) ? filteredPosts.subList(fromIndex, toIndex) : Collections.emptyList();

        model.addAttribute("posts", pagePosts);
        model.addAttribute("start", start);
        model.addAttribute("limit", limit);
        model.addAttribute("total", total);
        model.addAttribute("authorId", authorId);
        model.addAttribute("tagName", tagName != null ? Arrays.asList(tagName.split(",")) : null);
        model.addAttribute("sortField", sortField);
        model.addAttribute("order", order);
        model.addAttribute("search", search);

        return "index";
    }

    @GetMapping("/post/{id}")
    public String postPage(@PathVariable Long id, Model model) {
        Post post = viewService.getPostById(id);
        model.addAttribute("post", post);
        return "blogPage";
    }

    @GetMapping("/newblog")
    public String addPostPage() { return "newBlog"; }

    @GetMapping("/login")
    public String login() { return "login"; }

    @GetMapping("/register")
    public String registration() { return "register"; }
}

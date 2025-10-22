package io.mountblue.BlogApplication.dao;

import io.mountblue.BlogApplication.entity.Tag;
import io.mountblue.BlogApplication.repositery.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TagDao {
    @Autowired
    private TagRepository tagRepository;

    public Tag saveTag(Tag tag) {
        Optional<Tag> existingTag = tagRepository.findByNameIgnoreCase(tag.getName());
        if (existingTag.isPresent()) {
            return existingTag.get();
        }
        return tagRepository.save(tag);
    }

    public List<Tag> getAllTag() {
        return tagRepository.findAll();
    }
}

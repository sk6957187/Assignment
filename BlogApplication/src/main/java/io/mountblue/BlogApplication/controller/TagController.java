package io.mountblue.BlogApplication.controller;

import io.mountblue.BlogApplication.entity.ResponseStructure;
import io.mountblue.BlogApplication.entity.Tag;
import io.mountblue.BlogApplication.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping
    public ResponseEntity<ResponseStructure<Tag>> saveTag(@RequestBody Tag tag){
        return tagService.saveTag(tag);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Tag>>> getAllTag(){
        return tagService.getAllTag();
    }
}

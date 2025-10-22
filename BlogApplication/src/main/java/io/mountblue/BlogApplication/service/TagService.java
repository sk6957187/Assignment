package io.mountblue.BlogApplication.service;

import io.mountblue.BlogApplication.dao.TagDao;
import io.mountblue.BlogApplication.entity.ResponseStructure;
import io.mountblue.BlogApplication.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagDao tagDao;

    public ResponseEntity<ResponseStructure<Tag>> saveTag(Tag tag) {
        ResponseStructure<Tag> resp = new ResponseStructure<>();
        tag = tagDao.saveTag(tag);
        if(tag == null){
            resp.setMessage("Tag not save");
            resp.setStatus(HttpStatus.BAD_REQUEST.value());
            resp.setData(null);
        } else {
            resp.setStatus(HttpStatus.CREATED.value());
            resp.setData(tag);
            resp.setMessage("Tag is created.");
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<List<Tag>>> getAllTag() {
        ResponseStructure<List<Tag>> resp = new ResponseStructure<>();
        List<Tag> tagList = tagDao.getAllTag();

        if(tagList != null && !tagList.isEmpty()) {
            resp.setStatus(HttpStatus.OK.value());
            resp.setMessage("Tags fetched successfully.");
            resp.setData(tagList);
        } else {
            resp.setStatus(HttpStatus.NO_CONTENT.value());
            resp.setMessage("No tags found.");
            resp.setData(null);
        }

        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

}

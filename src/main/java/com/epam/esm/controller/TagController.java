package com.epam.esm.controller;

import com.epam.esm.domain.tag.Tag;
import com.epam.esm.dto.BaseResponseDTO;
import com.epam.esm.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?> create(@RequestBody Tag tag) {
        BaseResponseDTO<Tag> dto = tagService.create(tag);
        return ResponseEntity
                .status(dto.getStatus())
                .body(dto);
    }

    @RequestMapping(value = "/get_list", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> getAll() {
        BaseResponseDTO<List<Tag>> all = tagService.getAll();
        return ResponseEntity
                .status(all.getStatus())
                .body(all);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<?> get(@RequestParam UUID id) {
        BaseResponseDTO<Tag> dto = tagService.get(id);
        return ResponseEntity
                .status(dto.getStatus())
                .body(dto);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<?> deleteTag(@RequestParam UUID id) {
        BaseResponseDTO<Tag> delete = tagService.delete(id);
        return ResponseEntity
                .status(delete.getStatus())
                .body(delete);
    }
}

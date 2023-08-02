package com.repeat.controller;

import com.repeat.dto.PostRepsonseDto;
import com.repeat.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostRepsonseDto>> getPosts() {
        return ResponseEntity.ok().body(postService.getPosts());
    }

}



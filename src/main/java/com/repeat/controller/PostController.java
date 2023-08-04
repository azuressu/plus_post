package com.repeat.controller;

import com.repeat.dto.ApiResponseDto;
import com.repeat.dto.OnePostResponseDto;
import com.repeat.dto.PostRepsonseDto;
import com.repeat.dto.PostRequestDto;
import com.repeat.security.UserDetailsImpl;
import com.repeat.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/post")
    public ResponseEntity<ApiResponseDto> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto postRequestDto) {
        PostRepsonseDto postRepsonseDto = postService.createPost(userDetails.getUser(), postRequestDto);
        return ResponseEntity.ok().body(postRepsonseDto);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<OnePostResponseDto> getOnePost(@PathVariable Long postId) {
        OnePostResponseDto onePostResponseDto = postService.getOnePost(postId);
        return ResponseEntity.ok().body(onePostResponseDto);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<OnePostResponseDto> updatePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto postRequestDto) {
        OnePostResponseDto onePostResponseDto = null;
        try {
            onePostResponseDto = postService.updatePost(userDetails.getUser(), postId, postRequestDto);
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
        return ResponseEntity.ok().body(onePostResponseDto);
    }
}



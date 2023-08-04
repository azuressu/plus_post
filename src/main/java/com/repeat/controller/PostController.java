package com.repeat.controller;

import com.repeat.dto.ApiResponseDto;
import com.repeat.dto.OnePostResponseDto;
import com.repeat.dto.PostRepsonseDto;
import com.repeat.dto.PostRequestDto;
import com.repeat.security.UserDetailsImpl;
import com.repeat.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponseDto> updatePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostRequestDto postRequestDto) {
        OnePostResponseDto onePostResponseDto = null;
        try {
            onePostResponseDto = postService.updatePost(userDetails.getUser(), postId, postRequestDto);
            return ResponseEntity.ok().body(onePostResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            postService.deletePost(userDetails.getUser(), postId);
            return ResponseEntity.ok().body(new ApiResponseDto("삭제 성공", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
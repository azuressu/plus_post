package com.repeat.controller;

import com.repeat.dto.ApiResponseDto;
import com.repeat.dto.CommentRequestDto;
import com.repeat.dto.PostRepsonseDto;
import com.repeat.security.UserDetailsImpl;
import com.repeat.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/post")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<PostRepsonseDto> createComment(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto commentRequestDto) {
        log.info(commentRequestDto.getCommentcontent());
        PostRepsonseDto postRepsonseDto = commentService.createComment(postId, userDetails.getUser(), commentRequestDto);
        return ResponseEntity.ok().body(postRepsonseDto);
    }

}

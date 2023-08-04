package com.repeat.controller;

import com.repeat.dto.ApiResponseDto;
import com.repeat.dto.CommentRequestDto;
import com.repeat.dto.CommentResponseDto;
import com.repeat.dto.PostRepsonseDto;
import com.repeat.security.UserDetailsImpl;
import com.repeat.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
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

    @PutMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = null;
        try {
            commentResponseDto = commentService.updateComment(postId, commentId, userDetails.getUser(), commentRequestDto);
            return ResponseEntity.ok().body(commentResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long postId, @PathVariable Long commentId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.deleteComment(commentId, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("삭제 성공", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

}

package com.repeat.dto;


import com.repeat.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto extends ApiResponseDto {

    private String nickname;
    private String commentcontent;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.nickname = comment.getUser().getUsername();
        this.commentcontent = comment.getCommentcontent();
        this.createdAt = comment.getCreatedAt();
    }

}

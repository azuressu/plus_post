package com.repeat.dto;

import com.repeat.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OnePostResponseDto extends ApiResponseDto{

    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;

    public OnePostResponseDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickname = post.getUser().getUsername();
        this.createdAt = post.getCreatedAt();
    }
}

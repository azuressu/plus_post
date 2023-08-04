package com.repeat.dto;


import com.repeat.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
public class PostRepsonseDto extends ApiResponseDto {

    private String title;
    private String username;
    private LocalDateTime createdAt;

    private List<CommentResponseDto> comments;

    public PostRepsonseDto(Post post) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.createdAt = post.getCreatedAt();
        this.comments = post.getComments().stream().map(CommentResponseDto::new).toList();
    }

}

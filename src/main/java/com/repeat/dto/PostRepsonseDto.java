package com.repeat.dto;


import com.repeat.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostRepsonseDto {

    private String title;
    private String username;
    private LocalDateTime createdAt;

    public PostRepsonseDto(Post post) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.createdAt = post.getCreatedAt();
    }

}

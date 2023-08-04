package com.repeat.service;

import com.repeat.dto.CommentRequestDto;
import com.repeat.dto.PostRepsonseDto;
import com.repeat.entity.Comment;
import com.repeat.entity.Post;
import com.repeat.entity.User;
import com.repeat.repository.CommentRepository;
import com.repeat.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostRepsonseDto createComment(Long postId, User user, CommentRequestDto commentRequestDto) {
        Post post = findPost(postId);
        Comment comment = new Comment(commentRequestDto, post, user);

        commentRepository.save(comment);
        post.addComment(comment);

        return new PostRepsonseDto(post);
    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }
}

package com.repeat.service;

import com.repeat.dto.CommentRequestDto;
import com.repeat.dto.CommentResponseDto;
import com.repeat.dto.PostRepsonseDto;
import com.repeat.entity.Comment;
import com.repeat.entity.Post;
import com.repeat.entity.User;
import com.repeat.repository.CommentRepository;
import com.repeat.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public CommentResponseDto updateComment(Long postId, Long commentId, User user, CommentRequestDto commentRequestDto) {
        Post post = findPost(postId);
        Comment comment = findComment(commentId);

        if (comment.getUser().equals(user)) {
            comment.update(commentRequestDto);
        } else {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = findComment(commentId);

        if (comment.getUser().equals(user)){
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    public Comment findComment(Long commentId) { return commentRepository.findById(commentId).orElseThrow(); }


}

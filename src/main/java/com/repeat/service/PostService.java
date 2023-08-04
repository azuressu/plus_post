package com.repeat.service;

import com.repeat.dto.OnePostResponseDto;
import com.repeat.dto.PostRepsonseDto;
import com.repeat.dto.PostRequestDto;
import com.repeat.entity.Post;
import com.repeat.entity.User;
import com.repeat.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostRepsonseDto> getPosts() {
        List<PostRepsonseDto> postRepsonseDtos = postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostRepsonseDto::new).toList();
        return postRepsonseDtos;
    }

    public PostRepsonseDto createPost(User user, PostRequestDto postRequestDto) {
        Post newPost = new Post(postRequestDto, user);
        Post savedPost = postRepository.save(newPost);
        return new PostRepsonseDto(savedPost);
    }

    public OnePostResponseDto getOnePost(Long postId) {
        Post post = findPost(postId);
        return new OnePostResponseDto(post);
    }


    public Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }

    @Transactional
    public OnePostResponseDto updatePost(User user, Long postId, PostRequestDto postRequestDto) {
        Post post = findPost(postId);

        if (post.getUser().equals(user)){
            post.updatePost(postRequestDto);
        } else {
            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
        }
        return new OnePostResponseDto(post);
    }
}

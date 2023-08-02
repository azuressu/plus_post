package com.repeat.service;

import com.repeat.dto.PostRepsonseDto;
import com.repeat.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostRepsonseDto> getPosts() {
        List<PostRepsonseDto> postRepsonseDtos = postRepository.findAllByOrderByCreateAtDesc().stream().map(PostRepsonseDto::new).toList();
        return postRepsonseDtos;
    }
}

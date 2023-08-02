package com.repeat.repository;

import com.repeat.dto.PostRepsonseDto;
import com.repeat.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreateAtDesc();
}
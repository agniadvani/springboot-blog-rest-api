package com.agniadvani.blog.springbootblogrestapi.repository;

import com.agniadvani.blog.springbootblogrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

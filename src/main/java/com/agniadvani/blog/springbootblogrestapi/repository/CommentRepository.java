package com.agniadvani.blog.springbootblogrestapi.repository;

import com.agniadvani.blog.springbootblogrestapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

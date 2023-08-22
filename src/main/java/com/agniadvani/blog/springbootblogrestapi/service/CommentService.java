package com.agniadvani.blog.springbootblogrestapi.service;

import com.agniadvani.blog.springbootblogrestapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);
}

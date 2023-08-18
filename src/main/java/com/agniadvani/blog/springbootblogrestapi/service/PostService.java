package com.agniadvani.blog.springbootblogrestapi.service;

import com.agniadvani.blog.springbootblogrestapi.payload.PostDto;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto);

    public List<PostDto> getAllPosts();
}

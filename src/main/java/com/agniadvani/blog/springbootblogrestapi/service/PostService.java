package com.agniadvani.blog.springbootblogrestapi.service;

import com.agniadvani.blog.springbootblogrestapi.payload.PostDto;
import com.agniadvani.blog.springbootblogrestapi.payload.PostResponse;

public interface PostService {
    public PostDto createPost(PostDto postDto);

    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    public PostDto getPostById(long id);

    public PostDto updatePost(PostDto postDto, long id);

    public void deletePostById(long id);

}

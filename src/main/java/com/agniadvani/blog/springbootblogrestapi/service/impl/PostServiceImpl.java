package com.agniadvani.blog.springbootblogrestapi.service.impl;

import com.agniadvani.blog.springbootblogrestapi.entity.Post;
import com.agniadvani.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.agniadvani.blog.springbootblogrestapi.payload.PostDto;
import com.agniadvani.blog.springbootblogrestapi.repository.PostRepository;
import com.agniadvani.blog.springbootblogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //Convert DTO to entity

        Post post = mapToEntity(postDto);

        //Saving entity to DB
        Post newPost = postRepository.save(post);

        return mapToDto(newPost);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();

        return postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getContent());
        post.setTitle(postDto.getTitle());
        postRepository.save(post);
        return mapToDto(post);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        postRepository.delete(post);
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}

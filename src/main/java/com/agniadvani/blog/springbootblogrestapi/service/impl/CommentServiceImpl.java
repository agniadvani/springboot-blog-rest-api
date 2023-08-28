package com.agniadvani.blog.springbootblogrestapi.service.impl;

import com.agniadvani.blog.springbootblogrestapi.entity.Comment;
import com.agniadvani.blog.springbootblogrestapi.entity.Post;
import com.agniadvani.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.agniadvani.blog.springbootblogrestapi.payload.CommentDto;
import com.agniadvani.blog.springbootblogrestapi.repository.CommentRepository;
import com.agniadvani.blog.springbootblogrestapi.repository.PostRepository;
import com.agniadvani.blog.springbootblogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

        if (comment.getPost().getId() != post.getId()) {
            throw new ResourceNotFoundException("Comment", "Id", commentId);
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

        if (comment.getPost().getId() != post.getId()) {
            throw new ResourceNotFoundException("Comment", "Id", commentId);
        }

        comment.setBody(commentRequest.getBody());
        comment.setEmail(commentRequest.getEmail());
        comment.setName(commentRequest.getName());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

        if (comment.getPost().getId() != post.getId()) {
            throw new ResourceNotFoundException("Comment", "Id", commentId);
        }

        commentRepository.delete(comment);
    }


    private CommentDto mapToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return modelMapper.map(commentDto, Comment.class);
    }
}

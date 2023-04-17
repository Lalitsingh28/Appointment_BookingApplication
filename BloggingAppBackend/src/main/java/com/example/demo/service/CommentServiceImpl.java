package com.example.demo.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.exception.ApiException;
import com.example.demo.exception.PostException;
import com.example.demo.payload.CommentDTO;
import com.example.demo.repository.CommentRepo;
import com.example.demo.repository.PostRepo;


@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CommentDTO createComment(Comment comment, Integer postId) throws PostException{

		Optional<Post> postOpt = postRepo.findById(postId);
		if(postOpt.isPresent()) {
			comment.setPost(postOpt.get());
			Comment savedComment = commentRepo.save(comment);
			CommentDTO commentDto = modelMapper.map(savedComment, CommentDTO.class);
			return commentDto;
			
		}else {
			throw new PostException("There is no post with Id : "+ postId);
		}
	}

	@Override
	public String deleteComment(Integer commentId) throws ApiException{

		Optional<Comment> commentOpt = commentRepo.findById(commentId);
		if(commentOpt.isPresent()) {
			commentRepo.delete(commentOpt.get());
			return "Comment Deleted";	
		}else {
			throw new ApiException("There is no Comment with Id : "+ commentId);
		}
	}

	
}

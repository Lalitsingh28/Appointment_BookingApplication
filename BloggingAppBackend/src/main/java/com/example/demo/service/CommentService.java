package com.example.demo.service;

import com.example.demo.payload.CommentDTO;

public interface CommentService {
	
	public CommentDTO createComment(CommentDTO commentDto, Integer postId);

	public void deleteComment(Integer commentId);

}

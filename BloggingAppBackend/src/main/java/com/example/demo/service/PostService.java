package com.example.demo.service;

import java.util.List;

import com.example.demo.payload.PostDTO;
import com.example.demo.payload.PostResponse;

public interface PostService {
	
	public PostDTO createPost(PostDTO postDto,Integer userId,Integer categoryId);

	public PostDTO updatePost(PostDTO postDto, Integer postId);
	
	public PostDTO getPostById(Integer postId);
	
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

	public void deletePost(Integer postId);

	public List<PostDTO> getPostsByCategory(Integer categoryId);
	
	public List<PostDTO> getPostsByUser(Integer userId);
	
	public List<PostDTO> searchPosts(String keyword);

}

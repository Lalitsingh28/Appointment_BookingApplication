package com.example.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.exception.CategoryException;
import com.example.demo.exception.PostException;
import com.example.demo.exception.UserException;
import com.example.demo.payload.PostDTO;
import com.example.demo.payload.PostResponse;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.UserRepo;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;
    
    public PostDTO postToDto(Post post) {
    	PostDTO postDto = modelMapper.map(post, PostDTO.class);
    	return postDto;
    }

    @Override
    public PostDTO createPost(Post post, Integer userId, Integer categoryId) throws UserException,CategoryException{

    	Optional<User> userOpt = userRepo.findById(userId);
    	if(userOpt.isPresent()) {
    		Optional<Category> categoryOpt = categoryRepo.findById(categoryId);
    		if(categoryOpt.isPresent()) {
    			post.setUser(userOpt.get());
    			post.setCategory(categoryOpt.get());
    			post.setAddedDate(new Date());
    			Post savedPost = postRepo.save(post);
    			PostDTO postDto = postToDto(savedPost);
    			return postDto;
    		}else {
    			throw new UserException("There is no Category with ID : "+ userId);
    		}
    		
    	}else {
    		throw new UserException("There is no user with ID : "+ userId);
    	}
    	
    }

    @Override
    public PostDTO updatePost(Post post, Integer postId) throws PostException{

        Optional<Post> postOpt = postRepo.findById(postId);
        if(postOpt.isPresent()) {
	        Category category = categoryRepo.findById(postOpt.get().getCategory().getCategoryId()).get();
	        postOpt.get().setTitle(post.getTitle());
	        postOpt.get().setContent(post.getContent());
	        postOpt.get().setImageName(post.getImageName());
	        postOpt.get().setCategory(category);
	        postRepo.save(postOpt.get());
	        
	        PostDTO postDto = postToDto(postOpt.get());
	        return postDto;
	        
        }else {
        	throw new PostException("There is no Post with ID : "+postId);
        }

    }

    @Override
    public String deletePost(Integer postId) throws PostException{

        Optional<Post> postOpt = postRepo.findById(postId);
        if(postOpt.isPresent()) {
        	postRepo.delete(postOpt.get());
        	return "Post Has been deleted";
        }else {
        	throw new PostException("There is no Post with ID : "+postId);
        }

    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = postRepo.findAll(p);

        List<Post> allPosts = pagePost.getContent();

        List<PostDTO> postDtos = allPosts.stream().map((post) ->  postToDto(post))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer postId) throws PostException{
        Optional<Post> postOpt = postRepo.findById(postId);
        if(postOpt.isPresent()) {
        	PostDTO postDto = postToDto(postOpt.get());
        	return postDto;
        }else {
        	throw new PostException("There is no Post with ID : "+postId);
        }
    }

    @Override
    public List<PostDTO> getPostsByCategory(Integer categoryId) throws CategoryException,PostException{

        Optional<Category> catOpt = categoryRepo.findById(categoryId);
        if(catOpt.isPresent()) {
        	List<Post> postList = postRepo.findByCategory(catOpt.get());
        	if(!postList.isEmpty()) {
        		List<PostDTO> postDto = postList.stream().map((post) -> postToDto(post))
        										  .collect(Collectors.toList());
                return postDto;
        	}else {
        		throw new PostException("No Post Found");
        	}
        	
        }else {
        	throw new CategoryException("There is no category with Id : "+categoryId);
        } 
    }

    @Override
    public List<PostDTO> getPostsByUser(Integer userId) throws UserException,PostException{

        Optional<User> userOpt = userRepo.findById(userId);
        if(userOpt.isPresent()) {
        	List<Post> posts = postRepo.findByUser(userOpt.get());
        	if(!posts.isEmpty()) {
        		List<PostDTO> postDto = posts.stream().map((post) -> postToDto(post))
						  						.collect(Collectors.toList());
        		return postDto;
        	}else {
        		throw new PostException("No Post Found");
        	}
        }else {
        	throw new UserException("There is no User with Id : "+userId);
        }
    }

    @Override
    public List<PostDTO> searchPosts(String keyword){
        List<Post> posts = postRepo.searchByTitle("%" + keyword + "%");
        List<PostDTO> postDtos = posts.stream().map((post) -> postToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

}

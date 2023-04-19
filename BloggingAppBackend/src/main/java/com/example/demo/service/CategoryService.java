package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Category;
import com.example.demo.exception.CategoryException;
import com.example.demo.payload.CategoryDTO;

public interface CategoryService {
	
	public CategoryDTO createCategory(Category category);

	public CategoryDTO updateCategory(Category category, Integer categoryId) throws CategoryException;
	
	public CategoryDTO getCategory(Integer categoryId)throws CategoryException;

	public List<CategoryDTO> getAllCategories()throws CategoryException;

	public void deleteCategory(Integer categoryId)throws CategoryException;

}

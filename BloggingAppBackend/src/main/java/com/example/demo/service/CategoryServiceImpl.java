package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.exception.CategoryException;
import com.example.demo.payload.CategoryDTO;
import com.example.demo.repository.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(Category category) {
		Category addedCat = categoryRepo.save(category);
		return this.modelMapper.map(addedCat, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(Category category, Integer categoryId) throws CategoryException {
		Optional<Category> catOpt = categoryRepo.findById(categoryId);
		if(catOpt.isPresent()) {
			catOpt.get().setCategoryDescription(category.getCategoryDescription());
			catOpt.get().setCategoryTitle(category.getCategoryTitle());
			
			CategoryDTO catUpdated = modelMapper.map(catOpt, CategoryDTO.class);
			return catUpdated;
		
		}else {
			throw new CategoryException("There is no Category with Id : "+categoryId);
		}
	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) throws CategoryException {
		Optional<Category> catOpt = categoryRepo.findById(categoryId);
		if(catOpt.isPresent()) {
			CategoryDTO cat = modelMapper.map(catOpt.get(), CategoryDTO.class);
			return cat;
		
		}else {
			throw new CategoryException("There is no Category with Id : "+categoryId);
		}
	}

	@Override
	public List<CategoryDTO> getAllCategories() throws CategoryException {
		List<Category> catList = categoryRepo.findAll();
		if(!catList.isEmpty()) {
			List<CategoryDTO> catDtoList = catList.stream().map(cat -> modelMapper
					                               .map(catList, CategoryDTO.class))
					                                .collect(Collectors.toList());
			return catDtoList;
			
		}else {
			throw new CategoryException("There are no Categories");
		}
	}

	@Override
	public void deleteCategory(Integer categoryId) throws CategoryException {
		Optional<Category> catOpt = categoryRepo.findById(categoryId);
		if(catOpt.isPresent()) {
			categoryRepo.delete(catOpt.get());
		
		}else {
			throw new CategoryException("There is no Category with Id : "+categoryId);
		}
		
	}

	
}

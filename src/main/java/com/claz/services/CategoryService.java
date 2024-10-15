package com.claz.services;

<<<<<<< HEAD
import com.claz.models.Category;
import com.claz.models.Product;
import com.claz.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<Category> getAllProducts() {
        return categoryRepository.findAll();
    }
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id);
    }
=======
import java.util.List;
import java.util.Optional;

import com.claz.models.Category;


public interface CategoryService {

	List<Category> findAll();
    
	Optional<Category> findById(int id);
	
	Category create(Category category);

	Category update(Category category);

	void delete(int id);
>>>>>>> cefbaa9380fbe81f6f5454181f197dfe67734ff9
}

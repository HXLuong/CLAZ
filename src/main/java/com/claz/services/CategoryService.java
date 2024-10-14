package com.claz.services;

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
}

package com.claz.services;

import java.util.List;
import java.util.Optional;

import com.claz.models.Category;


public interface CategoryService {

    List<Category> findAll();

    Optional<Category> findById(int id);

    Category create(Category category);

    Category update(Category category);

    void delete(int id);
}
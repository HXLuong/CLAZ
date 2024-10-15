<<<<<<< HEAD:src/main/java/com/claz/repository/CategoryRepository.java
package com.claz.repository;

import com.claz.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
 public Category findById(int id);
=======
package com.claz.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claz.models.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String categoryName);
>>>>>>> cefbaa9380fbe81f6f5454181f197dfe67734ff9:src/main/java/com/claz/repositories/CategoryRepository.java
}
